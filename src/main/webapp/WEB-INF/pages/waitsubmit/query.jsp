<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
	
	
</script>
<div class="bjui-pageContent">
	<form action="" id="look_testdemo" data-toggle="validate" data-alertmsg="false">
   	  <table class="table table-condensed table-hover" width="100%">
            <tbody>
                <tr>
                    <td>
                        <label for="t_demoName" class="control-label x150">名字：</label>
                        <span type="text" name="demoName" id="t_demoName"></span>
                    </td>
                    
                    <td>
                        <label for="t_demoCode" class="control-label x150">编码：</label>
                        <span type="text" name="demoCode" id="t_demoCode"></span>
                    </td>
                </tr>
            </tbody>
        </table> 
    </form>
  </div>  
<div class="bjui-pageFooter">
	<ul>
		<li><button type="button" class="btn btn-red cancel" data-icon="remove" onclick = "queryclose()">取消</button></li>
	</ul>
</div>