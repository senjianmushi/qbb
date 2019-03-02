package com.qbb.cxda.util;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.io.*;

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

    /**
     * MD5加密
     * @param password  密码
     * @param salt  加盐
     * @return  密码+盐的MD5加密
     */
    public static String encryptionMD5(String password,String salt){
        Md5Hash md5Hash = new Md5Hash(password,salt);
        return md5Hash.toString();
    }


    /**
     * 身份证信息隐藏中间数字
     * @param identityId
     * @return
     */
    public static String getIdentityId(String identityId){
        String id_middle = identityId.substring(4,14);
        return identityId.replaceAll(id_middle,"**************");
    }

//    public static  void main(String[] args){
//        System.out.println(CommonUtil.getIdentityId("330381199007164174"));
//    }

}
