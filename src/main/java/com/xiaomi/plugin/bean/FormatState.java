package com.xiaomi.plugin.bean;

import com.xiaomi.plugin.Constant;
import com.xiaomi.plugin.model.Operate;
import com.xiaomi.plugin.model.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * 判断当前用户是否有当前操作
 * Created by lijie on 2015-06-24.
 */
public class FormatState extends SimpleTagSupport {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void doTag() throws JspException, IOException {

        JspFragment jf = this.getJspBody();
        StringWriter sw = new StringWriter();
        jf.invoke(sw);
        String content = sw.toString();
        long now = new Date().getTime();
        try {
            String state = content.split(",")[0];
            long start=simpleDateFormat.parse(content.split(",")[1]).getTime();
            long end=simpleDateFormat.parse(content.split(",")[2]).getTime();
            if("0".equals(state)){
                this.getJspContext().getOut().write("<td>停用</td>");
            }else {
                if(now<start){
                    this.getJspContext().getOut().write("<td>未发布</td>");
                }else if(now>end){
                    this.getJspContext().getOut().write("<td class=\"gray\">已过期</td>");
                }else {
                    this.getJspContext().getOut().write("<td class=\"green\">发布中</td>");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
