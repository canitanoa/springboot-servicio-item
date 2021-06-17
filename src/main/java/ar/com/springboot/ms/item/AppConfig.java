package ar.com.springboot.ms.item;

import java.util.Collections;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // Para crear las configuraciones
@EnableSwagger2 // Para la Auto-Documentacion
@EnableFeignClients // Habilita los clientes Feign que tengamos en el proyecto y permite inyectarlos en los Servicios del Proyecto
@RibbonClient("servicio-productos") // Para integrar Fein con Ribbon
public class AppConfig {

	@Bean("clienteRestTemplate")
	@LoadBalanced //Con esta anitacion se integra con Ribbon para el balanceo de carga
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("ar.com.springboot.ms.item.controllers"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo());
	}
	
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Items Service API",
				"Items Service API Description",
				"1.0",
				"http://ejempo.com/terms",
				new Contact("Canitano", "https://adriancanitano.com", "acanitano@gmail.com"),
				"LICENSE",
				"LICENSE URL",
				Collections.emptyList()
				);
	}

}
