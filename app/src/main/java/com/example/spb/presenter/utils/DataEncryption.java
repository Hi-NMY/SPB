package com.example.spb.presenter.utils;

import com.example.spb.misc.BASE64Decoder;
import com.example.spb.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DataEncryption {

    private static final byte[] KEY = {11, 12, 13, -11, -10, -9, 99, 1};

    //加密
    public static String intoData(String s) {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dkey = new DESKeySpec(KEY);
            SecretKeyFactory sf = SecretKeyFactory.getInstance("DES");
            SecretKey sk = sf.generateSecret(dkey);
            Cipher c = Cipher.getInstance("DES");
            c.init(Cipher.ENCRYPT_MODE, sk, sr);
            return new BASE64Encoder().encode(c.doFinal(s.getBytes()));
        } catch (Exception e) {

        }
        return s;
    }

    //解密
    public static String outData(String s) {
        try {
            String decrypted = null;
            SecureRandom sr = new SecureRandom();
            DESKeySpec ks = new DESKeySpec(KEY);
            SecretKeyFactory sf = SecretKeyFactory.getInstance("DES");
            SecretKey sk = sf.generateSecret(ks);
            Cipher c = Cipher.getInstance("DES");
            c.init(Cipher.DECRYPT_MODE, sk, sr);
            decrypted = new String(c.doFinal(new BASE64Decoder().decodeBuffer(s)));
            return decrypted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
