package fun.enou.bot.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

import fun.enou.core.msg.AutoWrapMsgAspect;
import fun.enou.core.redis.RedisManager;
import fun.enou.core.redis.RedisProperty;

@Configuration
public class AppConfig {
	
	@Bean
	public RedisProperty redisProperty() {
		return new RedisProperty();
	}
	
	@Bean
	@DependsOn("redisProperty")
	@Autowired
	public RedisManager redisManager(RedisProperty redisProperty) {
		return new RedisManager(redisProperty);
	}
	
    @Bean
    public AutoWrapMsgAspect autoWrapMsgAspect() {
    	return new AutoWrapMsgAspect();
    }
    
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
