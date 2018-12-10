<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">

</script>
<div class="bjui-pageContent">
	<form action="testDemo/save.do" id="testdemo_form" data-toggle="validate" data-alertmsg="false">
		<input type="hidden" id="t_id" name="id" />
		<table class="table table-condensed table-hover" width="100%">
            <tbody>
            	<tr>
                    <td >  
                        <label for="t_demoName" class="control-label x120">名字：</label>
                    </td>
                    <td >
                        <input type="text" name="demoName" id="t_demoName" value="" data-rule="jsvalide;required;length[~20]" size="20">
                    </td>
                    <td >
                        <label for="t_demoCode" class="control-label x120">编码：</label>
                    </td>
                    <td >
                        <input type="text" name="demoCode" id="t_demoCode" value="" data-rule="jsvalide;required;length[~30]" size="20">
                    </td>
                </tr>  
            </tbody>
        </table> 
    </form>
</div>
<div class="bjui-pageFooter">
	<ul>
		<li><button type="button" class="btn btn-red cancel" data-icon="remove">取消</button></li>
		<li><button type="button" class="btn btn-default save" data-icon="save">保存</button></li>
	</ul>
</div>