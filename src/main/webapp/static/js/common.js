$.ajaxSetup({  
    contentType:"application/x-www-form-urlencoded;charset=utf-8",  
    complete:function(XMLHttpRequest,textStatus){
          //通过XMLHttpRequest取得响应头，sessionstatus             
          var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus");
          var permissonStatus=XMLHttpRequest.getResponseHeader("permissonStatus");
          var contextPath = XMLHttpRequest.getResponseHeader("contextPath");
          if(sessionstatus=="timeout"){
        	  redirectLogin(contextPath);
          }  
          
          if(permissonStatus == "nopermission"){
        	  var i = 5;
        	  $(this).alertmsg("warn","无权访问该页面,"+i+"秒后跳转到我的主页!",{
        		  displayPosition : 'middlecenter',
        		  autoClose : true,
        		  alertTimeout : 4000
        	  });
        	  setTimeout(function(){redirectLogin(contextPath);},5000);
          }  
    },
    "error":myfunc
});
function getJieguolaBackendUrl(){
	return "http://backend.dev.xinhang.cc/zyueph_backend/";
}
function redirectLogin(contextPath){
	  //跳转的登录页面  
//	  if (window.top != window) {
//		  window.top.location.href= contextPath + "/login.html";
//	  } else {
//		  window.location.href= contextPath + "/login.html";
//	  }
	window.location.href= contextPath + "/login.html";
}

function myfunc(XMLHttpRequest, textStatus, errorThrown) {
	var contextPath = XMLHttpRequest.getResponseHeader("contextPath");
	if(XMLHttpRequest.status==403){
		//跳转的登录页面  
		if (window.top != window) {
			window.top.location.href= contextPath + "/login.html";
		} else {
			window.location.href= contextPath + "/login.html";
		}
	}else if(XMLHttpRequest.status==500){
		window.location.href = contextPath +  "/error500.jsp";
	}else if(XMLHttpRequest.status==408){
		window.location.href = contextPath +  "/error408.jsp";
	}else if(XMLHttpRequest.status==406){
		window.location.href = contextPath +  "/error406.jsp";
	}
}

function setForm(jsonData, $form, callback) {
	$.each(jsonData, function(_name, _val){
		$inputObj = $form.find("input:[name=" + _name + "]");
		$spanObj = $form.find("span:[name=" + _name + "]");
		$textareaObj = $form.find("textarea:[name=" + _name + "]");
		if($inputObj && $inputObj.length != 0){
			if($inputObj.attr('type')=='radio'){
				$form.find("input:[name=" + _name + "][value="+ _val +"]").iCheck('check');
			} else{
				$inputObj.val(_val);
			}
		} else if($spanObj && $spanObj.length != 0) {
			$spanObj.html(_val);
		} else if($textareaObj && $textareaObj.length != 0) {
			$textareaObj.val(_val);
		} else {
			$inputObj = $form.find("select:[name=" + _name + "]");
			if($inputObj && $inputObj.length != 0){
				$inputObj.selectpicker('val', ''+_val);
			}
		}
		
		if(callback){
			callback($inputObj, _name, _val);
		}
	});
}
function setLook(jsonData, $form, callback) {
	$.each(jsonData, function(_name, _val){
		$inputObj = $form.find("input:[name=" + _name + "]");
		$spanObj = $form.find("span:[name=" + _name + "]");
		$textareaObj = $form.find("textarea:[name=" + _name + "]");
		if($inputObj && $inputObj.length != 0){
			$inputObj.val(_val);
		} else if($spanObj && $spanObj.length != 0) {
			$spanObj.html(_val);
		} else if($textareaObj && $textareaObj.length != 0) {
			$textareaObj.val(_val);
		} else {
			$inputObj = $form.find("select:[name=" + _name + "]");
			if($inputObj && $inputObj.length != 0){
				$inputObj.selectpicker('val', ''+_val);
			}
		}
		
		if(callback){
			callback($inputObj, _name, _val);
		}
	});
}

function getJsonData2Form(url, param, $form, callback){
	var isSuccess = false;
	$.ajax({
		type     : 'POST',
		async    : false,
		dataType : 'json',
		url      : url,
		data     : param,
		cache    : false,
		success  : function(json) {
			//modify by zhouc 20160119
			if(json){
				if(json.resultType == 'success'){
					if(json.data){
						setForm(json.data[0], $form, callback);
					}
					isSuccess = true;
				} else {
					$form.alertmsg('info', json.message);
				}
			}
		},
		error   : BJUI.ajaxError
	});
	return isSuccess;
}

function getJsonData2Look(url, param, $form, callback){
	var isSuccess = false;
	$.ajax({
		type     : 'POST',
		async    : false,
		dataType : 'json',
		url      : url,
		data     : param,
		cache    : false,
		success  : function(json) {
			//modify by zhouc 20160119
			if(json){
				if(json.resultType == 'success'){
					if(json.data){
						setLook(json.data[0], $form, callback);
					}
					isSuccess = true;
				} else {
					$form.alertmsg('info', json.message);
				}
			}
		},
		error   : BJUI.ajaxError
	});
	return isSuccess;
}

function setPrevAndNextBtn($dataGrid, $dialog, index){
	var datalen;
	$dataGrid.datagrid('getRowData', index, function(rowData, len){
		datalen = len;
	});
	
	if(datalen == 1 || datalen < 0 || index < 0){
		// 只有一条数据，或者是添加，[上一条] [下一条]按钮都不可用
		$dialog.find('.prev').addClass('disabled');
		$dialog.find('.next').addClass('disabled');
	} else if(datalen == (index+1)) {
		// 编辑最后一条数据，[下一条]按钮不可用
		$dialog.find('.next').addClass('disabled');
	} else if(index == 0) {
		// 编辑第一条数据，[上一条]按钮不可用
		$dialog.find('.prev').addClass('disabled');
	}
}

function getFileSize(obj) {
    var size = 0;  
    if(navigator.userAgent.indexOf("MSIE")>0){    
        var fso = new ActiveXObject('Scripting.FileSystemObject');  
        var file = fso.GetFile(obj.value);  
        size = file.size;   
    }else {  
        size = obj.files[0].size;  
    }  
    return size;
}