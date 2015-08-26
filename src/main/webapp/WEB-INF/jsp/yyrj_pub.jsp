<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="has" uri="http://hasOperate.com" %>
<%@ taglib prefix="fmtstat" uri="http://formatState.com" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <style type="text/css">
        .datagrid-btable tr {
            height: 31px;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>通用更新管理后台</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/ui/themes/gray/easyui.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/ui/themes/icon.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/ui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/ui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/page/jquery.pager.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/form/yyrj_pub.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/js.js"></script>
</head>

<body>

<div class="content">
    <div class="m20">
        <div class="nav">
            <has:operate> <a href="/filetype/list?tid=${tid}&fid=1">更新包管理</a></has:operate>
            <has:operate><a href="/filetype/list?tid=${tid}&fid=2">资源包管理</a></has:operate>
            <has:operate><a href="/pub/list?tid=${tid}&fid=1"  <c:if test="${fid==1}"> class="on" </c:if>>更新发布管理</a></has:operate>
            <has:operate> <a href="/pub/list?tid=${tid}&fid=2" <c:if
                    test="${fid==2}"> class="on" </c:if>>资源发布管理</a></has:operate>
        </div>
        <div class="con_box">
            <div class="p10">
                <div class="add_box clearfix">
                    <div class="add_div fl">
                        <a href="javascript:void (0)" onclick="$('#addWindow').window('open')" class="add_btn btn"> <em>+</em>
                            新增 </a>
                        <a href="javascript:void (0)" class="del_btn btn">批量删除</a>
                    </div>
                    <div class="search fr clearfix">
                        <form action="/pub/list?tid=${tid}&fid=${fid}" method="post" id="search">
                            <input class="easyui-searchbox" name="namefield"
                                   data-options="prompt:'输入资源名称',searcher:doSearch" style="width: 300px;height: 34px;"/>
                        </form>
                    </div>
                </div>
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list1 table">
                    <tr>
                        <th width="6%"><input type="checkbox" id="checkall"/> 全选</th>
                        <th width="4%">序号</th>
                        <th>任务说明</th>
                        <th width="6%">任务范围</th>
                        <th width="10%">范围设置</th>
                        <th width="13%">发布时间</th>
                        <th width="13%">有效期 -- 开始</th>
                        <th width="13%">有效期 -- 结束</th>
                        <th width="6%">发布状态</th>
                        <th width="6%">发布用户</th>
                        <th width="13%">操作</th>
                    </tr>
                    <c:forEach items="${publist}" var="pub" varStatus="state">
                        <c:if test="${state.index%2==0}"><tr class="tr1"></c:if>
                        <c:if test="${state.index%2==1}"><tr class="tr2"></c:if>
                        <td><input type="checkbox" name="keys" value="${pub.id}"/></td>
                        <td>${state.index+1}</td>
                        <td>${pub.desc}</td>
                        <td>
                            <c:choose>
                                <c:when test="${pub.rangeType==1}">目标机器</c:when>
                                <c:when test="${pub.rangeType==2}">产品版本</c:when>
                                <c:when test="${pub.rangeType==3}">目标网吧</c:when>
                                <c:when test="${pub.rangeType==4}">代理商</c:when>
                                <c:when test="${pub.rangeType==5}">区域</c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${fn:length(pub.rangeValue)>10}">${fn:substring(pub.rangeValue, 0,10 )}...</c:when>
                                <c:otherwise>${pub.rangeValue}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${fn:substring(pub.createTime, 0, 19)}</td>
                        <td>${fn:substring(pub.startTime, 0, 19)}</td>
                        <td>${fn:substring(pub.endTime, 0, 19)}</td>
                        <fmtstat:operate>${pub.state},${pub.startTime},${pub.endTime}</fmtstat:operate>
                        <td>${pub.user.nickname}</td>
                        <td class="operate">
                            <a href="javascript:void (0)" onclick="edit1(${pub.id});" class="edit">编辑</a>
                            <a href="javascript:void (0)" hrefs="/pub/deletepub?tid=${tid}&fid=${fid}&ids=${pub.id}" onclick="confirm1(this);" class="delete">删除</a>
                        </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="page_box">
                    <div id="pager" pagenumber="${pagenumber}" pagecount="${pagecount}"
                         url="/pub/list?tid=${tid}&fid=${fid}"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--新增弹出层开始-->
<div class="easyui-window" title="应用软件 - 发布管理"
     data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false" id="addWindow">
    <div class="formbox">
        <form method="post" id="add" action="/pub/addapppub">

            <div class="r_form clearfix"><label class="labels_w">任务说明：</label>
                <input type="text" name="desc" id="desc" class="inputs easyui-textbox" data-options="required:true"
                       style="height: 28px;"/>
            </div>

            <div class="r_form clearfix"><label class="labels_w">任务范围：</label>
                <select name="rangeType" id="rangeType" class="selects easyui-combobox"
                        data-options="required:true,editable:false" style="height: 28px;">
                    <%--<option value="1">目标机器</option>--%>
                    <option value="2">产品版本</option>
                    <option value="3">目标网吧</option>
                    <option value="4">代理商</option>
                    <option value="5">区域</option>
                </select>
            </div>

            <div class="r_form clearfix"><label class="labels_w">范围设置：</label>
                <input type="text" name="rangeValueView" id="rangeValueView" class="inputs easyui-textbox"
                       data-options="required:true,buttonText:'选择',onClickButton:openWin" style="height: 28px;"/>

            </div>

            <div class="r_form clearfix"><label class="labels_w">资源选择：</label>
                <input type="text" name="filelistnames" id="filelistnames" class="inputs easyui-textbox"
                       data-options="required:true,buttonText:'选择',onClickButton:function(){$('#addSoftWindow').window('open')},editable:false"
                       style="height: 28px;"/>
            </div>

            <div class="r_form clearfix"><label class="labels_w">有效期：</label>
                <input type="text" name="startTimes" id="startTimes" class="easyui-datetimebox"
                       style="height: 28px; width: 172px;" data-options="required:true,editable:false"/>&nbsp;-&nbsp;
                <input type="text" name="endTimes" id="endTimes" class="easyui-datetimebox"
                       style="height: 28px;width: 172px;" data-options="required:true,editable:false"/>
            </div>

            <div class="r_form clearfix"><label class="labels_w">发布延迟：</label>
                <input type="text" name="delay" id="delay" class="inputs easyui-numberbox"
                       data-options="required:true,prompt:'0无延迟'" style="height: 28px;">
            </div>
            <div class="r_form clearfix"><label class="labels_w">限制个数：</label>
                <input type="text" name="limit" id="limit" class="inputs easyui-numberbox"
                       data-options="required:true,prompt:'0无限制'" style="height: 28px;"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">获取次数：</label>
                <input type="text" name="number" id="number" class="inputs easyui-numberbox"
                       data-options="required:true,prompt:'0无限制'" style="height: 28px;"/>
            </div>
            <div class="r_form clearfix"><label class="labels_w">发布状态：</label><span>
                <input type="radio" name="state" value="1" checked="checked"/> 启用</span><span>
                <input type="radio" name="state" value="0"/> 停用</span>
            </div>
            <div style="display: none">
                <input type="text" name="rangeValue" id="rangeValue" class="easyui-textbox"  data-options="required:true" />
                <input type="text" name="filelistids" id="filelistids" class="easyui-textbox" data-options="required:true" >
                <input type="text" value="${fid}" name="fid" class="easyui-textbox" data-options="required:true"/>
                <input type="text" value="${tid}" name="tid" class="easyui-textbox" data-options="required:true"/>
                <input type="text" name="id" id="id"/>
            </div>
            <div class="r_btn clearfix">
                <a href="javascript:void (0)" onclick="dosubmit();" class="btn add_btn">保存</a>
                <a href="javascript:void (0)" class="btn del_btn" onclick="$('#addWindow').window('close')">关闭</a>
            </div>
        </form>
    </div>
</div>

<%--文件选择--%>
<div class="easyui-window" title="应用软件 - 资源选择"
     data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,footer:'#footer'"
     id="addSoftWindow"
     style="width: 400px;height: 300px;">
    <div id="content2" class="popcon">
        <div class="tree">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="list2">
                <c:forEach items="${fileTypeList}" var="filetype">
                    <tbody>
                    <tr>
                        <td colspan="5" class="row_null"></td>
                    </tr>
                    <tr>
                        <td colspan="5" class="row_head2">资源名称：<strong>${filetype.name}</strong>
                            (<strong>${fn:length(filetype.fileListSet)}</strong> 条记录)
                        </td>
                    </tr>
                    <tbody class="table_border2">
                    <c:forEach items="${filetype.fileListSet}" var="filelist" varStatus="state">
                        <tr>
                            <td width="20%">
                                <input type="radio" class="fileselect" name="${filetype.name}" value="${filelist.id}" data="${filetype.name}[${filelist.version}]" />
                            </td>
                            <td width="30%">${state.index+1}</td>
                            <td>${filelist.version}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    </tbody>
                </c:forEach>

                <tfoot>
                </tfoot>
            </table>
        </div>
    </div>
</div>
<div id="footer" style="padding:5px;">
    <div class="r_btn clearfix" style="margin-left: 70px;">
        <a href="javascript:void (0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="setRs();">保存</a>
        <a href="javascript:void (0)" onclick="$('#addSoftWindow').window('close');" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">关闭</a>
    </div>
</div>


<%--目标网吧--%>
<div id="barSelectWin" title="目标网吧" class="easyui-window"
     data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,footer:'#barfooter'"
     style="width: 800px;height: 480px;">
    <div class="easyui-layout" style="width:100%;height:100%;">
        <div region="west" split="false" style="width:480px;height: 100%;">
            <table id="all" class="easyui-datagrid" style="width:100%;height:100%;"
                   url="/config/getbar"
                   toolbar="#toolbar"
                   rownumbers="true"
                   fitColumns="true"
                   pagination="true"
                   border="false">
                <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th field="id" width="30">ID</th>
                    <th field="barname" width="40">网吧名称</th>
                    <th field="bararea" width="80">所在地区</th>
                    <th field="companyid" width="30">公司编号</th>
                    <th field="companyname" width="40">公司名称</th>
                </tr>
                </thead>
            </table>
        </div>
        <div id="content" region="center" title="操作">
            <a href="javascript:add()" class="easyui-linkbutton"
               style="width: 50px;height: 30px;background-image: url('/resources/images/right.gif');margin-top: 130px;margin-left: 2px;"></a>
            <a href="javascript:remove()" class="easyui-linkbutton"
               style="width: 50px;height: 30px;background-image: url('/resources/images/left.gif');margin-top: 30px;margin-left: 2px;"></a>
        </div>
        <div id="east" region="east" title="已选择" style="width:250px;">
            <table id="select" class="easyui-datagrid" style="width:100%;height:100%;"
                   rownumbers="true"
                   fitColumns="true"
                   singleSelect="false"
                   border="false"
                   idField="id">
                <thead>
                <tr>
                    <th field="ck" checkbox="true"></th>
                    <th field="id" width="30">ID</th>
                    <th field="barname" width="60">网吧名称</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <div id="toolbar" style="padding:3px">
        <input class="easyui-searchbox" data-options="prompt:'输入id',searcher:doSearch" style="width: 150px;height: 19px;"/>
    </div>
</div>

<div id="barfooter" style="padding:5px;">
    <div class="r_btn clearfix" style="padding-left: 300px;">
        <a href="javascript:void (0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="setValue3();">保存</a>
        <a href="javascript:void (0)" onclick="$('#barSelectWin').window('close');" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">关闭</a>
    </div>
</div>

<%--地区--%>
<div class="easyui-window" title="应用软件 - 代理选择"
     data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,footer:'#agentfooter'"
     id="agentTreeWindow" style="width: 300px;height: 200px;">
    <div id="agentcontent" class="popcon">
        <div class="r_form clearfix">
            <div id="agentTree" class="easyui-tree" data-options="url:'/agent/get',method:'get',checkbox:true"></div>
        </div>
    </div>
</div>

<div id="agentfooter" style="padding:5px;">
    <div class="r_btn clearfix" >
        <a href="javascript:void (0)" onclick="rangeValueAdd('agentTree');" class="easyui-linkbutton"  data-options="iconCls:'icon-add'">保存</a>
        <a href="javascript:void (0)" onclick="$('#agentTreeWindow').window('close');" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">关闭</a>
    </div>
</div>


<div class="easyui-window" title="应用软件 - 地区选择"
     data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false,footer:'#regionfooter'"
     id="regionTreeWindow" style="width: 300px;height: 350px;">
    <div id="regioncontent" class="popcon">
        <div class="r_form clearfix">
            <div id="regionTree" class="easyui-tree" data-options="url:'/region/get',method:'get',checkbox:true"></div>
        </div>
    </div>
</div>

<div id="regionfooter" style="padding:5px;">
    <div class="r_btn clearfix">
        <a href="javascript:void (0)" onclick="rangeValueAdd('regionTree');" class="easyui-linkbutton"  data-options="iconCls:'icon-add'">保存</a>
        <a href="javascript:void (0)" onclick="$('#regionTreeWindow').window('close');" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">关闭</a>
    </div>
</div>

<%--刷新当前页面--%>
<div style="display: none;">
    <form id="rf" method="post" action="/pub/deletepub">
        <input type="hidden" value="${fid}" name="fid"/><%--指代当前上传的是跟新包还是资源包--%>
        <input type="hidden" value="${tid}" name="tid"/><%--指代当前上传的属于哪个栏目--%>
        <input type="hidden" name="ids" id="ids"/>
    </form>
</div>
</body>
</html>
