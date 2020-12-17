package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
//c13 增加该注解以启动Eureka注册中心，启动后localhost:8080可以访问，配置文件中eureka.instance.hostname和server.port可以改变
@EnableEurekaServer
public class ServiceRegisterNetflixEurekaC13Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegisterNetflixEurekaC13Application.class, args);
	}

}
