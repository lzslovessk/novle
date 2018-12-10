package novle.quartz;

import org.springframework.stereotype.Component;


@Component
public class DemoQuartzJob {
	
//	WebApplicationContext wc = ContextLoader.getCurrentWebApplicationContext();
//	TestDemoService testDemoService = (TestDemoService)wc.getBean("testDemoService");

	public void runTask(){
//		TestDemo testDemo = testDemoService.getDetail(4);
//		System.out.println(testDemo.getDemoName()); 
		//testDemoService.del(2);
		System.out.println("我是quartz定时任务 ");
	}
}
