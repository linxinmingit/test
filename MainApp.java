package com.yr.util;

import java.nio.charset.Charset;

public class MainApp {
    private static final String  SKEY    = "zxshjlxb";//加密KEY
    private static final Charset CHARSET = Charset.forName("gb2312");//编码格式(中文默认就是GB2312)

    public static void main(String[] args) {
        // 待加密内容
        String str = "xiongdingkun";
        String encryptResult = DesUtil.encrypt(str, CHARSET, SKEY);
        System.out.println("加密以后的值 = "+encryptResult);
        
        
        // 直接将如上内容解密
        String decryResult = "942355691BD7505112D1D65DA669CF36";
        try {
            decryResult = DesUtil.decrypt(encryptResult, CHARSET, SKEY);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        System.out.println("解密以后的值 = "+decryResult);
    }
}