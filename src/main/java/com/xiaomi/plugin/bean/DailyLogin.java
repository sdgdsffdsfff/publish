package com.xiaomi.plugin.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.plugin.model.Region;
import com.xiaomi.plugin.service.RegionService;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 自动统计每日各地区登陆用户量
 * Created by lijie on 2015-06-15.
 */
public class DailyLogin extends QuartzJobBean {

    private RegionService regionService;
    private int pid;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String hql = "from Region where pid=?";
        Region region = getRegionService().getByHQL(hql, pid);

        int id = region.getId();
        String text = region.getText();
        String sql = "select count(*) from record where date=?";
        long total = getRegionService().countBySql(sql, simpleDateFormat.format(new Date()));
        text += "[" + total + "]";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[{");
        stringBuilder.append("\"id\":" + id + ",");
        stringBuilder.append("\"text\":\"" + text + "\",");

        List<Region> children = region.getChildren();
        stringBuilder.append(this.parseTreeJson(children));

        stringBuilder.append("}]");
        String Tree_FILE = IPSeeker.class.getResource("/tree.json").toString().substring(5);
        try {
            File treeFile = new File(Tree_FILE);
            PrintWriter ps = new PrintWriter(new FileOutputStream(treeFile));
            ps.println(stringBuilder.toString());
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //清空记录表 记录信息已经写入文件中
        sql = "delete from record";
        getRegionService().querySql(sql);
    }

    private String parseTreeJson(List<Region> regionList) {

        StringBuilder stringBuilder = new StringBuilder();

        if (regionList.size() > 0) {
            stringBuilder.append("\"children\":[");

            for (int i = 0; i < regionList.size(); i++) {
                Region region = regionList.get(i);
                int id = region.getId();
                String text = region.getText();
                String state = region.getState();

                String sql = "select count(*) from record where region like '%" + text + "%' and date=?";
                long total = regionService.countBySql(sql, simpleDateFormat.format(new Date()));
                text += "[" + total + "]";
                stringBuilder.append("{\"id\":" + id + ",");
                stringBuilder.append("\"text\":\"" + text + "\"");
                if (StringUtils.isNotEmpty(state)) {
                    stringBuilder.append(",");
                    stringBuilder.append("\"state\":\"closed\"");
                }


                String children = parseTreeJson(region.getChildren());
                if (StringUtils.isNotEmpty(children)) {
                    stringBuilder.append(",");
                    stringBuilder.append(children);
                }
                if (i == regionList.size() - 1) {
                    stringBuilder.append("}");
                } else {
                    stringBuilder.append("},");
                }
            }

            stringBuilder.append("]");
        }

        return stringBuilder.toString();
    }


    public RegionService getRegionService() {
        return regionService;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
