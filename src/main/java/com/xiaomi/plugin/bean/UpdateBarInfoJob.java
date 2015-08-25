package com.xiaomi.plugin.bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi.plugin.model.Bar;
import com.xiaomi.plugin.service.BarService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 更新网吧信息
 * Created by lijie on 2015-08-13.
 */
public class UpdateBarInfoJob extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger(UpdateBarInfoJob.class);

    private BarService barService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            URL url = new URL("http://db.nmenu.cn/wb/search7daynetbarinfo.ashx?ucid=e255cfa8-2c08-4878-9b52-8ac259a697a2&a=NZRi3TPLLHc=");    // 把字符串转换为URL请求地址
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();// 打开连接
            httpURLConnection.connect();// 连接会话

            ObjectMapper objectMapper = new ObjectMapper();

            List<Bar> barList = objectMapper.readValue(httpURLConnection.getInputStream(), new TypeReference<List<Bar>>() {
            });

            httpURLConnection.disconnect();// 断开连接

            for (Bar bar : barList) {
                Bar entity = barService.get(bar.getId());
                if (entity != null) {
                    barService.update(bar);
                } else {
                    barService.save(bar);
                }

            }
            LOGGER.error("更新数据库网吧信息完成");
        } catch (Exception e) {
            LOGGER.error("更新数据库网吧信息失败");
        }
    }

    public BarService getBarService() {
        return barService;
    }

    public void setBarService(BarService barService) {
        this.barService = barService;
    }
}
