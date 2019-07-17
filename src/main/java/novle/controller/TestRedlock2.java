package novle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import novle.service.TestDemoService;
import novle.service.impl.RedisLocker;
import novle.service.redisson.AquiredLockWorker;

@Controller
public class TestRedlock2 {
	
	@Autowired
    RedisLocker distributedLocker;
	
	@Autowired
	TestDemoService testDemoService;
	
	@RequestMapping(value = "/noredlock.do")
    public String testNoRedlock() throws Exception{
	  	System.out.println("3====================="); 
		 try { 
			 Thread.sleep(1000); 
			 testDemoService.doTask();
		  } 
		 catch (InterruptedException e) {
			 e.printStackTrace(); 
		 }
      	System.out.println("4=====================");
        return "nolock";
    }
	
	@RequestMapping(value = "/noredlock1.do")
    public String testNoRedlock1() throws Exception{
	  	System.out.println("5====================="); 
	  	testDemoService.doTask();
      	System.out.println("6=====================");
        return "nolock";
    }
	
    @RequestMapping(value = "/redlock.do")
    public String testRedlock() throws Exception{
        new Thread(new Worker()).start();
        System.out.println("All processors done. Shutdown connection");
        return "redlock";
    }

     class Worker implements Runnable {
        public void run() {
            try {
                distributedLocker.lock("test",new AquiredLockWorker<Object>() {
                    @Override
                    public Object invokeAfterLockAquire() {
                        doTask();
                        return null;
                    }
                });
            }catch (Exception e){
            	e.printStackTrace();
            }
        }
        
        void doTask() {
        	System.out.println("1====================="); 
        	testDemoService.doTask();
        	System.out.println("2=====================");
        }
        
    }

}
