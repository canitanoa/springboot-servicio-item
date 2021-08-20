package ar.com.springboot.ms.item.feign.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.springboot.ms.commons.models.entity.Producto;

//@FeignClient(name = "servicio-productos", url = "localhost:8001") // Conf sin integrar con Ribbon
@FeignClient(name = "servicio-productos") // Conf (para balanceo de carga)
public interface ProductoFeignClienteRest {

	@GetMapping("/listar")
	public List<Producto> listar();

	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id);
	
	@PostMapping("/crear")
	public Producto crear(@RequestBody Producto producto);
	
	@PutMapping("/editar/{id}")
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id);
	
	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable Long id);
	
	
	
	
	

}
