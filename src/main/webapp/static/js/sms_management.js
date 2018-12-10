/* 短信发送 dialog */
function add_sender_user(t){
    $(t).dialog({
        id:'sms_management',
        url:'sms_add_user.html',
        maxable: false,
        mask: true,
        width: 680,
        height: 600,
        onLoad:function($dialog){
            edit_template($('.select_template'));
        },
        title:'短信发送'
    });
}

/*删除单个发送人*/
function user_delete(t){
    $(t).parent('li').remove();
}
/*选择模板，并根据模板填充输入框数量*/
function edit_template(t){
    $(t).find('option').each(function(i,a){
        if($(t).val()==$(t).find('option').eq(i).val()){
            $('.template_content0').eq(i).addClass('select').siblings('').removeClass('select');
            var num = $('.template_content0').eq(i).html().split("{").length-1;
            var txt='';
            for(var i=1;i<=num;i++){
                if(i==1){
                    txt+='<li><span>{'+i+'}:</span><input name="txt'+i+'" type="text" class="form-control" value="{name}"></li>';
                }
                else{
                    txt+='<li><span>{'+i+'}:</span><input name="txt'+i+'" type="text" class="form-control"></li>';
                }
            }
            $('.template_txt ul').html('').append(txt);
        }
    });
}

function edit_template2(t,result){
    $(t).find('option').each(function(i,a){
        if($(t).val()==$(t).find('option').eq(i).val()){
            $('.template_content0').eq(i).addClass('select').siblings('').removeClass('select');
            var num = $('.template_content0').eq(i).html().split("{").length-1;
            var txt='';
            for(var i=1;i<=num;i++){
                if(i==1){
                    txt+='<li><span>{'+i+'}:</span><input name="txt'+i+'" type="text" class="form-control" value="{name}"></li>';
                }
                else{
                    txt+='<li><span>{'+i+'}:</span><input name="txt'+i+'" type="text" class="form-control" value="'+result[i-2]+'"></li>';
                }
            }
            $('.template_txt ul').html('').append(txt);
        }
    });
}
/*添加客户的弹窗*/
function add_users(t){
    /*指定客户*/
        $(t).dialog({
            id:'specify_user',
            url:'webMsg/smsUser.do',
            maxable: false,
            resizable:false,
            mask: true,
            width: 650,
            height: 480,
            onLoad:function($dialog){
            	 $('#specify_user_table').datagrid({
                    gridTitle : '客户信息',
                    showToolbar: true,
//                    toolbarCustom: '<a href="javascript:void(0);" class="btn btn-green add_user" style="margin-left: 5px;" onclick="add_specify_user()"><i class="fa fa-plus"></i> 添加</a>',
                    dataUrl: 'webMsg/userList.do',
                    dataType: 'json',
                    sortAll: true,
                    filterAll: true,
                    height: 400,
                    filterThead: false,
                    showCheckboxcol:true,
                    columns: [
                      {
                          name: 'loginName',
                          label: '登录名',
                          align: 'center',
                          width: 220
                      },
                        {
                            name: 'custSurname',
                            label: '用户姓名',
                            align: 'center',
                            width: 220
                        },
                       
                        {
                            name: 'userSn',
                            hide: true,
                            align: 'center',
                            width: 80
                        }
                    ],
                    editUrl: 'ajaxDone1.html',
                    paging: {pageSize:100, pageCurrent:1},
                    showCheckboxCol: true,
                    showEditBtnsCol: true,
                    linenumberAll: true,
                    fullGrid:false
                })
            },
            title:'添加指定用户'
        });
    
  
}
/*选中指定客户添加到短信发送弹窗中*/
var data;

function add_specify_user(_this){

    $('#specify_user_table tr').each(function(i,a){
    	
        if($(a).find('.datagrid-checkbox-td input').attr('checked')){
        	var sendDataGrid = $('#specify_user_table').datagrid();
        	sendIndex = i;
        	sendDataGrid.datagrid('getRowData', sendIndex, function(rowData){
        		data = rowData;
        	});
        	
            var content_html = '<li>'+
                '<span class="user_name">'+$(a).find('td').eq(3).find('div').html()+'</span>'+
                '<span class="user_sex">'+$(a).find('td').eq(2).find('div').html()+'</span>'+
                '<input type="hidden" name="msgReceiver" value="'+data.userSn+'"></input>'+
            '<a href="javascript:void(0);" onclick="user_delete(this)" class="user_delete">删除</a>'+
            '</li>';
            $('.send_users .text').prepend(content_html);
   
        }
        if(i==$('#specify_user_table tr').length-1){
//            alert('添加完成');
        }
    });
    sms_save(1);
}

function cancel(a){
	$('#specify_user_table').dialog('closeCurrent');
}

/*保存/保存并发送*/
function sms_save(n){
	var tId = $('.select_template').val();
	var smsSn = $("#smsSn").val();
    var template = $('.template_content0.select');

    var sms = '';
  
    if($('.send_users ul').html()!=''){
     
    }else{
    	alert("请选择发送客户！");
    	return;
    }

    if(n==1){
    	$('#specify_user_table').dialog('closeCurrent');
//    	$.ajax({
//			url: "sms/saveSms.do",
//			type: "post",
//			data:{"param":sms_arr,"title":title,"smsSn":smsSn},
//			dataType: "json",
//			success: function(res){
//				if("success"==res.resultType){
//
//				}else{
//				}
//			},
//			error: function(){
//
//			}
//		});
    }
    else if(n==2){
    	$.ajax({
			url: "sms/sendAndSaveSms.do",
			type: "post",
			data:{"param":sms_arr,"title":title,"smsSn":smsSn},
			dataType: "json",
			success: function(res){
				if("success"==res.resultType){

				}else{
				}
			},
			error: function(){

			}
		});
    	
    }
}