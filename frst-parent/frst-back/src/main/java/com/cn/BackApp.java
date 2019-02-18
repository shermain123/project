package com.cn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@MapperScan("com.cn.mapper")
@SpringBootApplication
@EnableEurekaClient
public class BackApp {

	public static void main(String[] args) {
		SpringApplication.run(BackApp.class, args);

	}

}
