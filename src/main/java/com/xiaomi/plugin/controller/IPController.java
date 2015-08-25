package com.xiaomi.plugin.controller;

import com.xiaomi.plugin.Constant;
import com.xiaomi.plugin.bean.IPSeeker;
import com.xiaomi.plugin.model.IP;
import com.xiaomi.plugin.model.User;
import com.xiaomi.plugin.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * ip库管理
 * Created by lijie on 2015-06-16.
 */
@Controller
@RequestMapping(value = "/ip")
public class IPController {
    @Autowired
    private IPSeeker ipSeeker;
    @Autowired
    private IpService ipService;

    @RequestMapping(value = "/upload")
    public String upload(MultipartFile file) throws IOException {
        ipSeeker.destroy();
        String ipFilePath = IPController.class.getResource("/").toString().substring(5);
        File desFile = new File(ipFilePath + "/ip.dat");
        file.transferTo(desFile);
        ipSeeker.init();
        try {
            IP ip = new IP();
            ip.setTime(new Date());
            ipService.save(ip);
        }catch (Exception e){

        }
        return "redirect:/ip/form";
    }

    @RequestMapping(value = "/form")
    public ModelAndView form() {
        String sql = "from IP order by time desc limit 0,1";
        List<IP> ipList = ipService.getListByHQL(sql);

        ModelMap modelMap = new ModelMap();
        if (ipList.size() > 0) {
            modelMap.put("ip", ipList.get(0));
        }

        return new ModelAndView("ip", modelMap);
    }
}
