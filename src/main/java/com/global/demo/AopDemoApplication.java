package com.global.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.global.demo.dao.IUserDao;
import com.global.demo.dao.UserDao;

import lombok.extern.slf4j.Slf4j;

/*
 * AOP开发流程
 * 1.spring-boot -starter-aop,加入依赖，默认就开启了AOP的支持
 * 2.写一个Aspect，封装横切关注点（日志，监控等等），需要配置通知（前置通知，后置通知等等）和切入点（哪些包的哪些类的哪些方法等等）
 * 3.这个Aspect需要纳入到spring容器管理，并且需要加入@Aspect
 * 
 * spring.aop.auto配置项决定是否启用AOP，默认启用
 * 
 * 默认是使用基于JDK的动态代理来实现AOP
 * spring.aop.proxy-target-class=false 或者不配置，表示使用JDK的动态代理
 * =true表示使用CGLIB
 * 如果配置了false，而类没有接口，则依然使用CGLIB
 */
@Slf4j
@EnableAspectJAutoProxy
@SpringBootApplication
public class AopDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AopDemoApplication.class, args);
		log.info(context.getBean(IUserDao.class).getClass().toString());
		System.out.println();
		context.getBean(IUserDao.class).add("admin", "123");
		context.close();
	}

}
