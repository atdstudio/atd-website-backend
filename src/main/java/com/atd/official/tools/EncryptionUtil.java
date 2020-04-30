package com.atd.official.tools;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class EncryptionUtil {

    public static String encryption(String username, String password){
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(username + Constants.SALT);
        //加密次数
        int hashIterations = 1;
        SimpleHash result = new SimpleHash("SHA-1", password, byteSalt, hashIterations);
        return result.toString();
    }

}
