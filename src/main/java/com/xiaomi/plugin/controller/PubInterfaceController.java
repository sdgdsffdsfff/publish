package com.xiaomi.plugin.controller;

import com.xiaomi.plugin.bean.PubHelper;
import com.xiaomi.plugin.model.FileList;
import com.xiaomi.plugin.model.Pub;
import com.xiaomi.plugin.service.PubService;
import com.xiaomi.plugin.util.DESUtil;
import com.xiaomi.plugin.util.IPUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发布接口
 * Created by lijie on 2015-06-11.
 */
@Controller
public class PubInterfaceController extends BaseController {
    @Autowired
    private PubService pubService;

    @RequestMapping(value = "/upgrade.do")
    @ResponseBody
    public String upgrade(HttpServletRequest request) {
        //应用软件 style=1
        String version = getStrValue("version");
        String localid = getStrValue("localid");
        String agentid = getStrValue("agentid");
        String userId=getStrValue("UserId");
        String sid = getStrValue("sid");
        if (sid == null) {
            return "[]";//empty sid
        }

        if (localid == null) {
            return "[]";//empty localid
        }

        String hql = "from Pub where sid=? and style=? and startTime<=? and endTime>=? and state=?";

        Date date = new Date();

        List<Pub> pubList = pubService.getListByHQL(hql, sid, "1", date, date, "1");

        if (pubList.size() < 1) {
            return "[]";//Expired pub or error sid
        }
        return this.pub(pubList, version, localid, agentid,userId ,request);
    }


    @RequestMapping(value = "/resource.do")
    @ResponseBody
    public String plugins(HttpServletRequest request) {
        String version = getStrValue("version");
        String localid = getStrValue("localid");
        String agentid = getStrValue("agentid");
        String userId=getStrValue("UserId");
        String sid = getStrValue("sid");
        if (sid == null) {
            return "[]";//empty sid
        }

        if (localid == null) {
            return "[]";//empty localid
        }

        String hql = "from Pub where sid=? and style=? and startTime<=? and endTime>=? and state=?";

        Date date = new Date();

        List<Pub> pubList = pubService.getListByHQL(hql, sid, "2", date, date, "1");
        if (pubList.size() < 1) {
            return "[]";//Expired pub or error sid
        }
        return this.pub(pubList, version, localid, agentid, userId,request);
    }

    @RequestMapping(value = "/plugins.do")
    @ResponseBody
    public String resource(HttpServletRequest request) {
        String version = getStrValue("version");
        String localid = getStrValue("localid");
        String agentid = getStrValue("agentid");
        String userId=getStrValue("UserId");
        String sid = getStrValue("sid");

        if (sid == null) {
            sid = "zzyw";
        }

        String hql = "from Pub where sid=? and style in(? ,?) and startTime<=? and endTime>=? and state=? order by style desc";

        Date date = new Date();

        List<Pub> pubList = pubService.getListByHQL(hql, sid, "3", "4", date, date, "1");

        if (pubList.size() < 1) {
            return "[]";//Expired pub
        }
        return this.pub(pubList, version, localid, agentid,userId, request);
    }

    @SuppressWarnings("all")
    private String pub(List<Pub> list, String version, String localid, String agentid,String userId, HttpServletRequest request) {
        //这里根据发布延迟
        // 限制次数做筛选
        List<Pub> newList = new ArrayList<Pub>();
        for (Pub pub : list) {
            int delay = pub.getDelay() == null ? 0 : pub.getDelay();
            int limit = pub.getLimit() == null ? 0 : pub.getLimit();
            int number = pub.getNumber() == null ? 0 : pub.getNumber();
            //判断当前发布是否有延迟
            if (delay > 0) {
                long fistGetTimeMills = pubService.insertORGet(localid);
                Long nowMillis = new Date().getTime()/1000;
                Long delayDays = (nowMillis - fistGetTimeMills) / 86400;
                if (delayDays < delay) continue;
            }
            //判断是否有个数限制
            if (limit > 0) {
                //得到当前资源已经被使用的次数
                int pubUsedNumber = pubService.getPubUsedNumber(pub.getId());
                if (pubUsedNumber >= limit) continue;
            }
            //判断是否有机子获取限制
            if (number > 0) {
                int localUserNumber = pubService.getLocalUsedNumber(localid,pub.getId());
                if (localUserNumber >= number) continue;
            }
            newList.add(pub);
        }

        //这里根据发布范围做筛选(最终筛选)
        String ip = IPUtil.getIp(request);
        String address = IPUtil.getIPAddress(ip);
        PubHelper<String, FileList> pubPubHelper = new PubHelper<String, FileList>();
        pubPubHelper.put(newList, version, localid, agentid,userId, address);

        //将使用过的发布 记录下被使用过的次数 limit>0 有限制次数 number>0 有获取次数
        for (Pub pub : newList) {
            if (pub.getLimit() > 0 || pub.getNumber() > 0) {
                if (pub.isUsed()) {
                    pubService.updatePubLimt(localid, pub.getId());
                }
            }
        }

        //记录当前机子的访问信息,做每日登陆量统计
        String sql = "insert into record (ip,region,date) values(?,?,?)";
        pubService.querySql(sql, ip, address, new Date());

        //符合条件的发布下的资源
        String pubResult = "";
        for (FileList fileList : pubPubHelper.values()) {
            pubResult += "[" + fileList.getFileType().getName() + "]\n";
            pubResult += "version=" + fileList.getVersion() + "\n";
            pubResult += "VerCheckMode=" + fileList.getModel() + "\n";
            String process = fileList.getProcess();
            pubResult += "Protect=" + (StringUtils.isEmpty(process) ? "0" : process.contains(",") ? "3" : process) + "\n";
            pubResult += "url=" + fileList.getUrl() + "\n";
            String check = fileList.getCheck();
            if ("1".equals(check)) {
                pubResult += "md5=" + fileList.getMd5() + "\n";
            }
            if (fileList.getStart() != null) {
                pubResult += "RunMode=" + fileList.getStart() + "\n";
            }
            pubResult += "size=" + fileList.getSize() + "\n";
            String plus = fileList.getPlus();
            if (StringUtils.isNotEmpty(plus)) {
                pubResult += plus + "\n";
            }
        }
        return "[" + DESUtil.encrypt(pubResult, DESUtil.GenerateKey(localid.toCharArray())) + "]";
    }

}
