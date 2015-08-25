package com.xiaomi.plugin.service;

import com.xiaomi.plugin.bean.PageResults;
import com.xiaomi.plugin.dao.BaseDao;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用泛型service
 * Created by lijie on 2015-06-11.
 */
public class BaseService<T> {

    @Autowired
    private BaseDao<T> baseDao;

    public void save(T t) {
        baseDao.save(t);
    }

    public void update(T t) {
        baseDao.update(t);
    }

    public void saveOrUpdate(T t) {
        baseDao.saveOrUpdate(t);
    }

    @SuppressWarnings("unchecked")
    public T get(int id) {
        return (T) baseDao.get(id);
    }

    public void delete(T t) {
        baseDao.delete(t);
    }

    public void deleteById(int Id) {
        this.delete(baseDao.load(Id));
    }

    /**
     * <执行Hql语句>
     *
     * @param hqlString hql
     * @param values    不定参数数组
     */
    public void queryHql(String hqlString, Object... values) {
        baseDao.queryHql(hqlString, values);
    }

    /**
     * <执行Sql语句>
     *
     * @param sqlString sql
     * @param values    不定参数数组
     */
    public void querySql(String sqlString, Object... values) {
        baseDao.querySql(sqlString, values);
    }

    /**
     * <根据HQL语句查找唯一实体>
     *
     * @param hqlString HQL语句
     * @param values    不定参数的Object数组
     * @return 查询实体
     */
    public T getByHQL(String hqlString, Object... values) {
        return baseDao.getByHQL(hqlString, values);
    }

    /**
     * <根据SQL语句查找唯一实体>
     *
     * @param sqlString SQL语句
     * @param values    不定参数的Object数组
     * @return 查询实体
     */
    public T getBySQL(String sqlString, Object... values) {
        return baseDao.getBySQL(sqlString, values);
    }

    /**
     * <根据HQL语句，得到对应的list>
     *
     * @param hqlString HQL语句
     * @param values    不定参数的Object数组
     * @return 查询多个实体的List集合
     */
    public List<T> getListByHQL(String hqlString, Object... values) {
        return baseDao.getListByHQL(hqlString, values);
    }

    /**
     * <根据SQL语句，得到对应的list>
     *
     * @param sqlString HQL语句
     * @param values    不定参数的Object数组
     * @return 查询多个实体的List集合
     */
    public List<T> getListBySQL(String sqlString, Object... values) {
        return baseDao.getListBySQL(sqlString, values);
    }

    /**
     * <根据HQL得到记录数>
     *
     * @param hql    HQL语句
     * @param values 不定参数的Object数组
     * @return 记录总数
     */
    public Long countByHql(String hql, Object... values) {
        return baseDao.countByHql(hql, values);
    }

    /**
     * <根据SQL得到记录数>
     *
     * @param sql    HQL语句
     * @param values 不定参数的Object数组
     * @return 记录总数
     */
    public Long countBySql(String sql, Object... values) {
        return baseDao.countBySql(sql, values);
    }

    /**
     * <HQL分页查询>
     *
     * @param hql      HQL语句
     * @param countHql 查询记录条数的HQL语句
     * @param pageNo   下一页
     * @param pageSize 一页总条数
     * @param values   不定Object数组参数
     * @return PageResults的封装类，里面包含了页码的信息以及查询的数据List集合
     */
    public PageResults<T> findPageByFetchedHql(String hql, String countHql,
                                               int pageNo, int pageSize, Object... values) {
        return baseDao.findPageByFetchedHql(hql, countHql, pageNo, pageSize, values);
    }

    public void deleteByIds(List<Integer> idsList) {
        baseDao.deleteByIds(idsList);
    }

}
