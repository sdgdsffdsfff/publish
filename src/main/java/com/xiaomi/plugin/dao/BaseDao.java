package com.xiaomi.plugin.dao;

import com.xiaomi.plugin.bean.PageResults;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用泛型dao
 * Created by lijie on 2015-06-10.
 */
public class BaseDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    protected Class clazz;


    public void save(T t) {
        this.getSession().save(t);
    }

    public void update(T t) {
        this.getSession().update(t);
    }

    public void saveOrUpdate(T t) {
        this.getSession().saveOrUpdate(t);
    }

    @SuppressWarnings("unchecked")
    public T load(int id) {
        return (T) this.getSession().load(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public T get(int id) {
        return (T) this.getSession().get(clazz, id);
    }

    public void delete(T t) {
        this.getSession().delete(t);
    }

    public void deleteById(int Id) {
        this.delete(this.load(Id));
    }

    /**
     * <执行Hql语句>
     *
     * @param hqlString hql
     * @param values    不定参数数组
     */
    public void queryHql(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        query.executeUpdate();
    }

    /**
     * <执行Sql语句>
     *
     * @param sqlString sql
     * @param values    不定参数数组
     */
    public void querySql(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);

        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        query.executeUpdate();
    }

    /**
     * <根据HQL语句查找唯一实体>
     *
     * @param hqlString HQL语句
     * @param values    不定参数的Object数组
     * @return 查询实体
     */
    @SuppressWarnings("unchecked")
    public T getByHQL(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (T) query.uniqueResult();
    }

    /**
     * <根据SQL语句查找唯一实体>
     *
     * @param sqlString SQL语句
     * @param values    不定参数的Object数组
     * @return 查询实体
     */
    @SuppressWarnings("unchecked")
    public T getBySQL(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (T) query.uniqueResult();
    }

    /**
     * <根据HQL语句，得到对应的list>
     *
     * @param hqlString HQL语句
     * @param values    不定参数的Object数组
     * @return 查询多个实体的List集合
     */
    @SuppressWarnings("unchecked")
    public List<T> getListByHQL(String hqlString, Object... values) {
        Query query = this.getSession().createQuery(hqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    /**
     * <根据SQL语句，得到对应的list>
     *
     * @param sqlString HQL语句
     * @param values    不定参数的Object数组
     * @return 查询多个实体的List集合
     */
    @SuppressWarnings("unchecked")
    public List<T> getListBySQL(String sqlString, Object... values) {
        Query query = this.getSession().createSQLQuery(sqlString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    /**
     * <根据HQL得到记录数>
     *
     * @param hql    HQL语句
     * @param values 不定参数的Object数组
     * @return 记录总数
     */
    public Long countByHql(String hql, Object... values) {
        Query query = this.getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (Long) query.uniqueResult();
    }

    /**
     * <根据SQL得到记录数>
     *
     * @param sql    HQL语句
     * @param values 不定参数的Object数组
     * @return 记录总数
     */
    public Long countBySql(String sql, Object... values) {
        Query query = this.getSession().createSQLQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return Long.valueOf(query.uniqueResult().toString());
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
    @SuppressWarnings("unchecked")
    public PageResults<T> findPageByFetchedHql(String hql, String countHql,
                                               int pageNo, int pageSize, Object... values) {
        PageResults<T> retValue = new PageResults<T>();
        Query query = this.getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        int currentPage = pageNo > 1 ? pageNo : 1;
        retValue.setCurrentPage(currentPage);
        retValue.setPageSize(pageSize);
        if (countHql == null) {
            ScrollableResults results = query.scroll();
            results.last();
            retValue.setTotalCount(results.getRowNumber() + 1);// 设置总记录数
        } else {
            Long count = countByHql(countHql, values);
            retValue.setTotalCount(count.intValue());
        }
        retValue.resetPageNo();
        List<T> itemList = query.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).list();
        if (itemList == null) {
            itemList = new ArrayList<T>();
        }
        retValue.setResults(itemList);

        return retValue;
    }

    /**
     * 根据id集合删除
     *
     * @param idsList 主键集合
     */
    public void deleteByIds(List<Integer> idsList) {
        String hql = "delete from " + clazz.getName() + " where id in(:ids)";
        Query query = this.getSession().createQuery(hql);
        query.setParameterList("ids", idsList);
        query.executeUpdate();
    }

    public BaseDao() {
        try {
            ResolvableType resolvableType = ResolvableType.forClass(this.getClass());
            clazz = resolvableType.getSuperType().getGeneric(0).resolve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
