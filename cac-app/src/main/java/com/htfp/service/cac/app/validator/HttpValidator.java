package com.htfp.service.cac.app.validator;

import com.htfp.service.cac.common.constant.HttpHeaderConstant;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 15:59
 */
@Slf4j
@Service
public class HttpValidator implements BaseValidator<ErrorCodeEnum, HttpServletRequest> {

    /**
     * 校验HTTP请求
     * @param request
     * @param httpServletRequest
     * @return
     */
    public ErrorCodeEnum httpRequestValidate (BaseValidate<ErrorCodeEnum> request, HttpServletRequest httpServletRequest) {
        try {
            // 校验请求头
            ErrorCodeEnum headerValidateResult = validate(httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(headerValidateResult)) {
                return headerValidateResult;
            }
            // 校验请求体
            ErrorCodeEnum requestValidateResult = request.validate();
            if (!ErrorCodeEnum.SUCCESS.equals(requestValidateResult)) {
                return requestValidateResult;
            }
        } catch (Exception e) {
            log.error("请求头和请求体校验发生异常, request={}，httpServletRequest={}", request, httpServletRequest, e);
            return ErrorCodeEnum.UNKNOWN_ERROR;
        }
        return ErrorCodeEnum.SUCCESS;
    }

    /**
     * 参数校验
     * @param request
     * @return
     */
    @Override
    public ErrorCodeEnum validate(HttpServletRequest request) {
        String path = request.getRequestURI();
        String authorization = request.getHeader(HttpValidatorConstant.HTTP_HEADER_AUTHORIZATION);
        String date = request.getHeader(HttpValidatorConstant.HTTP_HEADER_DATE);
        String gcsId = request.getHeader(HttpValidatorConstant.HTTP_HEADER_GCS_ID);
        String realIp = request.getHeader(HttpHeaderConstant.HTTP_HEADER_REAL_IP);
        String forwardedIp = request.getHeader(HttpHeaderConstant.HTTP_HEADER_FORWARDED_IP);
        String requestId = request.getHeader(HttpHeaderConstant.HTTP_HEADER_REQUEST_ID);
        log.info("BA认证 gcsId="
                + ((StringUtils.isBlank(gcsId) ? "N/A" : gcsId)
                + ",date=" + date + ",path=" + path + ",authorization=" + authorization
                + ",forwardedIP=" + forwardedIp + ",realIP=" + realIp + ",requestId=" + (StringUtils.isBlank(requestId) ? "N/A" : requestId)));
        ErrorCodeEnum headerParamCheckResult = headerParamCheck(authorization, date, gcsId);
        if (!ErrorCodeEnum.SUCCESS.equals(headerParamCheckResult)) {
            log.info("BA认证失败，authorization->{}", authorization);
            return headerParamCheckResult;
        }
        return authorizationCheck(authorization, date, gcsId, request);
    }

    /**
     * 校验authorization
     * @param authorization
     * @param date
     * @param gcsId
     * @param request
     * @return
     */
    public ErrorCodeEnum authorizationCheck(String authorization, String date, String gcsId, HttpServletRequest request) {
        String[] clientSignature = authorization.split(":");
        String requestGcsId = clientSignature[0];
        String requestSignature = clientSignature[1];
        String gcsToken = null;
        try {
            if (!gcsId.equals(requestGcsId)) {
                return ErrorCodeEnum.GCS_ID_VALIDATE_FAIL;
            }
            // TODO: 2022/5/20 获取gcsToken
        } catch (Exception e) {
            log.error("查询地面站信息异常，gcsId={}", gcsId, e);
            return ErrorCodeEnum.UNKNOWN_ERROR;
        }
        // 验证签名
        if (StringUtils.isBlank(gcsToken)) {
            log.info("BA验证异常, gcsToken is null, gcsId=" + gcsId);
            return ErrorCodeEnum.UNKNOWN_ERROR;
        }
        String stringToSign = request.getMethod().toUpperCase() + " " + request.getRequestURI() + "\n" + date;
        String signature = null;
        try {
            signature = getSignature(stringToSign, gcsToken);
        } catch (Exception e) {
            log.error("BAHeader.getSignature is error", e);
            return ErrorCodeEnum.UNKNOWN_ERROR;
        }
        if (!StringUtils.equals(requestSignature, signature)) {
            log.info("BA认证签名错误 请求签名=" + requestSignature + ", 计算的签名=" + signature
                    + ", gcsToken=" + gcsToken + ", 计算签名字符串=" + stringToSign);
            return ErrorCodeEnum.AUTHORIZATION_VALIDATE_FAIL;
        }
        return ErrorCodeEnum.SUCCESS;
    }

    /**
     * 校验请求头参数
     * @param authorization
     * @param date
     * @param gcsId
     * @return
     */
    public ErrorCodeEnum headerParamCheck(String authorization, String date, String gcsId) {
        if (StringUtils.isBlank(authorization)) {
            log.info("验证失败, 请求头authorization缺失");
            return ErrorCodeEnum.LACK_OF_AUTHORIZATION;
        }
        if (StringUtils.isBlank(date)) {
            log.info("验证失败, 请求头date缺失");
            return ErrorCodeEnum.LACK_OF_DATE;
        }
        if (StringUtils.isBlank(gcsId)) {
            log.info("请求头地面站编号缺失");
            return ErrorCodeEnum.LACK_OF_HEADER_GCS_ID;
        }
        SimpleDateFormat df = new SimpleDateFormat(HttpValidatorConstant.HTTP_HEADER_DATE_FORMAT, Locale.US);
        df.setTimeZone(TimeZone.getTimeZone(HttpValidatorConstant.HTTP_HEADER_TIME_ZONE));
        try {
            Date date1 = df.parse(date);
            // 拒绝一个小时以前的发过来的服务
            if (new DateTime(date1).isBefore(new DateTime().minusHours(1))) {
                log.info("BA验证异常:拒绝一个小时以前的发过来的请求.date->{}", date);
                return ErrorCodeEnum.DATE_VALIDATE_FAIL;
            }
        } catch (ParseException e) {
            log.info("反序列化header date错误, dateString=" + date);
            return ErrorCodeEnum.UNKNOWN_ERROR;
        }
        return ErrorCodeEnum.SUCCESS;
    }

    /**
     * 数据加密
     * @param content
     * @param secretKey
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     */
    private String getSignature(String content, String secretKey)
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        if (secretKey == null) {
            throw new InvalidKeyException("sign secretKey is null");
        }
        if (content == null) {
            throw new InvalidAlgorithmParameterException("sign content is null");
        }
        byte[] data = content.getBytes(Consts.UTF_8);
        byte[] key = secretKey.getBytes(Consts.UTF_8);
        SecretKeySpec signingKey = new SecretKeySpec(key, HttpValidatorConstant.ALGORITHM_HMAC_SHA1);
        Mac mac = Mac.getInstance(HttpValidatorConstant.ALGORITHM_HMAC_SHA1);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data);
        return new String(Base64.encodeBase64(rawHmac));
    }
}
