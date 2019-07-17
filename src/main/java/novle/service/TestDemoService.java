package novle.service;

import novle.entity.TestDemo;
import novle.model.PageResultDto;
import novle.model.ResultDto;
import novle.model.TestDemoDto;
import novle.model.TestDemoFilterDto;

public interface TestDemoService {
	
	public TestDemo getDetail(Integer id);
	
	public PageResultDto getTestDemoFilter(TestDemoFilterDto dto);
	
	public ResultDto save(TestDemoDto dto);
	
	public void del(Integer id);
	
	public void doTask();
	
}
