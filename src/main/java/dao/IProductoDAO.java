package dao;

import java.util.List;

import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;

/**Interfaz que implementa el ProductoDao
 * @author Usuario
 *
 */
public interface IProductoDAO {
	/**Metodo que guarda el producto de BD
	 * @param prod producto que se guarda
	 */
	public void storeProducto(Producto prod);
	/**Metodo que devuelve la lista de productos
	 * @return lista de productos
	 */
	public List<Producto> getProductos();
	/**metodo que devuelve la lista de productos vehiculos
	 * @return lista de productos vehiculo
	 */
	public List<ProductoVehiculo> getProductosVehiculos();
	/**metodo que devuelve la lista de productos ordenador
	 * @return lista de productos ordenador
	 */
	public List<ProductoOrdenador> getProductosOrdenador();
	/**metodo que devuelve la lista de productos ordenador en venta
	 * @returnlista de productos ordenador en venta
	 */
	public List<ProductoOrdenador> getProductosOrdenadorEnVenta();
	/**metodo que devuelve la lista de productos vehiculos en venta
	 * @return lista de preductos vehiculos en venta
	 */
	public List<ProductoVehiculo> getProductosVehiculosEnVenta();
	/**metodo que devuelve la lista de productos ordenador que esten en venta pero reservados
	 * @return lista de productos ordenador en venta y reservados
	 */
	public List<ProductoOrdenador> getProductosOrdenadorEnVentaConReservado();
	/**metodo que devuelve la lista de productos vehiculo en venta pero que esten reservados
	 * @return lista de vehiculos en venta pero reservados
	 */
	public List<ProductoVehiculo> getProductosVehiculosEnVentaConReservado();
}
