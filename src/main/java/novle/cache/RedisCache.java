package novle.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import novle.session.JedisUtil;

@Component
public class RedisCache<K,V> implements Cache<K,V>{
	
	@Autowired
	private JedisUtil jedisUtil;
	
	private final String CACHE_PREFIX = "ssk-cache";
	
	private byte[] getKey(K k) {
		if (k instanceof String) {
			return (CACHE_PREFIX + k).getBytes();
		}
		return SerializationUtils.serialize(k); 
	}

	@Override
	public void clear() throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V get(K arg0) throws CacheException {
		// 实际项目可以做改变，此处读本地二级缓存，如果本地缓存读取不到的话，可以在从redis获取
		// 从redis获取后，可以在写到本地缓存中
		System.out.println("从redis获取权限数据");
		byte[] value = jedisUtil.get(getKey(arg0));
		if (value != null) {
			return (V) SerializationUtils.deserialize(value);
		}
		return null;
	}

	@Override
	public Set<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V put(K arg0, V arg1) throws CacheException {
		byte[] key = getKey(arg0);
		byte[] value = SerializationUtils.serialize(arg1);
		jedisUtil.set(key, value);
		jedisUtil.expire(key, 600); 
		return arg1;
	}

	@Override
	public V remove(K arg0) throws CacheException {
		byte[] key = getKey(arg0);
		byte[] value = jedisUtil.get(key);
		jedisUtil.del(key);
		if (value != null) {
			return (V) SerializationUtils.deserialize(value);
		}
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
