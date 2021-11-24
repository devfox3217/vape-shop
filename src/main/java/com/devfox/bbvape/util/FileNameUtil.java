package com.devfox.bbvape.util;

public class FileNameUtil {
    public static String getFileName(String filename) {
        int index = filename.lastIndexOf(".");

        String extension = filename.substring(index);
        String randomString = RandomStringUtil.getRandomStr(10);

        return randomString + extension;
    }
}
