package com.xiaomi.plugin.dao;

import com.xiaomi.plugin.model.Pub;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 文件发布
 * Created by lijie on 2015-06-11.
 */
@Repository
public class PubDao extends BaseDao<Pub> {

    public long insertOrGet(String localid) {
        long timeMills = new Date().getTime() / 1000;
        try {
            int i = 0;
            String sql = "select time from delay where localid=?";
            Query query = getSession().createSQLQuery(sql);
            query.setString(i, localid);
            timeMills = Long.valueOf(query.uniqueResult().toString());
        } catch (Exception e) {
            int i = 0;
            String sql = "insert into delay(localid,time) values (?,?)";
            Query query = getSession().createSQLQuery(sql);
            query.setString(i++, localid);
            query.setLong(i, timeMills);
            query.executeUpdate();
        }
        return timeMills;
    }

    public void insertOrUpdate(String localid, Integer id) {
        try {
            int i = 0;
            String sql = "update pub_limit set num=num+1 where localid=? and pubid=?";
            Query query = getSession().createSQLQuery(sql);
            query.setString(i++, localid);
            query.setInteger(i, id);
            int k = query.executeUpdate();
            if (k == 0) {
                i = 0;
                sql = "INSERT INTO pub_limit(localid,pubid,num) VALUES (?,?,?)";
                query = getSession().createSQLQuery(sql);
                query.setString(i++, localid);
                query.setInteger(i++, id);
                query.setInteger(i, 1);
                query.executeUpdate();
            }
        } catch (Exception e) {

        }
    }

    public int getPubUsedNumber(Integer pubid) {
        try {
            int i = 0;
            String sql = "select count(*) from pub_limit where pubid=?";
            Query query = getSession().createSQLQuery(sql);
            query.setInteger(i, pubid);
            return Integer.valueOf(query.uniqueResult().toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public int getLocalUsedNumber(String localid, Integer pubId) {
        try {
            int i = 0;
            String sql = "select num from pub_limit where localid=? and pubid=?";
            Query query = getSession().createSQLQuery(sql);
            query.setString(i++, localid);
            query.setInteger(i, pubId);
            return Integer.valueOf(query.uniqueResult().toString());
        } catch (Exception e) {
            return 0;
        }
    }
}
