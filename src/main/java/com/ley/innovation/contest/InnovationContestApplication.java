package com.ley.innovation.contest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.ley.innovation.contest.*.dao")
public class InnovationContestApplication {

    public static void main(String[] args) {
        SpringApplication.run(InnovationContestApplication.class, args);
    }
}
