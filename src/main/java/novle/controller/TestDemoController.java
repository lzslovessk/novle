package novle.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import constants.ResultState;
import constants.ResultType;
import novle.entity.TestDemo;
import novle.model.PageResultDto;
import novle.model.ResultDto;
import novle.model.TestDemoDto;
import novle.model.TestDemoFilterDto;
import novle.service.TestDemoService;
import util.FilterUtil;

@Controller
@RequestMapping(value="/testDemo")
public class TestDemoController extends BaseController{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String INDEX = "waitsubmit/index";
	private static final String LOOK = "waitsubmit/query";
	private static final String EDIT = "waitsubmit/edit";
	
	@Autowired
	private TestDemoService testDemoService;
	
	@InitBinder
	protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("/index.do")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return INDEX;
	}
	
	@ResponseBody
	@RequestMapping("/list.do")
	public PageResultDto list(TestDemoFilterDto dto, HttpServletRequest request, HttpServletResponse response) {
		FilterUtil.seperateRequest(dto, request);
		PageResultDto result = testDemoService.getTestDemoFilter(dto); 
		this.setParameters(result, dto);
		return result;
	}
	
	@RequestMapping(value="/query.do")
	public String query(HttpServletRequest request, HttpServletResponse response){
		return LOOK;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping("/getDetail.do")
	public ResultDto getDetail(HttpServletRequest request) {
		ResultDto resultDto = new ResultDto();
		String id = request.getParameter("id");
		TestDemo testDemo = testDemoService.getDetail(Integer.parseInt(id)); 
		if(testDemo!=null){
			resultDto.setStatusCode(ResultState.SUCCESS);
			resultDto.setResultType(ResultType.SUCCESS);
			resultDto.setMessage("查询成功");
			List list = new ArrayList();
			list.add(testDemo);
			resultDto.setData(list);
		}else{
			resultDto.setStatusCode(ResultState.SUCCESS);
			resultDto.setResultType(ResultType.NOT_FOUND);
			resultDto.setMessage("数据未找到");
		}
		return resultDto;
	}
	
	@RequestMapping(value="/edit.do")
	public String edit(HttpServletRequest request, HttpServletResponse response){
		return EDIT;
	}
	
	@RequestMapping("/save.do")
	@ResponseBody
	public ResultDto save(TestDemoDto dto,HttpServletRequest request,HttpServletResponse response){
		ResultDto resultDto = new ResultDto();
		try{
			resultDto = testDemoService.save(dto);
		}catch(Exception e){
			logger.error(e.getMessage());
			resultDto.setResultType(ResultType.FAIL);
			resultDto.setStatusCode(ResultState.FAIL);
			resultDto.setMessage("添加失败");
		}
		return resultDto;
	}
	
	@RequestMapping("/del.do")
	@ResponseBody
	public ResultDto del(HttpServletRequest request){
		ResultDto resultDto = new ResultDto();
		String id = request.getParameter("id");
		testDemoService.del(Integer.parseInt(id));  
		resultDto.setStatusCode(ResultState.SUCCESS);
		resultDto.setResultType(ResultType.SUCCESS);
		resultDto.setMessage("删除成功"); 
		return resultDto;
	}
	
	

}
