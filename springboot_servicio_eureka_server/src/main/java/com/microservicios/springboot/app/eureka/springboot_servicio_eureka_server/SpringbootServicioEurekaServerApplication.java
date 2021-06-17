package com.microservicios.springboot.app.eureka.springboot_servicio_eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SpringbootServicioEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioEurekaServerApplication.class, args);
	}

}
