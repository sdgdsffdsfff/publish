function add() {
    var grid = $("#all");
    var rows = grid.datagrid('getSelections');
    if (rows.length > 0) {
        var selectGrid = $("#select");
        var items = selectGrid.datagrid('getRows');

        var add = [];
        for (var i = 0; i < rows.length; i++) {
            var o1 = rows[i];
            var has = false;
            for (var j = 0; j < items.length; j++) {
                var o2 = items[j];
                if (o1.id == o2.id) {
                    has = true;
                }
            }
            if (!has) {
                add.push(o1);
            }
        }

        items = items.concat(add);

        selectGrid.datagrid('loadData', items);
    }

}

function remove() {
    var grid = $("#select");
    var item = grid.datagrid('getSelections');
    for (var i = item.length - 1; i >= 0; i--) {
        var index = grid.datagrid('getRowIndex', item[i]);
        grid.datagrid('deleteRow', index);
    }
}

function doSearch(id) {
    var grid = $("#all");
    grid.datagrid('reload', {id: id});

}


function openWin() {
    var value = $("#rangeType").combobox("getValue");
//<option value="3">目标网吧</option>
//        <option value="4">代理商</option>
//        <option value="5">区域</option>

    if (value == 3) {
        $('#barSelectWin').window('open');
    } else if (value == 4) {
        $('#agentTreeWindow').window('open');
    } else if (value == 5) {
        $('#regionTreeWindow').window('open');
    }
}


function setRs() {
    var filelistids = "";
    var filelistnames = "";
    $(".fileselect").each(function () {

        var _select = $(this).get(0).checked;
        if (_select) {
            filelistids += $(this).val() + ",";
            filelistnames += $(this).attr("data") + ",";
        }
    });
    $("#filelistids").textbox("setValue", filelistids);
    $("#filelistnames").textbox("setValue", filelistnames);
    $('#addSoftWindow').window('close');
}


function setValue3() {
    var selectGrid = $("#select");
    var datas = selectGrid.datagrid("getData");
    if (datas) {
        var rangeValue = "", rangeValueView = "";
        var rows = datas.rows;
        for (var i = 0; i < rows.length; i++) {
            rangeValue += rows[i].id + ",";
            rangeValueView += rows[i].barname + ",";
        }
        if (rangeValue.indexOf(",") != -1) {
            rangeValue = rangeValue.substring(0, rangeValue.lastIndexOf(","));
        }
        if (rangeValueView.indexOf(",") != -1) {
            rangeValueView = rangeValueView.substring(0, rangeValueView.lastIndexOf(","));
        }
        $("#rangeValue").textbox("setValue", rangeValue);
        $("#rangeValueView").textbox("setValue", rangeValueView);
        $('#barSelectWin').window('close');
    }

}


function dosubmit(id) {
    $('#add').form('submit', {
        onSubmit: function () {
            return $(this).form('enableValidation').form('validate');
        }
    });

}

function confirm1(e) {
    $.messager.confirm('提示', '确认删除?', function (r) {
        if (r) {
            location.href = $(e).attr("hrefs");
        }
    });

}

function edit1(id) {
    $('#add').form("load", "/pub/get?id=" + id + "&t=" + new Date().getTime());
    $('#addWindow').window('open');

}

$(function () {

    $('#add').form({
        novalidate: true,
        success: function (data) {
            try {
                var jsondata = $.parseJSON(data);
                var success = jsondata.success;
                if (success == "t") {
                    $('#addWindow').window('close');
                    location.reload();
                } else {
                    $.messager.alert('提示', jsondata.msg, 'info');
                }

            } catch (e) {

            }

        }
    });


    //任务范围设置 根据任务范围的选择 给定不同的内容
    $("#rangeType").on("change", function () {
        var $rangeValue = $("#rangeValue");
        if ($(this).val() == 3) {
            $rangeValue.popModal({
                html: $('#agentcontent'),
                placement: 'bottomLeft'
            });
        } else if ($(this).val() == 4) {
            $rangeValue.popModal({
                html: $('#regioncontent'),
                placement: 'bottomLeft'
            });
        }
    })

});

/*
 *  树 选择值后确认
 * */
function rangeValueAdd(id) {
    var nodes = $("#" + id).tree('getChecked');
    var s = '';
    for (var i = 0; i < nodes.length; i++) {
        if (s != '') s += ',';
        if (nodes[i].value) {
            s += nodes[i].value;
        } else {
            s += nodes[i].text.substring(0, nodes[i].text.indexOf("["));
        }
    }
    $("#rangeValue").textbox("setValue", s);
    $("#rangeValueView").textbox("setValue", s);
    $("#" + id + "Window").window('close');
}

/*
 * 表单回显
 * ${pub.desc}-${pub.rangeType}-${pub.rangeValue}-${pub.filelistids}-${fn:substring(pub.startTime, 0, 19)}-${fn:substring(pub.endTime, 0, 19)}-${pub.delay}-${pub.limit}-${pub.state}-${pub.id}#${pub.number}#${pub.start}#${pub.filelistnames}
 * */

$.fn.echo = function ($this) {
    var value = $this.attr('data');
    if (value) {
        var val = value.split("#");
        var desc = val[0];
        var rangeType = val[1];
        var rangeValue = val[2];
        var filelistids = val[3];
        var startTime = val[4];
        var endTime = val[5];
        var delay = val[6];
        var limit = val[7];
        var state = val[8];
        var id = val[9];
        var number = val[10];
        var start = val[11];
        var filelistnames = val[12];
        $("#desc").val(desc);
        $("#rangeType").val(rangeType);
        $("#rangeValue").val(rangeValue);
        $("#filelistids").val(filelistids);
        $("#filelistnames").val(filelistnames);
        $("#startTimes").val(startTime);
        $("#endTimes").val(endTime);
        $("#delay").val(delay);
        $("#limit").val(limit);

        if (state.indexOf("0") != -1) {
            $("input[name='state'][value='0']").attr("checked", true);
        }
        if (state.indexOf("1") != -1) {
            $("input[name='state'][value='1']").attr("checked", true);
        }
        if (start.indexOf("0") != -1) {
            $("input[name='start'][value='0']").attr("checked", true);
        }
        if (start.indexOf("1") != -1) {
            $("input[name='start'][value='1']").attr("checked", true);
        }
        $("#id").val(id);
        $("#number").val(number);
    } else {
        $("#desc").val("");
        $("#rangeType").val("");
        $("#rangeValue").val("");
        $("#filelistids").val("");
        $("#filelistnames").val("");
        $("#startTimes").val("");
        $("#endTimes").val("");
        $("#delay").val(0);
        $("#limit").val(0);
        $("#number").val(0);
        $("#id").val("");
        $("input[name='state'][value='1']").attr("checked", true);
        $("input[name='start'][value='0']").attr("checked", true);
    }
};