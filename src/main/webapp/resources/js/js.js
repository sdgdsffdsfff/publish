


$(function(){
	var i=0;
	$(".table_border").each(function(){
		if(i!=0){
		$(this).css("display","none");
		}
		i++;
		});
	
	$(".row_head").click(function(){
		$(".table_border").css("display","none");
		$(".row_head").removeClass("retract").addClass("spread");
		$(this).removeClass("spread").addClass("retract");
		$(this).parent().parent().next().css("display","")
		});
})