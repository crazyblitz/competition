package com.ley.innovation.contest.utils;


import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.UUID;

/**
 * security utility class
 *
 * @author ley
 **/
@Slf4j
public class SecurityUtils {

    /**
     * base64 utility inner class
     **/
    public static class Base64Utils {

        /**
         * byte to base64
         **/
        public static String encode(byte[] bytes) {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            return base64Encoder.encode(bytes);
        }

        /**
         * base64 to byte
         **/
        public static byte[] decode(String base64) throws IOException {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            return base64Decoder.decodeBuffer(base64);
        }

        public static void main(String[] args) throws IOException {
            final String testString = "hello,i am chenkangxian,good night!";
            System.out.println(Base64Utils.encode(testString.getBytes()));
            String base64Str = "aGVsbG8saSBhbSBjaGVua2FuZ3hpYW4sZ29vZCBuaWdodCE=";
            System.out.println(new String(Base64Utils.decode(base64Str), Charset.defaultCharset()));
        }
    }


    /**
     * digest utility class with MD5 and SHA-1
     **/
    public static class DigestsUtils {

        /**
         * 160位(16进制字符串: 40)
         **/
        private static final String SHA1 = "SHA-1";

        /**
         * 128位(16进制字符串: 32)
         **/
        private static final String MD5 = "MD5";

        /**
         * SHA-1; encrypt without (salt and iterations)
         *
         * @param bytes encrypt byte array
         **/
        public static byte[] sha1(byte[] bytes) {
            return digest(bytes, SHA1, (byte[]) null, 1);
        }

        /**
         * SHA-1; encrypt without iterations
         *
         * @param bytes encrypt byte array
         * @param salt  encrypt salt
         **/
        public static byte[] sha1(byte[] bytes, byte[] salt) {
            return digest(bytes, SHA1, salt, 1);
        }

        /**
         * SHA-1;
         *
         * @param bytes      encrypt byte array
         * @param salt       encrypt salt
         * @param iterations encrypt iterations
         **/
        public static byte[] sha1(byte[] bytes, byte[] salt, int iterations) {
            return digest(bytes, SHA1, salt, iterations);
        }

        /**
         * MD5
         **/
        public static byte[] md5(byte[] bytes) {
            return digest(bytes, MD5, (byte[]) null, 1);
        }

        /**
         * MD5
         **/
        public static byte[] md5(byte[] bytes, byte[] salt) {
            return digest(bytes, MD5, salt, 1);
        }

        /**
         * MD5
         **/
        public static byte[] md5(byte[] bytes, byte[] salt, int iterations) {
            return digest(bytes, MD5, salt, iterations);
        }


        /**
         * 对salt生成的字节是平台默认编码
         *
         * @see Charset#defaultCharset()
         **/
        public static byte[] generateSalt() {
            return UUID.randomUUID().toString().getBytes();
        }


        /**
         * digest encode md5
         *
         * @param newStr     new str
         * @param oldMd5     old md5 bytes
         * @param salt       md5 salt
         * @param iterations md5 digest iterations
         * @see Arrays
         **/
        public static boolean decodeMd5(byte[] newStr, byte[] oldMd5, byte[] salt, int iterations) {
            return decode(MD5, newStr, oldMd5, salt, iterations);
        }


        /**
         * digest encode sha-1
         *
         * @param newStr     new str
         * @param oldSha1    old sha-1 bytes
         * @param salt       sha-1 salt
         * @param iterations sha-1 digest iterations
         * @see Arrays
         **/
        public static boolean decodeSha1(byte[] newStr, byte[] oldSha1, byte[] salt, int iterations) {
            return decode(SHA1, newStr, oldSha1, salt, iterations);
        }


