package com.vins_nerf.core.utils;

import com.vins_nerf.core.http.RestConstants;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MD5Util {
    private static final byte[] hex = "0123456789abcdef".getBytes(RestConstants.UTF8);

    public static String encode(String rawString) {
        if (StringUtil.isNullOrEmpty(rawString)) return null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(rawString.getBytes(RestConstants.UTF8));
            return convertToHexString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("[MD5Util.encode]", e);
        }
        return null;
    }

    private static byte[] convertToHex(byte[] digests) {
        byte[] md5String = new byte[digests.length * 2];

        int index = 0;
        for (byte digest : digests) {
            md5String[index] = hex[(digest >> 4) & 0x0F];
            md5String[index + 1] = hex[digest & 0x0F];
            index += 2;
        }

        return md5String;
    }

    public static String convertToHexString(byte[] digests) {
        return new String(convertToHex(digests));
    }
}
