package com.mrwan.pigcount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@ServletComponentScan
public class PigcountApplication {

    public static void main(String[] args) {
        SpringApplication.run(PigcountApplication.class, args);
    }

}
