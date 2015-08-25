package com.xiaomi.plugin.util;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 文件,字符串加密
 * Created by lijie on 2015-06-16.
 */
public class MD5Util {
    protected static MessageDigest messagedigest = null;
    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        messagedigest.update(byteBuffer);
        return Hex.encodeHexString(messagedigest.digest());
    }

    public static String getStrMd5String(String str) {
        messagedigest.update(str.getBytes());
        return Hex.encodeHexString(messagedigest.digest());
    }


    public static void main(String... args) throws IOException {
//        File fileZIP = new File("D:/test.zip");
//        String md5 = getFileMD5String(fileZIP);
//        System.out.println(md5.length());
        System.out.println(getStrMd5String("123456"));
    }


}
