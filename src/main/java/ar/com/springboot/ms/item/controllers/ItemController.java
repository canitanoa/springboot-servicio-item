package ar.com.springboot.ms.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import ar.com.springboot.ms.commons.models.entity.Producto;
import ar.com.springboot.ms.item.models.Item;
import ar.com.springboot.ms.item.service.ItemService;

@RefreshScope // Nos permite actualizar valores de config sin tener que reiniciar la app
@RestController
public class ItemController {

	private static Logger log = LoggerFactory.getLogger(ItemController.class);

	@Autowired
//	@Qualifier("ItemServiceRestTemplateImpl") //Imple del Cliente RestTemplate
	@Qualifier("ItemServiceFeignImpl") // Imple del Cliente Feign
	private ItemService itemService;
	
	//Para impl Resilience4J con CircuitBreaker
	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;
	
	// Obtenemos el ambiente
	@Autowired
	private Environment environment;

	// Obtener valores de properties de config-server
//	@Value("${configuracion.texto}")
	private String texto;
//	@Value("${server.port}")
	private String port;

	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig() {

		log.info(texto);

		Map<String, String> json = new HashMap<String, String>();
		json.put("texto", texto);
		json.put("puerto", port);

//		if(environment.getActiveProfiles().length>0) {
//			if(environment.getActiveProfiles()[0].equals("prod")) {
		json.put("autor.nombre", environment.getProperty("configuracion.autor.nombre"));
		json.put("autor.email", environment.getProperty("configuracion.autor.email"));
//			}
//		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	@GetMapping("/listar")
	public List<Item> listar(@RequestParam(name="nombre", required = false) String nombre, @RequestHeader(name="token-request", required = false) String tokenRequest) {
		System.out.println("nombre: " + nombre);
		System.out.println("token-request: " + tokenRequest);
		
		return itemService.findAll();
	}

	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return itemService.save(producto);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		return itemService.update(producto,id);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		itemService.deleteById(id);
	}
	
//	@HystrixCommand(fallbackMethod = "metodoTolerFallo")
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		// Para Hystrix
		// return itemService.findById(id, cantidad);
		
		// Para Resilience4J de forma programatica con lamda
		return circuitBreakerFactory.create("items").run(
				() -> itemService.findById(id, cantidad),
				e -> metodoTolerFallo(id, cantidad, e)
				);
		
		
	}

	public Item metodoTolerFallo(Long id, Integer cantidad, Throwable e) {
		
		log.info("metodoTolerFallo: " + e.getMessage());
		Item item = new Item();
		Producto producto = new Producto();
		
		producto.setId(id);
		producto.setPrecio(Double.valueOf(0));
		
		return item;
//		return new Item();
	}

}
