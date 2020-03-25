package com.douzone.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.douzone.mysite.config.app.DBConfig;
import com.douzone.mysite.config.app.MyBatisConfig;

// 여러 설정들을 모으는 클래스

@Configuration
@EnableAspectJAutoProxy			// @Aspect 들을 proxy(AOP)
@ComponentScan({"com.douzone.mysite.service", "com.douzone.mysite.repository", "com.douzone.mysite.aspect"})
@Import({DBConfig.class, MyBatisConfig.class})		// app config class들을 import함
public class AppConfig {

}
