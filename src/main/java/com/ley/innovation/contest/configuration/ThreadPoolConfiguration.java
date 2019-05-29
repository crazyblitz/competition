package com.ley.innovation.contest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfiguration {

    @Bean
    public ExecutorService imageThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        return executorService;
    }
}
