package jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisDemo1 {
	
	@Test
	public void demo() {
		// 1、设置IP地址和端口号
		Jedis jedis = new Jedis("192.168.64.223", 6379);
		// 2、保存数据
		jedis.set("name", "aaaa");
		// 3、获取数据
		String value = jedis.get("name");
		System.out.println(value);
		// 4、释放资源
		jedis.close();
	}
	
	/**
	 * 连接池方式连接
	 */
	@Test
	public void demo2() {
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxTotal(30);
		// 设置最大空闲连接数
		config.setMaxIdle(10);
		
		// 获得连接池
		JedisPool jedisPool = new JedisPool(config, "192.168.64.223", 6379);
		
		// 获得核心对象
		Jedis jedis = null;
		try {
			// 通过连接池获得连接
			jedis = jedisPool.getResource();
			// 设置数据
			jedis.set("name", "ssk1");
			// 获取数据
			String value = jedis.get("name");
			System.out.println(value);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			if (jedis != null) {
				jedis.close();
			}
			
		}
		
	}
	
	
	
	

}
