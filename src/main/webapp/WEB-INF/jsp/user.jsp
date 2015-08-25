<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>通用更新管理后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style1.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.reveal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/form/user.js"></script>
    <script language="javascript">
        var ss;
        window.onload = function () {
//var w=document.documentElement.clientWidth ;//可见区域宽度
            var h = document.documentElement.clientHeight;//可见区域高度
            ss = document.getElementById('scroll');
//alert(w);
//ss.style.width=w+"px";
            ss.style.height = h - 60 + "px";
        }
    </script>
</head>

<body>
<jsp:include page="top.jsp">
    <jsp:param name="index" value="4"/>
</jsp:include>
<div class="content_box clearfix">
    <div class="vmenu fl">
        <div class="mt80">
            <ul>
                <li><a href="/ip/form"><span>IP库管理</span></a></li>
                <li><a href="/usr/list" class="on"><span>权限管理</span></a></li>
            </ul>
        </div>
    </div>
    <div class="content fr" id="scroll">
        <div class="m20">
            <div class="con_box">
                <div class="p10">
                    <div class="add_box clearfix">
                        <div class="add_div fl">
                            <a href="#" data-reveal-id="addWindow" data-animation="fade" class="add_btn btn"><em>+</em>  新增</a>
                            <a href="#" data-reveal-id="delWindow" data-animation="fade" class="del_btn btn">批量删除</a>
                        </div>
                        <div class="search fr clearfix">
                            <form action="/usr/list" method="post">
                                <input type="text" name="namefield" id="namefield" class="search_input fl" value="请输入搜索关键字"  onfocus="if(value=='请输入搜索关键字') {value=''}"  onblur="if (value=='') {value='请输入搜索关键字'}"/>
                                <input type="submit" value="" onclick="return s();" class="search_btn fl"/>
                            </form>
                        </div>
                    </div>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list1">
                        <tr>
                            <th width="6%"><input type="checkbox" id="checkall"/> 全选</th>
                            <th width="4%">序号</th>
                            <th>用户名</th>
                            <th width="30%">登录名</th>
                            <th width="30%">操作</th>
                        </tr>

                        <c:forEach items="${userlist}" var="usr" varStatus="state">
                            <c:if test="${state.index%2==0}"> <tr class="tr1"></c:if>
                            <c:if test="${state.index%2==1}"> <tr class="tr2"></c:if>
                            <td><input type="checkbox" name="keys" value="${usr.id}"/></td>
                            <td>${state.index+1}</td>
                            <td>${usr.nickname}</td>
                            <td>${usr.username}</td>
                            <td class="operate">
                                <a href="#" data-reveal-id="authorityWindow" data-animation="fade" class="authority" key-id="${usr.id}" authority="<c:forEach items="${usr.operateSet}" var="operate">${operate.id},</c:forEach>">权限设置</a>
                                <a href="#" data-reveal-id="passwordWindow" data-animation="fade" class="password"
                                   key="${usr.id}">密码修改</a>
                                <a href="#" data-reveal-id="updateWindow" data-animation="fade" class="edit"
                                   data="${usr.nickname}#${usr.username}#${usr.id}">编辑</a>
                                <a href="/usr/delete?ids=${usr.id}" class="delete">删除</a>
                            </td>
                            </tr>
                        </c:forEach>

                    </table>
                    <div class="page_box">
                        <div id="pager" pagenumber="${pagenumber}" pagecount="${pagecount}" url="/usr/list"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--新增弹出层开始-->
<div class="reveal-modal" id="addWindow">
    <div class="modal_title">系统配置 - 用户管理 - <span>新增</span></div>
    <%--<a class="close-reveal-modal">×</a>--%>
    <!--<div class="form_tip">表单错误提示</div>-->
    <div class="formbox">
        <form method="post" action="/usr/add" id="add">
            <div class="r_form clearfix"><label class="labels_w">用户名：</label>
                <input type="text" name="nickname" class="inputs"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">登录名：</label>
                <input type="text" name="username" class="inputs"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">密码设置：</label>
                <input type="password" name="password" class="inputs">
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:document.getElementById('add').submit();" class="btn add_btn">保存</a>
                <a href="#" class="btn del_btn  close-reveal">关闭</a>
            </div>
        </form>
    </div>
