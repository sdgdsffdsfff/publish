package com.xiaomi.plugin.controller;

import com.xiaomi.plugin.model.FileList;
import com.xiaomi.plugin.service.FileListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文件具体信息
 * Created by lijie on 2015-06-24.
 */
@Controller
@RequestMapping(value = "/filelist")
public class FileListController {

    @Autowired
    private FileListService fileListService;

    @RequestMapping(value = "/get")
    @ResponseBody
    public Object get(String id) {
        FileList fileList = fileListService.get(Integer.valueOf(id));
        fileList.setName(fileList.getFileType().getName());
        fileList.setFileTypeId(fileList.getFileType().getId()+"");
        return fileList;
    }

}
