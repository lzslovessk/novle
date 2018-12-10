var num=0;
function initialize_dialog(t){
	var realPath = $(t).find('input').val();
	//获取后缀 如果是文件 在新页面中打开文件
	if(realPath){
		var _tmp = realPath.substring(realPath.lastIndexOf(".")+1);
		if(_tmp && "docx,doc,xls,xlsx,pptx,ppt,pdf,txt,rar,zip".indexOf(_tmp.toLowerCase()) >= 0){
			window.open(realPath, "_blank");
			return;
		}
	}
	
    num=1;
    $(t).dialog({
        id:'file-dialog',
        url:getJieguolaBackendUrl()+'attachment_img.html',
        maxable: false,
        mask: true,
        resizable:false,
        drawable:false,
        width: 970,
        height: 600,
        onLoad:function($dialog){
            $('#attachment_img_iframe').load(function() {
                if(num==1){
                    $(this).contents().find('.cropper').attr({
                        'c':$(t).find('img').attr('src')
                    });
                    num = 0;
                }
                $(this).contents().find('.eg-button').attr({
                    'img_index':$(t).parent().index(),
                    'img_num':$('.attachment_box').length
                });
                if($(t).parent().index() <= 0){
                    $(this).contents().find('.eg-button #replace_prev').attr('disabled','disabled');
                }
                else{
                    $(this).contents().find('.eg-button #replace_prev').removeAttr('disabled');
                }
                if($(t).parent().index() >= $('.attachment_box').length){
                    $(this).contents().find('.eg-button #replace_next').attr('disabled','disabled');
                }
                else{
                    $(this).contents().find('.eg-button #replace_next').removeAttr('disabled');
                }
                myFrame.window.img_refresh();
            });
        },
        title:'查看图片'
    });
}
function get_img(img_index){
    return $('.attachment_box').eq(img_index).find('img').attr('src');
}
function check_all(i){
    if(i==1){
        $('.attachment_box').find('input[type="checkbox"]').attr('checked','checked');
    }
    else{
        $('.attachment_box').find('input[type="checkbox"]').removeAttr('checked');
    }
}