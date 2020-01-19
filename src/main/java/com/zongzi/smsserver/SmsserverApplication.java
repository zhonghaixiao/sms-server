package com.zongzi.smsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SmsserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsserverApplication.class, args);
	}



}
