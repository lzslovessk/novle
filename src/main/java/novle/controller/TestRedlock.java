package novle.controller;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import novle.service.TestDemoService;
import novle.service.impl.RedisLocker;
import novle.service.redisson.AquiredLockWorker;

@Controller
public class TestRedlock {
	
	@Autowired
    RedisLocker distributedLocker;
	
	@Autowired
	TestDemoService testDemoService;
	
    @RequestMapping(value = "/redlock1.do")
    public String testRedlock() throws Exception{
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);
        for (int i = 0; i < 5; ++i) { // create and start threads
        	System.out.println(startSignal+":"+doneSignal);
            new Thread(new Worker(startSignal, doneSignal)).start();
        }
        startSignal.countDown(); // let all threads proceed(让所有线程继续)
        doneSignal.await();
        System.out.println("All processors done. Shutdown connection");
        return "redlock";
    }

     class Worker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        public void run() {
            try {
            	// 调用此方法会一直阻塞当前线程，直到计时器的值为0，除非线程被中断
                startSignal.await();
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
		  System.out.println(Thread.currentThread().getName() +" start"); 
		  Random random = new Random(); 
		  int _int = random.nextInt(200);
	      System.out.println(Thread.currentThread().getName() + " sleep " + _int + "millis"); 
		  try { 
			  Thread.sleep(_int); 
	      } catch (InterruptedException e) {
	    	  e.printStackTrace(); 
		  } 
		  System.out.println(Thread.currentThread().getName() +" end"); 
		  doneSignal.countDown(); // 计数减一 
	  }
		 
        
		
//	  void doTask() { 
//		  System.out.println("1=====================");
//	      testDemoService.doTask(); doneSignal.countDown(); // 计数减一
//	      System.out.println("2====================="); 
//	  }
		 
        
    }

}
