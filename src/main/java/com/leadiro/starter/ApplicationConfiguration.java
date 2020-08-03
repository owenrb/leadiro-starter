package com.leadiro.starter;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages="com.leadiro", annotationClass=Mapper.class)
public class ApplicationConfiguration {
}
