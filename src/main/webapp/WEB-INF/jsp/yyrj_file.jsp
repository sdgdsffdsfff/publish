<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="has" uri="http://hasOperate.com" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>通用更新管理后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/ui/themes/gray/easyui.css" rel="stylesheet"   type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/ui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/page/jquery.pager.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/js.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/form/yyrj_file.js"></script>
</head>

<body>

<div class="content">
        <div class="m20">
            <div class="nav">
                <has:operate> <a href="/filetype/list?tid=${tid}&fid=1" <c:if test="${fid==1}"> class="on" </c:if>>更新包管理</a></has:operate>
                <has:operate><a href="/filetype/list?tid=${tid}&fid=2" <c:if test="${fid==2}"> class="on" </c:if>>资源包管理</a></has:operate>
                <has:operate><a href="/pub/list?tid=${tid}&fid=1" >更新发布管理</a></has:operate>
                <has:operate> <a href="/pub/list?tid=${tid}&fid=2">资源发布管理</a></has:operate>
            </div>
            <div class="con_box">
                <div class="p10">
                    <div class="add_box clearfix">
                        <div class="add_div fl">
                            <a href="javascript:void (0)" onclick="$('#addWindow').window('open')" class="add_btn btn"> <em>+</em> 新增 </a>
                            <a href="javascript:void (0)" class="del_btn btn">批量删除</a>
                        </div>
                        <div class="search fr clearfix">
                            <form action="/filetype/list?tid=${tid}&fid=${fid}" method="post" id="search">
                                <input class="easyui-searchbox" value="${search}" name="namefield" data-options="prompt:'输入资源名称',searcher:doSearch" style="width: 300px;height: 34px;"/>
                            </form>
                        </div>
                    </div>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list2 table">
                        <thead>
                        <tr>
                            <th width="5%"><input type="checkbox" id="checkall"/> 全选</th>
                            <th width="5%">序号</th>
                            <th width="10%">资源版本</th>
                            <th width="15%">上传日期</th>
                            <th width="15%">MD5</th>
                            <th>下载地址</th>
                            <th width="5%">上传用户</th>
                            <th width="10%">操作</th>
                        </tr>
                        </thead>

                        <c:forEach items="${filetype}" var="ft">

                            <tbody>
                            <tr>
                                <td colspan="8" class="row_null"></td>
                            </tr>
                            <tr>
                                <td colspan="8" class="row_head retract" href="javascript:void(null)">
                                    资源名称：<strong>${ft.name}</strong> （<strong>${fn:length(ft.fileListSet)}</strong> 条记录）
                                </td>
                            </tr>
                            <tbody class="table_border">
                            <c:forEach items="${ft.fileListSet}" var="fl" varStatus="state">
                                <tr>
                                    <td><input type="checkbox" name="keys" value="${fl.id}"/></td>
                                    <td>${state.index+1}</td>
                                    <td>${fl.version}</td>
                                    <td>${fn:substring(fl.time, 0, 19)}</td>
                                    <td>${fl.md5}</td>
                                    <td class="http"><a href="${fl.url}" target="_blank">${fl.url}</a> </td>
                                    <td>${fl.user.nickname}</td>
                                    <td class="operate">
                                        <a href="javascript:void (0)" onclick="edit1(${fl.id});" class="edit" >编辑</a>
                                        <a href="javascript:void (0)" hrefs="/filetype/deleteappsoft?tid=${tid}&fid=${fid}&ids=${fl.id}" onclick="confirm1(this);" class="delete">删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            </tbody>

                        </c:forEach>

                        <tfoot>
                        </tfoot>
                    </table>
                    <div class="page_box">
                            <div id="pager" pagenumber="${pagenumber}" pagecount="${pagecount}" url="/filetype/list?tid=${tid}&fid=${fid}"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>



