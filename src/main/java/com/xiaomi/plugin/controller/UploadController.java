package com.xiaomi.plugin.controller;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.xiaomi.plugin.Constant;
import com.xiaomi.plugin.util.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理文件上传类
 * Created by lijie on 2015-06-26.
 */
@Controller
public class UploadController {

    private static final String ACCESS_ID = "jiwRJwwytHFTZ0Ee";
    private static final String ACCESS_KEY = "cC8BK4316r5CmmUKBkN6Lc81ecdYaz";
    private static final String OSS_ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
    private static final String BUCKETNAME = "xmnet";
    private static final long PART_SIZE = 5 * 1024 * 1024L;
    private static final int CONCURRENCIES = 2;

    /**
     * 处理文件上传类
     *
     * @param file 文件
     * @param isMd 是否计算当前文件的md5值 1计算
     * @return
     */
    @RequestMapping(value = "/upload")
    @ResponseBody
    public Map upload(MultipartFile file, String isMd, String isSize, HttpServletRequest request) {

        return localUpload(file, isMd, isSize, request);
//        return ossUpload(file, isMd, isSize, request);
    }

    private Map localUpload(MultipartFile file, String isMd, String isSize, HttpServletRequest request) {
        String basePath = request.getSession().getServletContext().getRealPath(Constant.UP_PATH);
        String fileName = String.valueOf(new Date().getTime()) + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File desFile = new File(basePath + "/" + fileName);
        if (!desFile.exists()) desFile.mkdirs();

        Map<String, String> map = new HashMap<String, String>();

        try {
            file.transferTo(desFile);

            map.put("url", "http://localhost:8080/resources/upload/" + fileName);

            if ("1".equals(isMd)) {
                map.put("md5", MD5Util.getFileMD5String(desFile));
            }

            if("1".equals(isSize)){
                map.put("size",file.getSize()+"");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private Map ossUpload(MultipartFile file, String isMd, String isSize, HttpServletRequest request) {

        ClientConfiguration config = new ClientConfiguration();

        OSSClient ossClient = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY, config);

        String fileName = String.valueOf(new Date().getTime()) + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.getSize());

        Map<String, String> map = new HashMap<String, String>();


        try {
            PutObjectResult putObjectResult = ossClient.putObject(BUCKETNAME, fileName, file.getInputStream(), objectMeta);

            map.put("url", "http://oss.xiaominet.com/" + fileName);

            if ("1".equals(isMd)) {

                map.put("md5", putObjectResult.getETag());
            }

            if("1".equals(isSize)){
                map.put("size",file.getSize()+"");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
