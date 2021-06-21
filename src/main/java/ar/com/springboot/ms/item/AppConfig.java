package ar.com.springboot.ms.item;

import java.util.Collections;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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
@EnableEurekaClient // Para que sea Cliente de Eureka y pueda registrarse en el server
@EnableFeignClients // Para crear Clientes REST (Feign) y consumir otros microservicios
@EnableCircuitBreaker // Para gestionar los errores
//@RibbonClient("servicio-productos") // Para integrar el Feign (cliente) con Ribbon (balanceador), se quita si esta conf Eureka
public class AppConfig {

	// Definicion del Cliente RestTemplate
	@Bean("clienteRestTemplate")
	@LoadBalanced // Con esta an0tacion se integra con Ribbon para el balanceo de carga
	public RestTemplate registrarRestTemplate() {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(3000);
		return new RestTemplate(httpRequestFactory);
	}

	// Definicion de Swagger
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("ar.com.springboot.ms.item.controllers"))
				.paths(PathSelectors.any()).build().apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("Items Service API", "Items Service API Description", "1.0", "http://ejempo.com/terms",
				new Contact("Canitano", "https://adriancanitano.com", "acanitano@gmail.com"), "LICENSE", "LICENSE URL",
				Collections.emptyList());
	}
	//........

}
