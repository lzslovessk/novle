package demo;

public class TestDemo2 {
	
	public static void main(String[] args) {
		//TestDemo3 three = new TestDemo3();  //error 抽象类不能被new
		TestDemo4 four = new TestDemo4();
		four.aaa(); // 抽象类中的方法要被使用，必须通过子类重写基类的方法，通过创建子类对象访问
		TestDemo3 three = new TestDemo4();  //对象向上转型
		three.run(); // 要访问基类中的方法 可以通过多态原理实现，返回结果：子类重写跑步方法
		TestDemo4 four2 = (TestDemo4)three; // 向下转型
		four2.run(); // 子类重写跑步方法
		
	
	}

}
