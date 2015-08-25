package com.xiaomi.plugin.bean;

import com.xiaomi.plugin.model.FileList;
import com.xiaomi.plugin.model.Pub;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 文件发布优先级判断
 * Created by lijie on 2015-06-12.
 */
public class PubHelper<K, V> extends HashMap<K, V> {
    /**
     * 1=目标机器 localId  2= 产品版本 version  3 =代理商 agent  4 = 区域 region
     * 1=目标机器 localId  2= 产品版本 version   3=目标网吧 4 =代理商 agent  5= 区域 region
     *
     * @param list    所有符合条件的发布
     * @param version 版本
     * @param localid 目标机器
     * @param agentid 代理号
     * @param address 用户的address
     */
    @SuppressWarnings("unchecked")
    public void put(List<Pub> list, String version, String localid, String agentid, String barid, String address) {
        int i = 0;//filelist 放入的顺序
        for (Pub pub : list) {
            String rangType = pub.getRangeType();
            String rangValue = pub.getRangeValue();
            int intRangType = Integer.valueOf(rangType);
            Set<FileList> fileSet = pub.getFileListSet();
            FileList model;
            boolean isUsed = false;//当前发布是否被使用一次
            for (FileList file : fileSet) {
                file.setOrderNo(i++);
                String name = file.getFileType().getName();
                String key = name + "-" + rangType;
                file.setStart(pub.getStart());//发布接口获取是需要的参数 commonPub里面 生成ini字符串时候不使用CommonPub 保存在当前对象中
                switch (intRangType) {
                    case 1:
                        if (StringUtils.isNotEmpty(localid) && rangValue.contains(localid)) {
                            //放入map  方便判断优先级
                            super.put((K) key, (V) file);
                            //放入优先级为1的 过后 移除优先级比1低的
                            super.remove(name + "-2");
                            super.remove(name + "-3");
                            super.remove(name + "-4");
                            super.remove(name + "-5");
                            isUsed = true;
                        }
                        break;
                    case 2:
                        model = (FileList) this.get(name + "-1");
                        if (model == null) {
                            if (StringUtils.isNotEmpty(version) && rangValue.contains(version)) {
                                super.put((K) key, (V) file);
                                //放入优先级为2的 过后 移除优先级比2低的
                                super.remove(name + "-3");
                                super.remove(name + "-4");
                                super.remove(name + "-5");
                                isUsed = true;

                            }
                        }
                        break;
                    case 3:
                        model = (FileList) this.get(name + "-1");
                        if (model == null) {
                            model = (FileList) this.get(name + "-2");
                            if (model == null) {
                                if (StringUtils.isNotEmpty(barid) && rangValue.contains(barid)) {
                                    super.put((K) key, (V) file);
                                    //放入优先级为3的 过后 移除优先级比3低的
                                    super.remove(name + "-4");
                                    super.remove(name + "-5");
                                    isUsed = true;

                                }
                            }
                        }
                        break;
                    case 4:
                        model = (FileList) this.get(name + "-1");
                        if (model == null) {
                            model = (FileList) this.get(name + "-2");
                            if (model == null) {
                                model = (FileList) this.get(name + "-3");
                                if (model == null) {
                                    if (StringUtils.isNotEmpty(agentid) && rangValue.contains(agentid)) {
                                        super.put((K) key, (V) file);
                                        //放入优先级为3的 过后 移除优先级比3低的
                                        super.remove(name + "-5");
                                        isUsed = true;
                                    }
                                }
                            }
                        }
                        break;
                    case 5:
                        model = (FileList) this.get(name + "-1");
                        if (model == null) {
                            model = (FileList) this.get(name + "-2");
                            if (model == null) {
                                model = (FileList) this.get(name + "-3");
                                if (model == null) {
                                    model = (FileList) this.get(name + "-4");
                                    if (model == null) {
                                        if (isContain(address, rangValue)) {
                                            super.put((K) key, (V) file);
                                            isUsed = true;
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            if (isUsed) {
                pub.setUsed(true);
            }
        }
    }

    public Collection<V> values() {
        List<V> list = new ArrayList<V>(super.values());
        Collections.sort(list, new Comparator<V>() {
            public int compare(V o1, V o2) {
                FileList f1 = (FileList) o1;
                FileList f2 = (FileList) o2;
                return f1.getOrderNo() - f2.getOrderNo();
            }
        });
        return list;
    }

    private boolean isContain(String v1, String v2) {
        if (StringUtils.isEmpty(v1) || StringUtils.isEmpty(v2)) {
            return false;
        }
        for (String val1 : v1.split(",")) {
            for (String val2 : v2.split(",")) {
                if (val1.contains(val2) || val2.contains(val1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
