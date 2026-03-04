package com.mosquito.mosquitowiki;

import org.springframework.boot.SpringApplication;

public class TestMosquitowikiApplication {

    public static void main(String[] args) {
        SpringApplication.from(MosquitowikiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
