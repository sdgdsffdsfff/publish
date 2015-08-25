/**
 * Created by lijie on 2015-06-29.
 */


/*
 * 表单回显
 * ${agent.text}#${agent.value}#${agent.orderNo}#${agent.id}
 * */

$.fn.echo = function ($this) {
    var values = $this.attr('data');
    if (values) {
        var val = values.split("#");
        var text = val[0];
        var value = val[1];
        var orderNo = val[2];
        var id = val[3];

        $("#text").val(text);
        $("#value").val(value);
        $("#orderNo").val(orderNo);
        $("#id").val(id);

    }else{
        $("#name").val("");
        $("#desc").val("");
        $("#orderNO").val("");
        $("#id").val("");
    }
};