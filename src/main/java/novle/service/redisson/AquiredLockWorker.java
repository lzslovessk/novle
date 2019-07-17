package novle.service.redisson;

/**
 * 获取锁后需要处理的逻辑
 * @author 24521
 * @param <T>
 */
public interface AquiredLockWorker<T> {
	
	 T invokeAfterLockAquire() throws Exception;

}
