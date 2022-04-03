package dao;

import java.util.List;

import serialization.Producto;

public interface IProductoDAO {
	public void storeProducto(Producto prod);
	public List<Producto> getProductos();
	public void deleteAllProductos();
	public void updateProducto(Producto p);
	
}
