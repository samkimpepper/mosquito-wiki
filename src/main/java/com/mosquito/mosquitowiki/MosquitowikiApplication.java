package com.mosquito.mosquitowiki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class MosquitowikiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MosquitowikiApplication.class, args);
    }

}
