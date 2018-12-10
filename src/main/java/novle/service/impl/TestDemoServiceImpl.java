package novle.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import constants.ResultState;
import constants.ResultType;
import novle.dao.TestDemoDao;
import novle.dao.TestDemoFilterListDao;
import novle.entity.TestDemo;
import novle.model.PageResultDto;
import novle.model.ResultDto;
import novle.model.TestDemoDto;
import novle.model.TestDemoFilterDto;
import novle.service.TestDemoService;
import util.BeanUtil;

@Service(value="testDemoService")
@Transactional
public class TestDemoServiceImpl implements TestDemoService {
	
	@Autowired
	TestDemoDao testDemoDao;
	@Autowired
	TestDemoFilterListDao testDemoFilterListDao;

	@Override
	public TestDemo getDetail(Integer id) {
		return testDemoDao.getDetail(id); 
	}

	@Override
	public PageResultDto getTestDemoFilter(TestDemoFilterDto dto) {
		return testDemoFilterListDao.getTestDemoFilter(dto);
	}

	@Override
	public ResultDto save(TestDemoDto dto) {
		ResultDto resultDto = new ResultDto();
		Integer id = 0;
		if(dto.getId()!=null){
			id = dto.getId();
		}
		TestDemo testDemo = testDemoDao.getDetail(id);
		if(testDemo==null){
			testDemo = new TestDemo();
			BeanUtil.copyBeans(dto,testDemo); 
			testDemo.setDemoTime(new Date());
			testDemoDao.save(testDemo);
		}else{
			if(id.equals(testDemo.getId())){
				BeanUtil.copyBeans(dto,testDemo); 
				testDemoDao.update(testDemo);
			}
		}
		resultDto.setResultType(ResultType.SUCCESS);
		resultDto.setMessage("保存成功！");
		resultDto.setStatusCode(ResultState.SUCCESS);
		return resultDto;
	}

	@Override
	public void del(Integer id) {
		testDemoDao.del(id);
	}

}
