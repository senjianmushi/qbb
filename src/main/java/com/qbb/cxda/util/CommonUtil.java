package com.qbb.cxda.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CommonUtil {

    /**
     * 判断字符串是否为空
     * @param str   字符串
     * @return
     */
    public static boolean ifEmpty(Object str){
        if(str == null){
            return true;
        }else if("".equals(str)){
            return true;
        }else{
            return false;
        }
    }

    public static void upload(){

    }

    public static String encryptionMD5(String password,String salt){
        Md5Hash md5Hash = new Md5Hash(password,salt);
        return md5Hash.toString();
    }
}
