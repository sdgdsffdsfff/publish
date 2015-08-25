<%--
  Created by IntelliJ IDEA.
  User: lijie
  Date: 2015-08-14
  Time: 上午 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>通用更新管理后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/ui/themes/gray/easyui.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/ui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
<%--north--%>
<div data-options="region:'north',border:false" style="height:60px;overflow: hidden;margin: 0;padding: 0;">
    <div class="top_box clearfix">
        <div class="logo fl"></div>
        <div class="menus fl">
            <ul class="clearfix">
                <li><a href="/view/app" class="on"><i class="software"></i>应用软件</a></li>
                <li><a href="/view/bus"><i class="business"></i>增值业务</a></li>
                <li><a href="/agent/list"><i class="agent"></i>代理商管理</a></li>
                <li><a href="/ip/form"><i class="system"></i>系统设置</a></li>
            </ul>
        </div>
        <div class="exit fr">您好！<span>${sessionScope.currentLoginUser.username}</span>
            <a href="#">修改密码</a>
            <a href="/usr/logout">退出登录</a>
        </div>
    </div>
</div>
<%--west--%>
<div data-options="region:'west',border:false" style="width:11.45%;margin: 0;padding: 0;">
    <div class="vmenu">
        <div class="mt80">
            <div class="soft_add">
                <a href="#"><span>管理软件类型</span></a>
            </div>
            <ul>
                <c:forEach items="${type}" var="ty">
                    <li><a href="/view/app?tid=${ty.name}&fid=1" <c:if test="${tid eq ty.name}"> class="on" </c:if>><span>${ty.desc}</span></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<%--center--%>
<div data-options="region:'center',border:false">
    <iframe src="/filetype/list?tid=${tid}&fid=${fid}" width="100%" height="99%" frameborder="0"></iframe>
</div>
</body>
</html>
