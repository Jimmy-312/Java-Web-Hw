package com.project.javaweb.util;



import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesUtil {

    private static SecureRandom secureRandom = new SecureRandom();;
    private static final String ALGORITHM_DES = "des";


    /**
     * 
     *
     * @param key 
     * @return SecretKey
     */
    public static SecretKey getSecretKey(final String key) {
        try {
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            SecretKeyFactory instance = SecretKeyFactory.getInstance(ALGORITHM_DES);
            SecretKey secretKey = instance.generateSecret(desKeySpec);
            return secretKey;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 
     *
     * @param processData 
     * @param key         
     * @param opsMode     
     * @param algorithm   
     * @return byte[]
     */
    private static byte[] processCipher(final byte[] processData, final Key key,
                                        final int opsMode, final String algorithm) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            //初始化
            cipher.init(opsMode, key, secureRandom);
            return cipher.doFinal(processData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DESEncrypt(final String key, final String content) throws UnsupportedEncodingException {
        return new String(Base64.getEncoder().encode(processCipher(content.getBytes("UTF-8"), getSecretKey(key), Cipher.ENCRYPT_MODE , ALGORITHM_DES)));
    }

    /**
     * 
     * @param key 
     * @param encoderContent 
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static String DESDecrypt(final String key, final String encoderContent) throws UnsupportedEncodingException {
        return new String(processCipher(Base64.getDecoder().decode(encoderContent), getSecretKey(key), Cipher.DECRYPT_MODE, ALGORITHM_DES),"UTF-8");
    }


    

    
}
