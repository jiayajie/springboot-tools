package com.example.baseproject;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BaseprojectApplication  {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(BaseprojectApplication.class);
		application.setBannerMode(Banner.Mode.OFF);//禁用banner
		application.run(args);
	}
}
