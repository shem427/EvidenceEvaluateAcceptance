package com.suyuan.poa.webapp.common;

import java.security.MessageDigest;
import java.util.List;

/**
 * utility类。
 */
public final class PoaUtil {
    /**
     * 散列算法
     */
    private static final String ALGORITHM = "SHA-256";

    private PoaUtil() {
    }

    /**
     * 密码散列。
     * @param password 密码
     * @return 散列后的密码
     */
    public static String hashEncode(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(password.getBytes());

            return bytesToHexString(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * byte数组转换为字符串
     * @param bytes byte数组
     * @return 字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
