package ar.com.springboot.ms.item.service;

import java.util.List;

import ar.com.springboot.ms.item.models.Item;

public interface ItemService {
	
	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);

}
