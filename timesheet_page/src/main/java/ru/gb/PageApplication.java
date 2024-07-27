package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PageApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PageApplication.class, args);
    }
}
