package org.caiqizhao.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/*
 * 1.初始化MessageDigest信息摘要对象
 * 2.传入需要计算的字符串更新摘要信息
 * 3.计算信息摘要
 * 4.将byte[] 转换为找度为32位的16进制字符串
 */
public class PasswordSHA1Util {


    /*
     * 生成md5 有传入参数字符串
     */
    public static String generateSHA1(String input) {

        try {
            //1.初始化MessageDigest信息摘要对象
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");

            //2.传入需要计算的字符串更新摘要信息，传入的为字节数组byte[],
            //将字符串转换为字节数组使用getBytes()方法完成
            //指定时其字符编码 为utf-8
            sha1.update(input.getBytes("utf-8"));

            //3.计算信息摘要digest()方法
            //返回值为字节数组
            byte[] hashCode = sha1.digest();
            //4.将byte[] 转换为找度为32位的16进制字符串
            //声明StringBuffer对象来存放最后的值
            StringBuffer sb = new StringBuffer();

            //遍历字节数组
            for (byte b : hashCode) {
                //对数组内容转化为16进制，
                sb.append(Character.forDigit(b >> 4 & 0xf, 16));
                //换2次为32位的16进制
                sb.append(Character.forDigit(b & 0xf, 16));
            }
           return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }


    }
}
