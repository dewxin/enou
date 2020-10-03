package samuragi.enou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

//todo support qq message
// https://github.com/skyrocketingHong/Bot.QQ/

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class EnouApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnouApplication.class, args);
    }

}
