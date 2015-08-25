package com.xiaomi.plugin.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

/**
 * 区域管理
 * Created by lijie on 2015-06-11.
 */
@Controller
@RequestMapping(value = "/region")
public class RegionController extends BaseController {

    private static final Logger LOG = Logger.getLogger(RegionController.class);

    @RequestMapping(value = "/get")
    @ResponseBody
    public String getTree() {
        String Tree_FILE = this.getClass().getResource("/tree.json").toString().substring(5);
        StringBuilder result = new StringBuilder();
        try {
            File treeFile = new File(Tree_FILE);
            InputStream treeFileInputStream = new FileInputStream(treeFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(treeFileInputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            treeFileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String resultStr = "";
//        try {
//            resultStr = new String(result.toString().getBytes(), "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        LOG.error(result.toString());

        return result.toString();
    }
}