</div>
<div class="reveal-modal" id="updateWindow">
    <div class="modal_title">系统配置 - 用户管理 - <span>修改</span></div>
    <%--<a class="close-reveal-modal">×</a>--%>
    <!--<div class="form_tip">表单错误提示</div>-->
    <div class="formbox">
        <form method="post" action="/usr/add" id="update">
            <div class="r_form clearfix"><label class="labels_w">用户名：</label>
                <input type="text" name="nickname" id="nickname" class="inputs"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">登录名：</label>
                <input type="text" name="username" id="username" class="inputs"/>
                <input type="hidden" name="id" id="id"/>
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:document.getElementById('update').submit();" class="btn add_btn">保存</a>
                <a href="#" class="btn del_btn  close-reveal">关闭</a>
            </div>
        </form>
    </div>
</div>
<!--新增弹出层结束-->

<!--删除弹出层开始-->
<div class="reveal-modal2" id="delWindow">
    <div class="del_text clearfix"><span>数据删除后将无法恢复，您是否确定要继续执行删除操作？</span></div>
    <div class="operation">
        <a href="javascript:rf();" class="btns add_btn close-reveal">确认</a>
        <a href="#" class="btns del_btn close-reveal">取消</a>
    </div>
</div>
<!--删除弹出层开始-->

<!--权限设置弹出层开始-->
<div class="reveal-modal" id="authorityWindow">
    <div class="modal_title">系统配置 - 权限管理 - <span>权限设置</span></div>
    <%--<a class="close-reveal-modal">×</a>--%>
    <!--<div class="form_tip">表单错误提示</div>-->
    <div class="formbox authority_style">
        <form method="post" id="authority">
            <input type="hidden" name="id" id="key-id"/>
            <div class="r_form clearfix">应用软件：<br/>
                <c:forEach items="${operateList}" var="operete" varStatus="state">
                    <c:if test="${operete.style eq '1'}">
                        <span><input type="checkbox" name="operete" value="${operete.id}"/> ${operete.name}</span>
                        <c:if test="${state.index%2==1}"><br></c:if>
                    </c:if>
                </c:forEach>
            </div>
            <div class="r_form clearfix">增值业务：<br/>
                <c:forEach items="${operateList}" var="operete"  varStatus="state">
                    <c:if test="${operete.style eq '2'}">
                        <span><input type="checkbox" name="operete" value="${operete.id}"/> ${operete.name}</span>
                        <c:if test="${state.index%2==1}"><br></c:if>
                    </c:if>
                </c:forEach>
            </div>
            <div class="r_form clearfix">
                <c:forEach items="${operateList}" var="operete"  varStatus="state">
                    <c:if test="${operete.style eq '3'}">
                        <span><input type="checkbox" name="operete" value="${operete.id}"/> ${operete.name}</span>
                        <c:if test="${state.index%2==1}"><br></c:if>
                    </c:if>
                </c:forEach>
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:au();" class="btn add_btn">保存</a>
                <a href="#" class="btn del_btn close-reveal">关闭</a>
            </div>
        </form>
    </div>
</div>
<!--权限设置弹出层开始-->

<!--密码修改弹出层开始-->

<div style="display: none;">
    <form id="rf" method="post" action="/usr/delete">
        <input type="hidden" name="ids" id="ids"/>
    </form>
</div>
<script type="text/javascript">
    $(function () {

        $("#checkall").bind("change", function () {
            if ($(this).attr("checked")) {
                $("input[name='keys']").attr("checked", true);
            } else {
                $("input[name='keys']").attr("checked", false);
            }
        });
    });

    /*
    * 删除
    * */
    function rf() {
        var keys = "";
        $("input[name='keys']").each(function () {
            if ($(this).attr("checked")) {
                keys += $(this).val() + ",";
            }
        });
        if (keys == "") {
            alert("请选择删除项!");
            return;
        }
        $("#ids").val(keys);
        $("#rf").submit();
    }

    /*
    * 权限
    * */
    function au(){
        var $authority =$('#authority');
        $.ajax({
            cache: true,
            type: "POST",
            url:"/usr/updateAu?"+$authority.serialize(),
            async: false,
            error: function(request) {
                alert("Connection error");
            },
            success: function(data) {
               if(data=="success"){
                   alert("权限设置成功!");
                   document.location.href="/usr/list"
               }
            }
        });
    }

</script>
</body>
</html>
