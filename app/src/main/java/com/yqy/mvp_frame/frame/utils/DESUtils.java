package com.yqy.mvp_frame.frame.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtils {

    public static String encryption(String strsource, String password) {
        try {
            byte[] datasource = strsource.getBytes("UTF-8");
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes("UTF-8"));
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return Base64.encode(cipher.doFinal(datasource));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String str, String password)  {
        byte[] src = Base64.decodeFast(str);
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey;
            desKey = new DESKeySpec(password.getBytes("UTF-8"));
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);
            // 真正开始解密操作
            return new String(cipher.doFinal(src), "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block

            str = str.replaceAll("%2B","\\+");
            src = Base64.decodeFast(str);
            try {
                // DES算法要求有一个可信任的随机数源
                SecureRandom random = new SecureRandom();
                // 创建一个DESKeySpec对象
                DESKeySpec desKey;
                desKey = new DESKeySpec(password.getBytes("UTF-8"));
                // 创建一个密匙工厂
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                // 将DESKeySpec对象转换成SecretKey对象
                SecretKey securekey = keyFactory.generateSecret(desKey);
                // Cipher对象实际完成解密操作
                Cipher cipher = Cipher.getInstance("DES");
                // 用密匙初始化Cipher对象
                cipher.init(Cipher.DECRYPT_MODE, securekey, random);
                // 真正开始解密操作
                return new String(cipher.doFinal(src), "UTF-8");
            } catch (Exception e1) {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
        return null;
    }
}