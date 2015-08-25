<%--
  Created by IntelliJ IDEA.
  User: lijie
  Date: 2015-08-14
  Time: 上午 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="has" uri="http://hasOperate.com" %>
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
                <li><a href="/view/app"><i class="software"></i>应用软件</a></li>
                <li><a href="/view/bus"  class="on"><i class="business"></i>增值业务</a></li>
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
        <div class="dd">
            <ul>
                <has:operate>
                    <li><a hrefs="/filetype/list?tid=zzyw&fid=3"  class="on rf" ><span>插件管理</span></a></li>
                </has:operate>
                <has:operate>
                    <li><a hrefs="/filetype/list?tid=zzyw&fid=4"  class="rf" ><span>资源管理</span></a></li>
                </has:operate>
                <has:operate>
                    <li><a hrefs="/pub/list?tid=zzyw&fid=3" class="rf" ><span>插件发布管理</span></a></li>
                </has:operate>
                <has:operate>
                    <li><a hrefs="/pub/list?tid=zzyw&fid=4" class="rf" ><span>资源发布管理</span></a></li>
                </has:operate>
            </ul>
        </div>
    </div>
</div>
<%--center--%>
<div data-options="region:'center',border:false">
    <iframe id="_iframe" src="/filetype/list?tid=zzyw&fid=3" width="100%" height="99%" frameborder="0"></iframe>
</div>
</body>
<script type="text/javascript">

    $(function(){
       $(".rf").on("click", function () {
          $(".rf").removeClass("on");
           $(this).addClass("on");
           $("#_iframe").attr("src",$(this).attr("hrefs"));
       })
    });

</script>
</html>
