package com.waysli.tools.security.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * Security 为开发接口提供安全传输方案, 包括数字签名和加解密服务
 *
 * @author waysli
 * @create 2017-10-17 下午5:25
 **/
public class Security {
    /**
     * 获得随机的128位密码, 用于Api客户端生成临时密码
     *
     * @return 128位密码
     * @author waysli
     * @date 16/12/26 下午8:29
     */
    public static String getSecretKey() {
        String secretKey = "";
        for (int i = 0; i < 4; i++) {
            secretKey += UUID.randomUUID().toString();
        }
        secretKey = secretKey.replace("-", "");

        return secretKey;
    }

    /**
     * 根据指定密钥加密指定的文本, 加密算法为标准AES算法
     *
     * @param cipherKey 密钥
     * @param plainText 要加密的文本明文
     * @return 加密后的数据
     * @throws Exception
     * @author waysli
     * @date 16/12/26 下午3:20
     */
    public static String encryptData(String cipherKey, String plainText) throws Exception {
        if (StringUtils.isEmpty(cipherKey) || cipherKey.length() != 128 || StringUtils.isEmpty(plainText)) {
            return null;
        }
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(cipherKey.getBytes());
        kgen.init(128, secureRandom);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, kgen.generateKey());
        byte[] bytes = cipher.doFinal(plainText.getBytes("utf-8"));

        return new Base64().encodeToString(bytes);
    }

    /**
     * 根据指定密钥解密指定的文本, 算法为标准AES算法
     *
     * @param cipherKey  密钥
     * @param cipherText 要解密的文本密文
     * @return 解密后的数据
     * @throws Exception
     * @author waysli
     * @date 16/12/26 下午4:32
     */
    public static String decryptData(String cipherKey, String cipherText) throws Exception {
        if (StringUtils.isEmpty(cipherKey) || cipherKey.length() != 128 || StringUtils.isEmpty(cipherText)) {
            return null;
        }
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(cipherKey.getBytes());
        kgen.init(128, secureRandom);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, kgen.generateKey());
        byte[] bytes = new Base64().decode(cipherText);

        return new String(cipher.doFinal(bytes));
    }

    /**
     * 获取指定内容的签名信息
     *
     * @param content 要签名的文本内容
     * @return 签名结果
     * @throws Exception
     * @author waysli
     * @date 16/12/26 下午6:32
     */
    public static String getSignature(String content) throws NoSuchAlgorithmException {
        if (StringUtils.isEmpty(content)) {
            return null;
        }

        MessageDigest md = null;
        md = MessageDigest.getInstance("SHA-1");
        md.update(content.getBytes());
        byte[] bytes = md.digest();

        return new Base64().encodeToString(bytes);
    }

    public static void help() {
        System.out.println("############################## 使用说明 ##############################");
        System.out.println("1. 加密功能, 第一个参数固定为:encrypt, 第二个参数为密码, 第三个参数为要加密的文本");
        System.out.println("2. 解密功能, 第一个参数固定为:decrypt, 第二个参数为密码, 第三个参数为要解密的文本");
        System.out.println("3. 签名功能, 第一个参数固定为:signature, 第二个参数为要签名的文本内容");
        System.out.println("4. 密钥生成功能, 第一个参数固定为:key");
        System.out.println("--------------------------------------------------------------------");
        System.out.println("");
    }

    public static void main(String[] args) {
        if (args.length <= 0) {
            help();
            return;
        }

        try {
            if ("encrypt".equals(args[0]) && args.length == 3) {
                System.out.println(encryptData(args[1], args[2]));
            } else if ("decrypt".equals(args[0]) && args.length == 3) {
                System.out.println(decryptData(args[1], args[2]));
            } else if ("signature".equals(args[0]) && args.length == 2) {
                System.out.println(getSignature(args[1]));
            } else if ("key".equals(args[0]) && args.length == 1) {
                System.out.println(getSecretKey());
            } else {
                help();
            }
        } catch (Exception e) {
            help();
        }
    }
}
