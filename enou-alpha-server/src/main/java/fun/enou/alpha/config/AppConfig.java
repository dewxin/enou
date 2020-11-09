package fun.enou.alpha.config;

import fun.enou.alpha.config.property.CommonProperty;
import fun.enou.alpha.config.property.RedisProperty;
import fun.enou.core.encoder.EncodeUserPwdAspect;
import fun.enou.core.msg.AutoResponseMsgAspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class AppConfig {

    @Autowired
    RedisProperty redisProperty;
    
    @Autowired
    CommonProperty commonPeoperty;
    
    @Bean
    public EncodeUserPwdAspect passwordEncodeAspect() {
    	return new EncodeUserPwdAspect(commonPeoperty.getPwdSalt());
    }
    
    @Bean
    public AutoResponseMsgAspect AutoResponseMsgAspect() {
    	return new AutoResponseMsgAspect();
    }
    
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {

        // 注册CORS过滤器
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 是否支持安全证书
        config.addAllowedOrigin(commonPeoperty.getCrossOriginAllowed()); // 允许任何域名使用
        config.addAllowedHeader("*"); // 允许任何头
        config.addAllowedMethod("*"); // 允许任何方法（post、get等）
        // 预检请求的有效期，单位为秒。
        //        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
