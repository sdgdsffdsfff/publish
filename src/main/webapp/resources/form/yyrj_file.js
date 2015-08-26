function dosubmit(id) {
    var $e=$('#' + id);
    $e.form('submit', {
        onSubmit: function () {
            $('<div class="datagrid-mask-msg" style="display:block;left:40%">表单提交中,请稍后......</div>').appendTo($e);
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
    $('#add').form("load", "/filelist/get?id=" + id+"&t="+new Date().getTime());
    $('#addWindow').window('open');
}

function doSearch(){
    $("#search").submit();
}


$(function () {

    $('#add').form({
        novalidate: true,
        success: function (data) {
          $("div[class='datagrid-mask-msg']").remove();
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


    $('#upload').form({
        novalidate: true,
        success: function (data) {
            $("div[class='datagrid-mask-msg']").remove();
            try {
                var jsondata = $.parseJSON(data);
                $("#url").textbox("setValue", jsondata.url);
                $("#md5").textbox("setValue", jsondata.md5);
                $("#size").numberbox("setValue", jsondata.size);
            } catch (e) {

            }

            //$.messager.alert('提示', '上传成功', 'info');
            $('#uploadWindow').window('close');
        }
    });


    //1、新增资源包（插件包、更新包、广告包……）时，界面上初始化时，附件信息初始化为最后一次同名资源包的附加信息
    $("input[name='name']").change(function () {
        var name = $(this).val();
        if (!$("input[name='fileType.id']").val()) {

            $.ajax({
                type: 'post',
                url: "/filetype/ajax",
                data: {name: name},
                success: function (data) {
                    if (data) {
                        $("#plus").val(data);
                    }
                }
            })

        }

    });

});