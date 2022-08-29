package com.lastrix.scp.writeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lastrix.scp.writeservice")
public class ScpWriteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScpWriteServiceApplication.class, args);
    }

}
