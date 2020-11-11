package fun.enou.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisManager {
	
	JedisPool jedisPool;
	
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
