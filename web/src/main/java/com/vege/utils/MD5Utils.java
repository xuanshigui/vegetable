package com.vege.utils;


import org.springframework.util.DigestUtils;

import java.util.UUID;

public class MD5Utils {


    /*
     * 密码加密
     */
    public static String encodePassword(String password, String salt) {
        String base = password + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }

    public static boolean comparePassword(String password, String input, String salt) {
        String inputBase = input + "/" + salt;
        String passwordBase = password + "/" + salt;
        String inputString = DigestUtils.md5DigestAsHex(inputBase.getBytes());
        String passwordString = DigestUtils.md5DigestAsHex(passwordBase.getBytes());
        if (inputString.compareTo(passwordString) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String getRandomSalt() {

        return UUID.randomUUID().toString().replaceAll("-", "x");
    }

}