        /**
         * digest encode
         *
         * @param newStr     new str
         * @param oldDigest  old digest bytes
         * @param salt       md5 salt
         * @param iterations md5 digest iterations
         * @see Arrays
         **/
        private static boolean decode(String algorithm, byte[] newStr, byte[] oldDigest, byte[] salt, int iterations) {
            if (MD5.equalsIgnoreCase(algorithm)) {
                byte[] newDigest = md5(newStr, salt, iterations);
                log.debug("encode new md5 digest: {}", ByteHexUtils.bytes2Hex(newDigest));
                if (Arrays.equals(newDigest, oldDigest)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                byte[] newDigest = sha1(newStr, salt, iterations);
                log.debug("encode new sha1 digest: {}", ByteHexUtils.bytes2Hex(newDigest));
                if (Arrays.equals(newDigest, oldDigest)) {
                    return true;
                } else {
                    return false;
                }
            }
        }


        /**
         * digest with salt and iterations
         **/
        private static byte[] digest(byte[] bytes, String algorithm, byte[] salt, int iterations) {
            try {
                MessageDigest e = MessageDigest.getInstance(algorithm);
                if (salt != null) {
                    e.update(salt);
                }
                byte[] result = e.digest(bytes);
                for (int i = 1; i < iterations; ++i) {
                    e.reset();
                    result = e.digest(result);
                }
                return result;
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static void main(String[] args) {
            String testString = "123456";
            String salt = UUID.randomUUID().toString();
            byte[] md5 = DigestsUtils.md5(testString.getBytes(), salt.getBytes(), 1);
            System.out.println(ByteHexUtils.bytes2Hex(md5));
            if (DigestsUtils.decodeMd5("123456".getBytes(), md5, salt.getBytes(), 1)) {
                System.out.println("密码正确");
            } else {
                System.out.println("密码错误");
            }


            byte[] sha1 = DigestsUtils.sha1(testString.getBytes());
            System.out.println(ByteHexUtils.bytes2Hex(sha1));
            if (DigestsUtils.decodeSha1("123456".getBytes(), sha1, null, 1)) {
                System.out.println("密码正确");
            } else {
                System.out.println("密码错误");
            }
        }
    }


    /**
     * rsa utility class
     **/
    public static class RsaUtils {


        private static final String RSA = "RSA";

        //生成公钥和私钥

        /**
         * 首先初始化{@link KeyPairGenerator},并生成{@link KeyPair},
         * 得到{@link KeyPair},便可以通过getPublic和getPrivate分别获取
         * 公钥和私钥.为了方便保存,将其使用Base64编码转换为String类型的打印字符
         **/
        public static KeyPair getKeyPair() throws Exception {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            //默认512,如果是1024位,需要到官网下载不限长度的加密jar包
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        }

        /**
         * get public key
         *
         * @see Base64Utils#encode(byte[])
         **/
        public static String getPublicKeyStr(KeyPair keyPair) {
            PublicKey publicKey = keyPair.getPublic();
            byte[] bytes = publicKey.getEncoded();
            return Base64Utils.encode(bytes);
        }

        /**
         * get primary key
         *
         * @see Base64Utils#encode(byte[])
         **/
        public static String getPrivateKeyStr(KeyPair keyPair) {
            PrivateKey privateKey = keyPair.getPrivate();
            byte[] bytes = privateKey.getEncoded();
            return Base64Utils.encode(bytes);
        }

        /**
         * 将String类型的密钥转换为PublicKey和PrivateKey对象
         **/
        public static PublicKey string2PublicKey(String publicKeyStr) throws Exception {
            byte[] keyBytes = Base64Utils.decode(publicKeyStr);
            //使用ASN.1为公钥编码
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        }


        /**
         * 将String类型的密钥转换为{@link PrivateKey}
         **/
        public static PrivateKey string2PrivateKey(String privateKeyStr) throws Exception {
            byte[] keyBytes = Base64Utils.decode(privateKeyStr);
            //使用ASN.1为私钥编码
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        }


        /**
         * 使用公钥加密
         **/
        public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(content);
            return bytes;
        }

        /**
         * 使用私钥解密
         **/
        public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = cipher.doFinal(content);
            return bytes;
        }

        public static void main(String[] args) throws Exception {
            final String testString = "1234567";

            //获得密钥对
            KeyPair keyPair = RsaUtils.getKeyPair();

            //获得私钥字符串
            String privateKeyStr = RsaUtils.getPrivateKeyStr(keyPair);
            System.out.println("primary key string: " + privateKeyStr);
            System.out.println();

            //获得公钥字符串
            String publicKeyStr = RsaUtils.getPublicKeyStr(keyPair);
            System.out.println("public key string: " + publicKeyStr);
            System.out.println();

            //获取私钥和公钥
            PublicKey publicKey = RsaUtils.string2PublicKey(publicKeyStr);
            PrivateKey privateKey = RsaUtils.string2PrivateKey(privateKeyStr);
            System.out.println();

            //使用公钥加密
            byte[] bytes = RsaUtils.publicEncrypt(testString.getBytes(), publicKey);
            System.out.println("RSA公钥加密: " + Base64Utils.encode(bytes));
            System.out.println();

            //使用私钥解密
            System.out.println("RSA私钥解密: " + new String(RsaUtils.privateDecrypt(bytes, privateKey),
                    Charset.defaultCharset()));
        }
    }
}
