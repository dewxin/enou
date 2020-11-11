package org.fun.enou.server.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EnouServerCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnouServerCenterApplication.class, args);
    }
}