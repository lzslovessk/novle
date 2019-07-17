package novle.service.impl;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import novle.redisson.RedissonConnector;
import novle.service.redisson.AquiredLockWorker;
import novle.service.redisson.DistributedLocker;
import novle.service.redisson.UnableToAquireLockException;

@Service
public class RedisLocker implements DistributedLocker{
	
	private final static String LOCKER_PREFIX = "lock:";

    @Autowired
    RedissonConnector redissonConnector;
    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws InterruptedException, UnableToAquireLockException, Exception {

        return lock(resourceName, worker, 100);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception {
        RedissonClient redisson = redissonConnector.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName); // 设置锁对象
        // Wait for 100 seconds seconds and automatically unlock it after lockTime seconds(等待100秒，在锁定时间秒后自动解锁)
        boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
        if (success) {// 获取锁成功
            try {
            	// 业务操作
                return worker.invokeAfterLockAquire();
            } finally {
                lock.unlock();
            }
        }
        throw new UnableToAquireLockException();
    }

}
