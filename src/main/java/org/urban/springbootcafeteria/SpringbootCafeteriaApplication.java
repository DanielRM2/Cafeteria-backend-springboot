package org.urban.springbootcafeteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.urban.springbootcafeteria"})
public class SpringbootCafeteriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCafeteriaApplication.class, args);
    }

}
