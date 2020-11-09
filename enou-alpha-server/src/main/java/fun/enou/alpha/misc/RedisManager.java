package fun.enou.alpha.misc;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import fun.enou.alpha.config.property.RedisProperty;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@DependsOn("redisProperty")
public class RedisManager {
	
	JedisPool jedisPool;
	
	@Autowired
	public RedisManager(RedisProperty redisProperty) {
	    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	    jedisPoolConfig.setMaxTotal(200);
	    jedisPool = new JedisPool(jedisPoolConfig, redisProperty.getHost(), 6379, 1000, redisProperty.getAuthPass());
	}
	
	public Jedis getJedis() {
		Jedis jedis = jedisPool.getResource();
		return jedis;
	}
	
	public String getUserTokenKey(long userId) {
		return "token:uid:"+userId;
	}
	
	public String getToken(long userId) {
		
		try(Jedis jedis = getJedis()) {
			String userTokenKey = getUserTokenKey(userId);
			return jedis.get(userTokenKey);
		}
	}

}
