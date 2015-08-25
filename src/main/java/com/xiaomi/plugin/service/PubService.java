package com.xiaomi.plugin.service;

import com.xiaomi.plugin.dao.PubDao;
import com.xiaomi.plugin.model.Pub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 文件发布
 * Created by lijie on 2015-06-11.
 */
@Service
public class PubService extends BaseService<Pub> {
    @Autowired
    private PubDao pubDao;
    public long insertORGet(String localid) {
        return pubDao.insertOrGet(localid);
    }

    public void updatePubLimt(String localid, Integer id) {
        pubDao.insertOrUpdate(localid,id);
    }

    public int getPubUsedNumber(Integer pubid) {
        return pubDao.getPubUsedNumber(pubid);
    }

    public int getLocalUsedNumber(String localid,Integer pubId) {
        return pubDao.getLocalUsedNumber(localid ,pubId);
    }
}
