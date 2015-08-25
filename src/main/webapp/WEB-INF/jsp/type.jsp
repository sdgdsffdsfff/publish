<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="has" uri="http://hasOperate.com" %>
<%@ taglib prefix="fmtstat" uri="http://formatState.com" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>通用更新管理后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style1.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <%--弹出表单 表单回显--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.reveal.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/form/type.js"></script>

    <%--分页插件--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/page/jquery.pager.js"></script>

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
    <jsp:param name="index" value="1"/>
</jsp:include>
<div class="content_box">
    <div class="content2" id="scroll">
        <div class="m20">
            <div class="con_box">
                <div class="p10">
                    <div class="add_box clearfix">
                        <div class="add_div fl">
                            <a href="#" data-reveal-id="addWindow" data-animation="fade" class="add_btn btn"> <em>+</em>
                                新增 </a>
                            <a href="#" data-reveal-id="delWindow" data-animation="fade" class="del_btn btn">批量删除</a>
                        </div>
                        <div class="search fr clearfix"><input type="text" name="textfield" id="textfield"
                                                               class="search_input fl" value="请输入搜索关键字"
                                                               onfocus="if(value=='请输入搜索关键字') {value=''}"
                                                               onblur="if (value=='') {value='请输入搜索关键字'}"/><input
                                type="submit" name="button" id="button" value="" class="search_btn fl"
                                onclick="window.location.href='search_list.html'"/>
                        </div>
                    </div>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list1">
                        <tr>
                            <th width="6%"><input type="checkbox" id="checkall"/> 全选</th>
                            <th width="4%">序号</th>
                            <th width="17%">类型描述</th>
                            <th width="17%">软件类型</th>
                            <th width="17%">排序</th>
                            <th width="13%">操作</th>
                        </tr>

                        <c:forEach items="${typeList}" var="type" varStatus="state">
                            <tr class="tr2">
                                <td><input type="checkbox" name="keys" value="${type.id}"/></td>
                                <td>${state.index+1}</td>
                                <td>${type.desc}</td>
                                <td>${type.name}</td>
                                <td>${type.orderNO}</td>
                                <td class="operate">
                                    <a href="#" data-reveal-id="addWindow" data-animation="fade" data="${type.name}#${type.desc}#${type.orderNO}#${type.id}" class="edit">编辑</a>
                                    <a href="/type/delete?ids=${type.id}" class="delete">删除</a>
                                </td>
                            </tr>
                        </c:forEach>

                    </table>
                    <div class="page_box">
                        <div id="pager" pagenumber="${pagenumber}" pagecount="${pagecount}"  url="/type/list"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--新增弹出层开始-->
<div class="reveal-modal" id="addWindow">
    <div class="modal_title">应用软件 - 软件类型 - <span>新增</span></div>
    <!--<div class="form_tip">表单错误提示</div>-->
    <div class="formbox">
        <form method="post" id="add" action="/type/add">
            <div class="r_form clearfix"><label class="labels_w">软件类型：</label>
                <input type="text" name="name" id="name" class="inputs"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">类型描述：</label>
                <textarea name="desc" id="desc" cols="45" rows="3"></textarea>
            </div>
            <div class="r_form clearfix"><label class="labels_w">排序：</label>
                <input type="text" name="orderNO" id="orderNO" class="inputs plus">
                <input type="hidden" name="id" id="id"/>
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:document.getElementById('add').submit();" class="btn add_btn">保存</a>
                <a href="#" class="btn del_btn close-reveal">关闭</a>
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
<%--刷新当前页面--%>
<div style="display: none;">
    <form id="rf" method="post" action="/type/delete">
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
</script>
</body>
</html>
