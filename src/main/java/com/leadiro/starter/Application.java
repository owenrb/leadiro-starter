package com.leadiro.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.leadiro.starter.controller.auth.JwtBearerTokenAuthenticationFilter;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.leadiro"}, excludeFilters={
		  @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=JwtBearerTokenAuthenticationFilter.class)})
public class Application implements ApplicationRunner {
    private static final String APP_NAME = "starter";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("Starting {} service", APP_NAME);
    }
}
