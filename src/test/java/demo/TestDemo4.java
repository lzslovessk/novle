package demo;

public class TestDemo4 extends TestDemo3{

	@Override
	public void aaa() {
		System.out.println("aaa");
	}

	@Override
	void bbb() {
		System.out.println("bbb"); 
	}
	
	@Override
	void run() {
		System.out.println("子类重写跑步方法");
	}
 
}
