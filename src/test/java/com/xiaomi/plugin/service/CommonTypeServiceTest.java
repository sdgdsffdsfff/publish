package com.xiaomi.plugin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.plugin.util.IPUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CommonTypeServiceTest {

    @Test
    public void IPTest() {
//       System.out.println(IPUtil.getIPAddress("222.74.181.47"));
//       System.out.println(IPUtil.getIPAddress("59.172.141.77"));
//        List<String> test = new ArrayList<String>();
//        test.add("武汉");
//        System.out.println(test.contains("武汉市"));
        System.out.println("武汉市".indexOf("武汉"));
        System.out.println("武汉".indexOf("武汉市"));

    }

}