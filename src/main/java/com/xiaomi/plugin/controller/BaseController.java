package com.xiaomi.plugin.controller;

import com.xiaomi.plugin.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用controller
 * Created by lijie on 2015-06-11.
 */
public class BaseController {

    @Autowired
    private HttpServletRequest request;

    public String getStrValue(String key) {
        return request.getParameter(key);
    }

    public Integer getIntValue(String key) {
        String strValue = request.getParameter(key);
        if (strValue == null) {
            return null;
        }
        return Integer.valueOf(strValue);
    }

    public int getPagenumber() {
        Object object = request.getParameter("pagenumber");
        if (object == null) {
            return 1;
        }
        return Integer.valueOf(object.toString());
    }


    public int getPagesize() {
        Object object = request.getParameter("pagesize");
        if (object == null) {
            return 8;
        }
        return Integer.valueOf(object.toString());
    }

    public String getSearchFiledValue() {
        return request.getParameter("namefield");
    }

}
