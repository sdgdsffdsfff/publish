package com.xiaomi.plugin.util;

import com.xiaomi.plugin.bean.IPSeeker;
import com.xiaomi.plugin.model.Region;
import com.xiaomi.plugin.service.RegionService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class IPSeekerTest {
    @Autowired
    private RegionService regionService;
    @Autowired
    private IPSeeker ipSeeker;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void methodTest() {
//        String hql = "from Region where pid=? and id<?";
//        Region region = regionService.getByHQL(hql, 405, 20);
//        System.out.println(region);
//        System.out.println(java.sql.Date.valueOf("2015-05-02"));
    }

    @Test
    public void regionTest() {

        String hql = "from Region where pid=?";
        Region region = regionService.getByHQL(hql, -1);

        int id = region.getId();
        String text = region.getText();
        String sql = "select count(*) from record where date=?";
        long total = regionService.countBySql(sql, simpleDateFormat.format(new Date()));
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
            PrintStream ps = new PrintStream(new FileOutputStream(treeFile));
            ps.println(stringBuilder.toString());
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Test
    public void IpTest() {
        System.out.println(ipSeeker.getAddress("219.78.113.243"));
        System.out.println(ipSeeker.getAddress("222.74.181.47"));
        System.out.println(ipSeeker.getAddress("59.172.141.77"));
    }

}