package ar.com.springboot.ms.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.springboot.ms.item.models.Item;
import ar.com.springboot.ms.item.service.ItemService;

@RestController
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping("/listar")
//	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public List<Item> listar(){
		return itemService.findAll();
	}
	
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
//	@RequestMapping(value = "/listar/{id}", method = RequestMethod.GET)
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad){
		return itemService.findById(id,cantidad);
	}

}
