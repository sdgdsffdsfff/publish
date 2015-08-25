<%@ taglib prefix="has" uri="http://hasOperate.com" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.setAttribute("index", request.getParameter("index"));%>
<div class="top_box clearfix">
    <div class="logo fl"></div>
    <div class="menu fl">
        <ul class="clearfix">
            <li><a href="/view/app" <c:if test="${index==1}"> class="on" </c:if> ><i class="software"></i>应用软件</a>
            </li>
            <li><a href="/view/bus?tid=zzyw&fid=3" <c:if test="${index==2}"> class="on" </c:if>><i  class="business"></i>增值业务</a></li>
            <has:operate>
                <li><a href="/agent/list" <c:if test="${index==3}"> class="on" </c:if>><i class="agent"></i>代理商管理</a>
                </li>
            </has:operate>
            <has:operate>
                <li><a href="/ip/form" <c:if test="${index==4}"> class="on" </c:if>><i class="system"></i>系统设置</a></li>
            </has:operate>
        </ul>
    </div>
    <div class="exit fr">您好！<span>${sessionScope.currentLoginUser.username}</span>
        <a href="#" data-reveal-id="passwordWindow" data-animation="fade"   key="${sessionScope.currentLoginUser.id}">修改密码</a>
        <a href="/usr/logout">退出登录</a>
    </div>
</div>
<!--密码修改弹出层开始-->
<div class="reveal-modal" id="passwordWindow">
    <div class="modal_title">系统配置 - 用户管理 - <span>密码修改</span></div>
    <%--<a class="close-reveal-modal">×</a>--%>
    <!--<div class="form_tip">表单错误提示</div>-->
    <div class="formbox">
        <form method="post" id="sf">
            <div class="r_form clearfix"><label class="labels_w">原密码：</label>
                <input type="password" name="oldpassword" id="oldpassword" class="inputs"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">新密码：</label>
                <input type="password" name="newpassword" id="newpassword" class="inputs"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">确认密码：</label>
                <input type="password" name="confirmpassword" id="confirmpassword" class="inputs">
                <input type="hidden" name="id" id="key" value="${sessionScope.currentLoginUser.id}"/>
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:sf();" class="btn add_btn">保存</a>
                <a href="#" class="btn del_btn close-reveal">关闭</a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    /*
     * 密码修改
     * */
    function sf() {
        var oldpassword = $("#oldpassword").val();
        var newpassword = $("#newpassword").val();
        var confirmpassword = $("#confirmpassword").val();
        if (oldpassword == "" || newpassword == "" || confirmpassword == "") {
            return;
        }
        if (newpassword != confirmpassword) {
            alert("两次输入密码不一致!");
        } else {
            var $sf = $('#sf');
            $.ajax({
                cache: true,
                type: "POST",
                url: "/usr/update?" + $sf.serialize(),
                async: false,
                error: function (request) {
                    alert("Connection error");
                },
                success: function (data) {
                    if (data == "error") {
                        alert("原密码不正确!")
                    } else {
                        $(".close-reveal").trigger("click");
                        alert("密码修改成功!");
                        $sf.get(0).reset();
                    }
                }
            });
        }
    }
    /*
     * 搜索功能
     * */
    function s() {
        return $("#namefield").val() != "请输入搜索关键字";
    }
</script>