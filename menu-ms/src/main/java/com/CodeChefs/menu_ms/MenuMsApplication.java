package com.CodeChefs.menu_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class MenuMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MenuMsApplication.class, args);
	}

}
