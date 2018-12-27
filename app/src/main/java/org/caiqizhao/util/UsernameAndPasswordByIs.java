package org.caiqizhao.util;

/**
 * 登陆检测
 */

public class UsernameAndPasswordByIs {

    /**
     * 密码验证
     * @param password
     * @return
     */
    public static boolean checkPassword(String password){
        String zz = "[a-zA-Z0-9]{6,16}";
        return password.matches(zz);
    }

    /**
     * 账户验证
     * @param username
     * @return
     */
    public static boolean checkusername(String username){
        String zz = "[a-zA-Z0-9_]{3,16}";
        return username.matches(zz);
    }
}
