package dao;

import java.util.List;

import deustoReventa.serialization.Producto;

public interface IProductoDAO {
	public void storeProducto(Producto prod);
	public List<Producto> getProductos();
}
