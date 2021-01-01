package com.github.mszarlinski.stories;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO @EnableAsync
@SpringBootApplication
public class StoriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoriesApplication.class, args);
    }

}
