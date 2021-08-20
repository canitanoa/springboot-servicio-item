package ar.com.springboot.ms.item.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.com.springboot.ms.commons.models.entity.Producto;
import ar.com.springboot.ms.item.models.Item;
import ar.com.springboot.ms.item.service.ItemService;

@Service("ItemServiceRestTemplateImpl")
public class ItemServiceRestTemplateImpl implements ItemService {

	private static String URI = "http://localhost:8001";
	private static String URI_balance = "http://servicio-productos";

	@Autowired
	private RestTemplate clienteRestTemplate;

	@Override
	public List<Item> findAll() {

		String URI = "";

		// Consumimos por medio del Cliente RestTemplate la lista de productos del MS
		// Productos
		// Si usamos "URI" la solicitud es comun
		// Si usamos "URI_balance" la solicitu es balanceada por Ribbon
		List<Producto> productos = Arrays
				.asList(clienteRestTemplate.getForObject(URI_balance + "/listar", Producto[].class));

		// Convertimos la lista de Productos en Items por medio de un stream
		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {

		// Creamos un Map para setear las variables que van a la URI
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());

		// Consumimos por medio del Cliente RestTemplate el producto por id del MS
		// Productos
		// Si usamos "URI" la solicitud es comun
		// Si usamos "URI_balance" la solicitu es balanceada por Ribbon
		Producto producto = clienteRestTemplate.getForObject(URI_balance + "/ver/{id}", Producto.class, pathVariables);

		return new Item(producto, cantidad);
	}

	@Override
	public Producto save(Producto producto) {

		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);

		ResponseEntity<Producto> response = clienteRestTemplate.exchange(URI_balance + "/crear", HttpMethod.POST, body,
				Producto.class);
		Producto productoResp = response.getBody();

		return productoResp;
	}

	@Override
	public Producto update(Producto producto, Long id) {

		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());

		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);

		ResponseEntity<Producto> response = clienteRestTemplate.exchange(URI_balance + "/editar/{id}", HttpMethod.PUT,
				body, Producto.class, pathVariables);
		Producto productoResp = response.getBody();

		return productoResp;
	}

	@Override
	public void deleteById(Long id) {

		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		clienteRestTemplate.delete(URI_balance + "/eliminar/{id}", pathVariables);

	}

}
