/**
 * Created by lijie on 2015-06-29.
 */


/*
 * 表单回显
 * ${type.name}#${type.desc}#${type.orderNO}#${type.id}
 * */

$.fn.echo = function ($this) {
    var value = $this.attr('data');
    if (value) {
        var val = value.split("#");
        var name = val[0];
        var desc = val[1];
        var orderNO = val[2];
        var id = val[3];

        $("#name").val(name);
        $("#desc").val(desc);
        $("#orderNO").val(orderNO);
        $("#id").val(id);

    }else{
        $("#name").val("");
        $("#desc").val("");
        $("#orderNO").val("");
        $("#id").val("");
    }
};