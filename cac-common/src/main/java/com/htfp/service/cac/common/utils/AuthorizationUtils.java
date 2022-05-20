package com.htfp.service.cac.common.utils;

import com.htfp.service.cac.common.constant.HttpValidatorConstant;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 14:25
 */
public class AuthorizationUtils {

    public static String getSignature(String content, String secretKey)
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
