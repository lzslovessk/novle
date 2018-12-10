package constants;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.StringUtil;


public class Constants {
	private final static Log logger = LogFactory.getLog(Constants.class);
	
	/**
	 * 开发模式
	 */
	public final static String RUN_MODE_DEV = "dev";

	/**
	 * 测试模式
	 */
	public final static String RUN_MODE_TEST = "test";
	
	/**
	 * 正式模式
	 */
	public final static String RUN_MODE_RELEASE = "release";
	
	private static Properties p = null;
	
	static {
		if (p == null) {
			try {
				p = new Properties();
				p.load(Constants.class.getClassLoader().getResourceAsStream("config.properties"));
			} catch (IOException e) {
				logger.error("读取config.properties文件失败！", e);
			}
		}
	}

	public static String getProperty(String key) {
		if (p == null) {
			return null;
		} else {
			String runmode = p.getProperty("run.mode");
			if (StringUtil.isNotNull(runmode)) {
				return p.getProperty(runmode + "." + key);
			}
			return p.getProperty(key);
		}
	}
	
	public static String getProperty(String key, boolean usedRunmode) {
		if (usedRunmode) {
			return getProperty(key);
		} else {
			if (p == null) {
				return null;
			} else {
				return p.getProperty(key);
			}
		}
	}
}
