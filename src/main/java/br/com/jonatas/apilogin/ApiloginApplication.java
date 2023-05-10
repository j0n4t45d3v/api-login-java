package br.com.jonatas.apilogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ApiloginApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiloginApplication.class, args);
    }

}
