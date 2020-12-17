package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@ComponentScan(basePackages = {"com.sat"})
@EntityScan(basePackages = "com.sat")
@SpringBootApplication
//添加注释启动断路器，也可以使用@EnableCircuitBreaker注解，它会根据断路器的实现来自动配置
//直接使用@EnableHystrix需要Hystrix在类路径下
@EnableHystrix
//添加注释启动断路器监控界面
@EnableHystrixDashboard
//上面的注解可以在本项目测试，不需要其他项目的支持
//------------------------------------------------------------------
//添加注释启动turbine，将多个hystrix流合并到一个流中
//此时需要用到spring5-netflix-eureka-client-common-service-c15和spring5-netflix-eureka-client-common-service2-c15以及spring5-netflix-eureka-registerServer-c15结合使用
@EnableTurbine
public class Spring5NetflixHystrixC15Application {

	public static void main(String[] args) {
		SpringApplication.run(Spring5NetflixHystrixC15Application.class, args);
	}


}
