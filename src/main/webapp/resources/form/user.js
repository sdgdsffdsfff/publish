/**
 * Created by lijie on 2015-06-29.
 */


/*
 * 表单回显
 * ${usr.nickname}#${usr.username}#${usr.id}
 * */

$.fn.echo = function ($this) {
    var value = $this.attr('data');
    if (value) {
        var val = value.split("#");
        var nickname = val[0];
        var username = val[1];
        var id = val[2];

        $("#nickname").val(nickname);
        $("#username").val(username);
        $("#id").val(id);

    } else {
        $("#nickname").val("");
        $("#username").val("");
        $("#id").val("");
    }

    /*
    * 设置权限
    * */
    var authority = $this.attr("authority");
    if(authority) {
        $("input[type='checkbox'][name='operete']").each(function () {
                if(authority.indexOf($(this).val()+",")!=-1){
                    $(this).attr("checked",true);
                }else{
                    $(this).attr("checked",false);
                }
        });
    }else{
        $("input[type='checkbox'][name='operete']").attr("checked",false);
    }
    var keyId = $this.attr("key-id");
    if (keyId) {
        $("#key-id").val(keyId);
    }
};