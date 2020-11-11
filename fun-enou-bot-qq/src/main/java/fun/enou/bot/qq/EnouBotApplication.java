package fun.enou.bot.qq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EnouBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnouBotApplication.class, args);
    }
}
