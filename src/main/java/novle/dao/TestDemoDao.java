package novle.dao;

import novle.entity.TestDemo;

public interface TestDemoDao {
	
	public TestDemo getDetail(Integer id);
	
	public Integer save(TestDemo dto);
	
	public void update(TestDemo dto);
	
	public void del(Integer id);

}
