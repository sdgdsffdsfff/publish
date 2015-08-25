package com.xiaomi.plugin.service;

import com.xiaomi.plugin.exception.SameVersionException;
import com.xiaomi.plugin.model.FileList;
import com.xiaomi.plugin.model.FileType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 文件上传
 * Created by lijie on 2015-06-11.
 */
@Service
public class FileTypeService extends BaseService<FileType> {
    @Autowired
    private FileListService fileListService;

    public FileType saveFile(FileList fileList, String name, String tid, String fid) throws SameVersionException {

        FileType fileType = this.getByHQL("from FileType where name=? and style=?", name, fid);

        if (fileType == null) {
            fileType = new FileType();
            fileType.setName(name);
            fileType.setStyle(fid);
            fileType.setSid(tid);
            this.save(fileType);
        }

        FileList entity;
        if (fileList.getId() != null) {
            entity = fileListService.get(fileList.getId());
            entity.setUrl(fileList.getUrl());
            entity.setMd5(fileList.getMd5());
            entity.setVersion(fileList.getVersion());
            entity.setDesc(fileList.getDesc());
            entity.setPlus(fileList.getPlus());
            entity.setModel(fileList.getModel());
            entity.setCheck(fileList.getCheck());
            entity.setProcess(fileList.getProcess());
            entity.setUser(fileList.getUser());
        } else {
            entity = fileList;
            entity.setTime(new Date());
        }

        entity.setFileType(fileType);
        fileListService.saveFile(entity);

        return fileType;
    }


    public void deleteFile(String ids, String tid, String fid) {
        for (String id : ids.split(",")) {
            if (StringUtils.isNotEmpty(id)) {
                FileList fileList = fileListService.get(Integer.valueOf(id));
                FileType fileType = fileList.getFileType();
                if (fileType.getFileListSet().size() == 1) {
                    this.delete(fileType);
                } else {
                    fileListService.delete(fileList);
                }
            }
        }
    }


}
