package org.caiqizhao.util;

import org.apache.commons.codec.binary.Hex;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSACode {
    private RSAPublicKey rsaPublicKey;
    private RSAPrivateKey rsaPrivateKey;

    public String encode(String src)
    {
        try
        {
            //初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            rsaPublicKey = (RSAPublicKey)keyPair.getPublic();
            rsaPrivateKey = (RSAPrivateKey)keyPair.getPrivate();

            //私钥加密 公钥解密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec
                    = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(src.getBytes());

            //私钥解密 公钥加密
//  X509EncodedKeySpec x509EncodedKeySpec =
//   new X509EncodedKeySpec(rsaPublicKey.getEncoded());
//  KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//  PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
//  Cipher cipher = Cipher.getInstance("RSA");
//  cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//  byte[] resultBytes = cipher.doFinal(src.getBytes());

            return Hex.encodeHex(resultBytes).toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String decode(String src)
    {
        try
        {
            //私钥加密 公钥解密
            X509EncodedKeySpec x509EncodedKeySpec =
                    new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(Hex.decodeHex(src.toCharArray()));

            //私钥解密 公钥加密
//  PKCS8EncodedKeySpec pkcs8EncodedKeySpec
//  = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
//  KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//  PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
//  Cipher cipher = Cipher.getInstance("RSA");
//  cipher.init(Cipher.DECRYPT_MODE, privateKey);
//  byte[] resultBytes = cipher.doFinal(Hex.decodeHex(src.toCharArray()));

            return new String(resultBytes);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
