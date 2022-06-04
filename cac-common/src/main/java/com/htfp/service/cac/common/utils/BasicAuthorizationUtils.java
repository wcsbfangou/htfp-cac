package com.htfp.service.cac.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpRequestBase;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Author sunjipeng
 * @Date 2022-06-02 18:09
 */
@Slf4j
public class BasicAuthorizationUtils {
    public static final String ALGORITHM_HMAC_SHA1 = "HmacSHA1";
    public static final String HTTP_HEADER_DATE = "Date";
    public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
    public static final String HTTP_HEADER_TIME_ZONE = "GMT";
    public static final String HTTP_HEADER_DATE_FORMAT = "EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'z";


    private BasicAuthorizationUtils() {
    }

    public static void generateAuthAndDateHeader(HttpRequestBase request, Long gcsId, String gcsToken) {
        Date sysdate = new Date();
        SimpleDateFormat df = new SimpleDateFormat(HTTP_HEADER_DATE_FORMAT, Locale.US);
        df.setTimeZone(TimeZone.getTimeZone(HTTP_HEADER_TIME_ZONE));
        String date = df.format(sysdate);
        String stringToSign = request.getMethod().toUpperCase() + " " + request.getURI().getPath() + "\n" + date;
        String encoding;
        try {
            encoding = getSignature(stringToSign.getBytes(), gcsToken.getBytes());
        } catch (Exception e1) {
            log.warn("generateSignature Exception occured:", e1);
            return;
        }
        String authorization = gcsId + ":" + encoding;
        request.addHeader(HTTP_HEADER_AUTHORIZATION, authorization);
        request.addHeader(HTTP_HEADER_DATE, date);
    }

    public static String getSignature(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec signingKey = new SecretKeySpec(key, ALGORITHM_HMAC_SHA1);
        Mac mac = Mac.getInstance(ALGORITHM_HMAC_SHA1);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data);
        return new String(Base64.encodeBase64(rawHmac));
    }

}
