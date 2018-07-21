package com.danny.imageview.cache;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密
 * Created by danny on 6/9/18.
 */

public class MD5Encoder {

    /**
     * md5算法对URL进行加密生成文件名
     * @param code url地址
     * @return 文件名
     */
    public static String encode(String code) {
        MessageDigest md = null;
        try {
             md = MessageDigest.getInstance("MD5");
            byte[] bys = md.digest(code.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bys) {
                int number = b & 0xff;
                if (number < 0x10){sb.append("0");}
                String str = Integer.toHexString(number);
                sb.append(str);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
