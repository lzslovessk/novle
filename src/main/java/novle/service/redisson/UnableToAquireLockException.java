package novle.service.redisson;

/**
 * 异常类
 * @author 24521
 *
 */
@SuppressWarnings("serial")
public class UnableToAquireLockException extends RuntimeException{
	
	public UnableToAquireLockException() {
	}

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
