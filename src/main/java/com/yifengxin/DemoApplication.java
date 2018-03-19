package com.yifengxin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author huangpeiyuan
 * 2018年3月3日
 */
@ServletComponentScan
@SpringBootApplication
@MapperScan("com.yifengxin.dao")//将项目中对应的mapper类的路径加进来就可以了
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
