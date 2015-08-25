package com.xiaomi.plugin.service;

import com.xiaomi.plugin.dao.FileListDao;
import com.xiaomi.plugin.exception.SameVersionException;
import com.xiaomi.plugin.model.FileList;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文件具体信息服务类
 * Created by lijie on 2015-06-24.
 */
@Service
public class FileListService extends BaseService<FileList> {
    @Autowired
    private FileListDao fileListDao;

    public void saveFile(FileList entity)throws SameVersionException{
        try {
            super.saveOrUpdate(entity);
        } catch (ConstraintViolationException e) {
            throw new SameVersionException("版本号重复");
        }
    }
}
