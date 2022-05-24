package dao;

import java.util.List;

import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;

public interface IProductoDAO {
	public void storeProducto(Producto prod);
	public List<Producto> getProductos();
	public List<ProductoVehiculo> getProductosVehiculos();
	public List<ProductoOrdenador> getProductosOrdenador();
	public List<ProductoOrdenador> getProductosOrdenadorEnVenta();
	public List<ProductoVehiculo> getProductosVehiculosEnVenta();
	public List<ProductoOrdenador> getProductosOrdenadorEnVentaConReservado();
	public List<ProductoVehiculo> getProductosVehiculosEnVentaConReservado();
}