<!--新增弹出层开始-->
<div class="easyui-window" title="应用软件 - 资源管理" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false" id="addWindow">
    <div class="formbox">
        <form method="post" action="/filetype/addappsoft" id="add">
            <div class="r_form clearfix"><label class="labels_w">资源名称：</label>
                <input type="text" name="name" id="name" class="inputs easyui-textbox" data-options="required:true"  style="height: 28px;"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">上传文件：</label>
                <input type="text" name="url" id="url" class="inputs easyui-textbox"
                       data-options="buttonText:'浏览',required:true,onClickButton:function(){$('#uploadWindow').window('open')},editable:false"
                       style="height: 28px;"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">资源MD5：</label>
                <input type="text" name="md5" id="md5" class="inputs easyui-textbox" data-options="required:true"
                       style="height: 28px;"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">资源大小：</label>
                <input type="text" name="size" id="size" class="inputs easyui-numberbox" style="height: 28px;"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">上传版本：</label>
                <input type="text" name="version" id="version" class="inputs easyui-textbox"
                       data-options="required:true" style="height: 28px;"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">上传备注：</label>
                <input type="text" name="desc" id="desc" class="inputs easyui-textbox" data-options="multiline:true"
                       style="height: 40px;"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">附加信息：</label>
                <input type="text" name="plus" id="plus" class="inputs easyui-textbox" data-options="multiline:true"
                       style="height: 40px;"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">更新模式：</label><span>
                <input type="radio" name="model" value="0" checked="checked"/> 小于新版本号时更新</span><span>
                <input type="radio" name="model" value="1"/> 不等于新版本号时更新</span>
            </div>
            <div class="r_form clearfix"><label class="labels_w">MD5校验：</label><span>
                <input type="radio" name="check" value="1" checked="checked"/> 校验</span><span>
                <input type="radio" name="check" value="0"/> 不校验</span>
            </div>
            <div style="display: none;">
                <input type="text" value="${fid}" name="fid" class="inputs easyui-textbox"  data-options="required:true"/><%--指代当前上传的是跟新包还是资源包--%>
                <input type="text" value="${tid}" name="tid" class="inputs easyui-textbox"  data-options="required:true"/><%--指代当前上传的属于哪个栏目--%>
                <input type="text" name="id" id="id"/>
                <input type="text" name="fileTypeId" id="fileTypeId" class="inputs easyui-textbox"/>
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:void (0)" class="btn add_btn" onclick="dosubmit('add')">保存</a>
                <a href="javascript:void (0)" class="btn del_btn" onclick="$('#addWindow').window('close')">关闭</a>
            </div>
        </form>
    </div>
</div>
<div id="uploadWindow" title="资源上传" class="easyui-window" data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false">
    <div class="formbox_1">
        <form method="post" action="/upload" id="upload" enctype="multipart/form-data">
            <div class="r_form clearfix"><label class="labels_w">资源名称：</label>
                <input type="text" name="file" id="file" class="inputs easyui-filebox"
                       data-options="required:true,buttonText:'浏览'" style="height: 28px;width: 350px;"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">计算大小：</label><span>
                <input type="radio" name="isSize" value="1" checked="checked"/> 是</span><span>
                <input type="radio" name="isSize" value="0"/> 否</span>
            </div>
            <div class="r_form clearfix"><label class="labels_w">计算MD5：</label><span>
                <input type="radio" name="isMd" value="1" checked="checked"/> 是</span><span>
                <input type="radio" name="isMd" value="0"/> 否</span>
            </div>
            <div class="r_btn clearfix" style="margin-left: auto;">
                <a href="javascript:void (0)" class="btn add_btn" onclick="dosubmit('upload')">保存</a>
                <a href="javascript:void (0)" class="btn del_btn" onclick="$('#uploadWindow').window('close')">关闭</a>
            </div>
        </form>
    </div>
</div>
<%--刷新当前页面--%>
<div style="display: none;">
    <form id="rf" method="post" action="/filetype/deleteappsoft">
        <input type="hidden" value="${fid}" name="fid"/><%--指代当前上传的是跟新包还是资源包--%>
        <input type="hidden" value="${tid}" name="tid"/><%--指代当前上传的属于哪个栏目--%>
        <input type="hidden" name="ids" id="ids"/>
    </form>
</div>

</body>
</html>
