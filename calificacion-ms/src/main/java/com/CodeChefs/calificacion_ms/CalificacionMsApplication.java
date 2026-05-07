package com.CodeChefs.calificacion_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class CalificacionMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalificacionMsApplication.class, args);
	}

}
