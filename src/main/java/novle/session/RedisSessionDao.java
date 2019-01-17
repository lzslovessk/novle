package novle.session;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

@Component
public class RedisSessionDao extends AbstractSessionDAO{

	@Autowired
	private JedisUtil jedisUtil;
	
	private final String SHIRO_SESSION_PREFIX = "novel-session";
	
	private byte[] getKey(String key) {
		return (SHIRO_SESSION_PREFIX + key).getBytes();
	}
	
	private void saveSession(Session session) {
		if (session != null && session.getId() != null) {
			byte[] key = getKey(session.getId().toString());
			byte[] value = SerializationUtils.serialize(session);
			// 存储到缓存中
			jedisUtil.set(key, value);
			// 超时时间
			jedisUtil.expire(key, 600);
		}
	}
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		saveSession(session);
	}

	@Override
	public void delete(Session session) {
		if (session == null || session.getId() == null) {
			return ;
		}
		byte[] key = getKey(session.getId().toString());
		jedisUtil.del(key);
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<byte[]> keys = jedisUtil.keys(SHIRO_SESSION_PREFIX);
		Set<Session> sessions = new HashSet<>();
		if (CollectionUtils.isEmpty(keys)) {
			return sessions;
		}
		for (byte[] key : keys) {
			Session session = (Session)SerializationUtils.deserialize(key);
			sessions.add(session);
		}
		return sessions;
	}

	/**
	 * 创建session
	 */
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId); 
		saveSession(session);
		return sessionId;
	}

	/**
	 * 获得session
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		if (sessionId == null) {
			return null;
		}
		byte[] key = getKey(sessionId.toString());
		// 通过key获取value
		byte[] value = jedisUtil.get(key);
		// 反序列化session对象
		return (Session)SerializationUtils.deserialize(value);
	}

}
