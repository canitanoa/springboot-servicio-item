package ar.com.springboot.ms.item.feign.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ar.com.springboot.ms.item.models.Producto;

//@FeignClient(name = "servicio-productos", url = "localhost:8001") // Conf sin integrar con Ribbon
@FeignClient(name = "servicio-productos") // Conf (para balanceo de carga)
public interface ProductoFeignClienteRest {

	@GetMapping("/listar")
	public List<Producto> listar();

	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id);

}
