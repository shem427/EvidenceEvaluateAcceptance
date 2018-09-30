package com.suyuan.poa.webapp.common;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.security.MessageDigest;

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
     * 文件下载。
     * @param file 文件对象
     * @param fileName 下载的文件名
     * @return 下载对象
     */
    public static ResponseEntity<FileSystemResource> export(File file, String fileName) {
        if (file == null) {
            return null;
        }
        if (fileName == null) {
            fileName = String.valueOf(System.currentTimeMillis());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + fileName + ".xls");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
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
