package com.sergio.curso.springboot.app.springboot_crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@PropertySource("classpath:messages.properties") // tuve que cerrar y volver a levantar la aplicación // puede desacoplarse en una clase de configuración aparte
public class SpringbootCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCrudApplication.class, args);
	}

}
