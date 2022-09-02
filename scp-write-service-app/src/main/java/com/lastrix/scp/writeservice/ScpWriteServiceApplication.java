package com.lastrix.scp.writeservice;

import com.lastrix.scp.lib.db.SchemaInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.lastrix.scp.writeservice")
@Import(SchemaInit.class)
public class ScpWriteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScpWriteServiceApplication.class, args);
    }

}
