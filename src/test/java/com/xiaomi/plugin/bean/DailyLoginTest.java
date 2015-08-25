package com.xiaomi.plugin.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class DailyLoginTest {

    @Test
    public void PSTest() {
        String Tree_FILE = IPSeeker.class.getResource("/tree.json").toString().substring(5);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String treeJSonResult = "{'test':'tes1111111t'}";
            File treeFile = new File(Tree_FILE);
            PrintStream ps = new PrintStream(new FileOutputStream(treeFile));
            ps.println(treeJSonResult);// 往文件里写入字符串
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("wenj..................");
    }

}