package ar.com.springboot.ms.item.models;

import org.apache.commons.lang.StringUtils;

import ar.com.springboot.ms.commons.models.entity.Producto;

public class Item {

	private Producto producto;
	private Integer cantidad;

	public Item() {
	}

	public Item(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getTotal() {
		Double tot = 0.0;
		
		if (producto == null || cantidad == null)
		{
			return tot;
		}else {
			
		}
		return producto.getPrecio() * cantidad.doubleValue();
	}

}
