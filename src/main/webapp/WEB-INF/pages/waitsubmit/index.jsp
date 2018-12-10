<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
	var testDemoDataGrid = $('#testDemo-datagrid').datagrid({
	    gridTitle : '用户查看',
	    showToolbar: true,
	    toolbarItem: '',
	    addLocation: 'first',
	    filterThead: false,
	    filterAll: true,
	    sortAll : true,
	    dataUrl: 'testDemo/list.do',
	    dataType: 'json',
	    columns: [
			{
			    name: 'id',
			    label: 'id(自动生成)',
			    align: 'center',
			    attrs: {readonly:'readonly'},
			    width: 80,
			    hide: true
			},
			{
			    name: 'demoName',
			    label: '名字',
			    align: 'center',
			    width: 150,
			},
			{
			    name: 'demoCode',
			    label: '编码',
			    align: 'center',
			    width: 150,
			},
			{
				name: '',
				label: '操作',
				align: 'center',
				width: 300,
				quicksort:false,
				menu: false,
				edit: false,
				render: function(value){
			        var html = '';
		    			html += '<div class="btn-group" role="group"><button type="button" class="btn btn-green" onclick="lookTestDemo(this)"><i class="fa fa-edit"></i>查看</button></div>&nbsp;';
	    				html += '<div class="btn-group" role="group"><button type="button" class="btn btn-green" onclick="editTestDemo(this)"><i class="fa fa-edit"></i>编辑</button></div>&nbsp;';
		    			html += '<div class="btn-group" role="group"><button type="button" class="btn btn-orange" onclick="deleteTestDemo(this)"><i class="fa fa-remove"></i> 删除</button></div>&nbsp;';
					return html;
				}
	        } 
	    ],
	    afterSave: function($trs, datas) {
	        	this.refresh();
	        },
	    paging: {pageSize:20, selectPageSize:'20,30,40'},
	    showTfoot: true,
	    fullGrid: false,
	    toolbarCustom: function(){
	    	var html = '';
    		html += '<div class="btn-group" role="group"><button type="button" class="btn btn-blue" onclick="addTestDemo()"><i class="fa fa-plus"></i> 添加</button></div>&nbsp;';
	    	html += '<div class="btn-group" role="group"><button type="button" class="btn btn-green" onclick="refreshTestDemo()"><i class="fa fa-refresh"></i> 刷新</button></div>';
		    return html;
	    }
	});

	
	function refreshTestDemo() {
		testDemoDataGrid.datagrid("refresh");
	}

	var testDemoIndex=-1;
	function addTestDemo() {
		testDemoIndex = -1;
		$('#edit-testDemo-dialog').dialog({
			id:'edit-testDemo-dialog', 
			//url:'testDemo/edit.do?isadd='+1, 
			url:'testDemo/edit.do',
			maxable: false,
			mask: true,
			width: 800,
			height: 400,
			onLoad: function($dialog){
				setEditDemoBtnEvent($dialog, true);
			},
			title:'添加'});
	}


	//查看
	function lookTestDemo(_this){
		testDemoIndex = $(_this).closest("tr").index();
		testDemoDataGrid.datagrid('getRowData', testDemoIndex, function(rowData){
			data = rowData;
		});
		$('#edit-testDemo-dialog').dialog({
			id:'edit-testDemo-dialog',
			url:'testDemo/query.do',
			maxable: false,
			mask: true,
			width: 600,
			height: 400,
			onLoad: function($dialog){
				
				// 取得数据并加载到form表单中
				var isSuccess = getJsonData2Form('testDemo/getDetail.do', 'id='+data.id, $('#look_testdemo'), testDemoCallback);
				
				if(!isSuccess){
					$dialog.dialog('closeCurrent');
				}
			},
			
			title:'查看'});
		
	}

	function editTestDemo(_this){
		testDemoIndex = $(_this).closest("tr").index();
		testDemoDataGrid.datagrid('getRowData', testDemoIndex, function(rowData){
			data = rowData;
		});

		$('#edit-testDemo-dialog').dialog({
			id:'edit-testDemo-dialog',
			url:'testDemo/edit.do', 
			maxable: false,
			mask: true,
			width: 800,
			height: 400,
			onLoad: function($dialog){
				
				// 取得数据并加载到form表单中
				setEditDemoBtnEvent($dialog, false);
				var isSuccess = getJsonData2Form('testDemo/getDetail.do', 'id='+data.id, $('#testdemo_form'), editTestDemoCallback);
				if(!isSuccess){
					$dialog.dialog('closeCurrent');
				}
			},
			
			title:'编辑'});
		
	}
	
	function deleteTestDemo(_this){
		testDemoIndex = $(_this).closest("tr").index();
		testDemoDataGrid.datagrid('getRowData', testDemoIndex, function(rowData){
			data = rowData;
		});
		testDemoDataGrid.alertmsg('confirm', '确定删除当前数据？', 
			{
			    displayMode:'slide', 
	            okName:'确定', 
	            cancelName:'取消', 
	            title:'提示信息',
	            okCall : function(){
	            	$.ajax({
	        			url: "testDemo/del.do?id="+data.id,
	        			type: "post",
	        			dataType: "json",
	        			success: function(res){
	        				if("success"==res.resultType){
	        					refreshTestDemo();
	        				}else{
	        					testDemoDataGrid.alertmsg('error', res.message);
	        				}
	        			},
	        			error: function(){
	        				refreshTestDemo();
	        			}
	        		});
	            }
	    });

	}
	
	function setEditDemoBtnEvent($dialog, isAdd) {
		var $save, $cancel;
		$save = $dialog.find('.save');
		$cancel = $dialog.find('.cancel');
		$save.click(function(){
			/* var isadd = 0;
			if(isAdd==true){
				isadd = 1;
		    } */
			$('#testdemo_form').isValid(function(v) {
				if(v){
					$.ajax({
						type     : 'POST',
						async    : false,
						dataType : 'json',
						//url      : 'testDemo/save.do?isadd='+isadd,
						url      : 'testDemo/save.do',
						data     : $('#testdemo_form').serialize(),
						cache    : false,
						success  : function(json) {
							if(json.resultType == 'success'){
								$dialog.dialog('closeCurrent');
								testDemoDataGrid.datagrid('refresh');
							} else {
								$('#testdemo_form').alertmsg('error', json.message);
							}
						},
						error   : BJUI.ajaxError
					});
				}
			});
		});
	
		$cancel.click(function(){
			$dialog.dialog('closeCurrent');
		}); 
		
	}
	
	function editTestDemoCallback($inputObj, _name, _val){
		
	}
	
	function testDemoCallback($inputObj, _name, _val){
		
	}
	
	function queryclose(){
		$('#look_testdemo').dialog('closeCurrent');
	}
</script>
<div class="bjui-pageContent">
    <div style="padding:0px; height:100%; width:100%;">
        <table id="testDemo-datagrid" data-width="100%" data-height="100%" class="table table-bordered">
        </table>
    </div>
</div>
<div id="edit-testDemo-dialog" style="display: none;">
</div>
