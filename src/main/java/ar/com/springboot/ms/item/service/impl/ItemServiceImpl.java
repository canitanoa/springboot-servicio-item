package ar.com.springboot.ms.item.service.impl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.com.springboot.ms.item.models.Item;
import ar.com.springboot.ms.item.models.Producto;
import ar.com.springboot.ms.item.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() {
		
		//Consumimos por medio del Cliente RestTemplate la lista de productos del MS Productos
		List<Producto> productos = Arrays.asList(
				clienteRest.getForObject("http://localhost:8001/listar", Producto[].class));
		
		//Convertimos la lista de Productos en Items por medio de un stream
		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		
		//Creamos un Map para setear las variables que van a la URI
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		//Consumimos por medio del Cliente RestTemplate el producto por id del MS Productos
		Producto producto = 
				clienteRest.getForObject("http://localhost:8001/ver/{id}", Producto.class, pathVariables);
		
		return new Item(producto, cantidad);
	}

}
