package fun.enou.alpha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

//todo support qq message
// https://github.com/skyrocketingHong/Bot.QQ/

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
@EnableDiscoveryClient
public class EnouAlphaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnouAlphaApplication.class, args);
    }

}
