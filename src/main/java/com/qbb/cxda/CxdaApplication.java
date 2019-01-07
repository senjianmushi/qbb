package com.qbb.cxda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,})
@MapperScan({"com.qbb.cxda.cmm.dao"})
@ComponentScan(basePackages={"com.qbb.cxda","com.qbb.cxda.util"})
public class CxdaApplication {
	public static void main(String[] args) {
		SpringApplication.run(CxdaApplication.class, args);
	}
}
