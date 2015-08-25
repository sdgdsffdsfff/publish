<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>通用更新管理后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style1.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.reveal.js"></script>
    <script type="text/javascript">
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
                <li><a href="/ip/form" class="on"><span>IP库管理</span></a></li>
                <li><a href="/usr/list"><span>权限管理</span></a></li>
            </ul>
        </div>
    </div>
    <div class="content fr" id="scroll">
        <div class="m20">
            <div class="con_box">
                <div class="p10">
                    <div class="formbox">
                        <form method="post" action="/ip/upload" enctype="multipart/form-data" id="up">
                            <div class="r_form clearfix"><label class="labels_w">上传文件：</label><input type="file"
                                                                                                     name="file"
                                                                                                     class="inputs"/>
                            </div>
                            <div class="r_form clearfix"><label class="labels_w">上传时间：</label><input type="text"
                                                                                                     name="time"
                                                                                                     id="time"
                                                                                                     class="inputs"
                                                                                                     value="${fn:substring(ip.time,0 ,19 )}"/>
                            </div>
                            <div class="r_btn clearfix"><a href="javascript:document.getElementById('up').submit();"
                                                           class="btn add_btn">保存</a></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
