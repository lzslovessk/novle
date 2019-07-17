package novle.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedissonConnector {
	
	 @Autowired
	 private RedissonClient redissonClient;
	 
	 public void init() {
		 redissonClient = Redisson.create();
	 }

	 public RedissonClient getClient(){
        return redissonClient;
	 }

}
