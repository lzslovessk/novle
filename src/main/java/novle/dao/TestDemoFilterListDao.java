package novle.dao;

import org.springframework.stereotype.Repository;

import novle.entity.TestDemo;
import novle.model.PageResultDto;
import novle.model.TestDemoFilterDto;

@SuppressWarnings("rawtypes")
@Repository
public class TestDemoFilterListDao extends BaseDataGridDao{

	public PageResultDto getTestDemoFilter(TestDemoFilterDto dto) { 
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select  ID as id,DEMO_TIME as demoTime, ");
		sqlBuffer.append("DEMO_NAME as demoName,DEMO_CODE as demoCode ");
		sqlBuffer.append("from test_demo ");
		
		//增加默认排序
		/*if(StringUtil.isNull(dto.getOrderField())){ 
			dto.setOrderField("startTime");
			dto.setOrderDirection("asc");
		}*/
		
		@SuppressWarnings("unchecked")
		PageResultDto result = super.getPageData(dto, sqlBuffer.toString(), TestDemo.class);
		return result;
	}

}
