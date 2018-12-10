package novle.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.sql.SqlMapper;

import novle.model.PageResultDto;
import novle.model.SearchCommonDto;
import util.DataGridFilter4MybatisUtil;

public class BaseDataGridDao<T extends SearchCommonDto> {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	private SqlSession Session = null;
	
	private SqlMapper getSqlMapper() {
		Session = sqlSessionFactory.openSession();
		return new SqlMapper(Session);
	}
	
	/**
	 * DataGrid 数据查询
	 * @param t filterDto
	 * @param countSql  查询总条数的sql语句 结果需起别名为total
	 * @param querySql  查询内容的sql语句
	 * @param tableName   表的别名
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected PageResultDto getPageData(T t, String sql, Class clazz) {
		PageResultDto resultDto = new PageResultDto();
		
		try{
			StringBuffer querySql = new StringBuffer();
			querySql.append(" SELECT * FROM (" + sql + ") t WHERE 1=1 ");
			
			StringBuffer countSql = new StringBuffer();
			countSql.append(" SELECT COUNT(*) AS total FROM (" + sql + ") t WHERE 1=1 ");
			
			SqlMapper sqlm = getSqlMapper();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			DataGridFilter4MybatisUtil.handleListSql(t, "t", countSql, querySql, paramMap);
			PageResultDto countResultDto = sqlm.selectOne(countSql.toString(), paramMap, PageResultDto.class);
			
			resultDto.setTotal(countResultDto.getTotal());
			if (countResultDto.getTotal() > 0) {
				querySql.append(" limit ${currPage}, ${pageSize} ");
				paramMap.put("currPage", (t.getPageCurrent() - 1) * t.getPageSize());
				paramMap.put("pageSize", t.getPageSize());
				List list = sqlm.selectList(querySql.toString(), paramMap, clazz);
				resultDto.setPageCurrent(t.getPageCurrent());
				resultDto.setList(list);
			}
		} catch(Exception e){
			e.getMessage();
		} finally {
			this.Session.commit();
			this.Session.close();
		}
		
		return resultDto;
	}
	
}
