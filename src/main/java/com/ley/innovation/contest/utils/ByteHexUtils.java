package com.ley.innovation.contest.utils;

import java.nio.ByteBuffer;

/**
 * byte hex utility class
 **/
public class ByteHexUtils {

    private static final String HEX_STR = "0123456789ABCDEF";


    /**
     * @param hex
     * @return
     */
    public static byte[] hex2Bytes(String hex) {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }


    /**
     * @param bytes
     **/
    public static String bytes2Hex(byte[] bytes) {
        String result = "";
        String hex = "";
        for (int i = 0; i < bytes.length; i++) {
            //字节高4位
            hex = String.valueOf(HEX_STR.charAt((bytes[i] & 0xF0) >> 4));
            //字节低4位
            hex += String.valueOf(HEX_STR.charAt(bytes[i] & 0x0F));
            result += hex;  //+" "
        }
        return result;
    }

}
