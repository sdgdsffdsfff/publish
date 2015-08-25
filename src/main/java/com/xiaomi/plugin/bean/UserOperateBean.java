package com.xiaomi.plugin.bean;

import com.xiaomi.plugin.Constant;
import com.xiaomi.plugin.model.Operate;
import com.xiaomi.plugin.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;

/**
 * 判断当前用户是否有当前操作
 * Created by lijie on 2015-06-24.
 */
public class UserOperateBean extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        HttpSession session = ((PageContext) this.getJspContext()).getSession();
        User user = (User) session.getAttribute(Constant.CURRENT_USER);
        Set<Operate> operates = user.getOperateSet();

        JspFragment jf = this.getJspBody();
        StringWriter sw = new StringWriter();
        jf.invoke(sw);
        String content = sw.toString();
        for (Operate operate : operates) {
            if (content.contains(operate.getName())) {
                jf.invoke(this.getJspContext().getOut());
            }
        }
    }
}
