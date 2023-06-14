package com.testing.pack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class LearnUnitTestingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnUnitTestingApplication.class, args);
    }

}