package com.example.gsrestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/*
 * @SpringBootApplication有什么用？它包括以下三个组分
 * 1. @Configuration： 是一个配置类。
 * 2. @EnableAutoConfiguration，激活行为..
 * 3. @ComponentScan 扫描其他组件
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.example.restservice")
public class GsRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GsRestServiceApplication.class, args);
		//让这个应用执行
	}

}
