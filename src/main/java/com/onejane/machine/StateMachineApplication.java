package com.onejane.machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.onejane"})
public class StateMachineApplication {
     public static void main(String[] args) {
        SpringApplication.run(StateMachineApplication.class, args);
    }
}
