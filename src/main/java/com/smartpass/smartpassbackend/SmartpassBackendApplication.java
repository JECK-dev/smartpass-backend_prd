package com.smartpass.smartpassbackend;

import com.smartpass.smartpassbackend.model.FacturaPdfConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@EnableConfigurationProperties
@SpringBootApplication
public class SmartpassBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartpassBackendApplication.class, args);
    }

}
