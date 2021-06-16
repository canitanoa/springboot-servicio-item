package ar.com.springboot.ms.item.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.com.springboot.ms.item.feign.clientes.ProductoFeignClienteRest;
import ar.com.springboot.ms.item.models.Item;
import ar.com.springboot.ms.item.models.Producto;
import ar.com.springboot.ms.item.service.ItemService;

@Service
@Primary //Indicamos que es la principal, si tenemos dos implementaciones de clientes entonces se toma esta
public class ItemServiceFeignImpl implements ItemService{

	@Autowired
	private ProductoFeignClienteRest productoFeignClienteRest;
	
	@Override
	public List<Item> findAll() {
		
		//Obtenemos la lista de productos por medio del Cliente Feign que consume la API Producto
		List<Producto> productos = productoFeignClienteRest.listar();
		
		//Convertimos la lista de Productos en Items por medio de un stream
		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		
		//Obtenemos el producto por medio del Cliente Feign que consume la API Producto
		Producto producto = productoFeignClienteRest.detalle(id);
		
		return new Item(producto, cantidad);
	}

}
