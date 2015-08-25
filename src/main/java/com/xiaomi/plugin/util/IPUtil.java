package com.xiaomi.plugin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.plugin.bean.IPSeeker;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Map;

/**
 * 解析ip
 * Created by lijie on 2015-06-12.
 */
public class IPUtil {

    public static String getIPAddress(String ip) {
        IPSeeker ipSeeker = SpringUtil.getBean("ipSeeker");
        return ipSeeker.getAddress(ip);
    }

//    public static String getIPAddress(String ip) {
//        String httpUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=";
//        if (StringUtils.isNotEmpty(ip)) {
//            try {
//                httpUrl += ip.trim();
//                URL url = new URL(httpUrl);
//                URLConnection conn = url.openConnection();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String line = null;
//                StringBuffer result = new StringBuffer();
//                while ((line = reader.readLine()) != null) {
//                    result.append(line);
//                }
//                reader.close();
//                ObjectMapper objectMapper = new ObjectMapper();
//                Map<String, Object> map = objectMapper.readValue(result.toString(), Map.class);
//                Map<String, String> map1 = (Map<String, String>) map.get("data");
//                String region = map1.get("region");
//                String city = map1.get("city");
//                if (StringUtils.isNotEmpty(city)) {
//                    return city.substring(0, city.length() - 1);
//                } else {
//                    return region;
//                }
//            } catch (Exception e) {
//                return null;
//            }
//        }
//        return null;
//    }

    public static String getIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.contains("192.168")) {
                try {
//                    URL url = new URL("http://ipapi.sinaapp.com/inc/ip.php?action=getip");
                    URL url = new URL("http://1111.ip138.com/ic.asp");
                    URLConnection conn = url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    StringBuffer result = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                    String strResult = result.toString();
                    if (strResult.contains("[") && strResult.contains("]")) {
                        ipAddress = strResult.substring(strResult.indexOf("[") + 1, strResult.indexOf("]"));
                    }
                } catch (Exception e) {
                }
            }
        }
        if (ipAddress.contains(",")) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }
        return ipAddress;
    }
}
