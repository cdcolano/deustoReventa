package dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;


import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;


/** Clase que crea un dao de Producto
 * @author Jon Eguiluz
 *
 */
public class ProductoDAO implements IProductoDAO{

	private PersistenceManagerFactory pmf;

	/**
	 * Constructor de ProductoDAO
	 */
	public ProductoDAO() {
		// TODO Auto-generated constructor stub
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}
	
	/**
	 *Metodo para almacenar un producto en la BD
	 */
	public void storeProducto(Producto producto) {
		this.storeObject(producto);
	}
	
	/** Metodo que almacena un objeto en la BD
	 * @param object objeto de cualquier clase a guardar en la BD
	 */
	private void storeObject(Object object) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			System.out.println("   * Storing an Producto: " + object);
			pm.makePersistent(object);
			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error storing an object: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}
	
	
	/**
	 * Metodo que devuelve la lista de todos los productos de la BD
	 */
	public List<Producto> getProductos() {
		PersistenceManager pm = pmf.getPersistenceManager();
		/*
		 * By default only 1 level is retrieved from the db so if we wish to fetch more
		 * than one level, we must indicate it
		 */

		Transaction tx = pm.currentTransaction();
		List<Producto> productos = new ArrayList<>();

		try {
			System.out.println("   * Retrieving an Extent for Products.");

			tx.begin();
			Extent<Producto> extent = pm.getExtent(Producto.class, true);

			for (Producto p : extent) {
				System.out.println(p.getPrecioSalida());
				productos.add(p);
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return productos;
	}
	
	
	/**
	 *Metodo que devuelve la lista de todos los productos de tipo ordenador de la BD
	 */
	public List<ProductoOrdenador> getProductosOrdenador() {
		PersistenceManager pm = pmf.getPersistenceManager();
		/*
		 * By default only 1 level is retrieved from the db so if we wish to fetch more
		 * than one level, we must indicate it
		 */
	

		Transaction tx = pm.currentTransaction();
		List<ProductoOrdenador> productos = new ArrayList<>();

		try {
			System.out.println("   * Retrieving an Extent for Products.");

			tx.begin();
			Extent<ProductoOrdenador> extent = pm.getExtent(ProductoOrdenador.class, true);

			for (ProductoOrdenador p : extent) {
				productos.add(p);
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return productos;
	}
	
	/**
	 * Metodo que devuelve una lista con todos los productos de tipo ordenador que estan a la venta en la BD (vendido != true) incluyendo los reservados 
	 */
	public List<ProductoOrdenador> getProductosOrdenadorEnVentaConReservado(){
		PersistenceManager pm = pmf.getPersistenceManager();
		/*
		 * By default only 1 level is retrieved from the db so if we wish to fetch more
		 * than one level, we must indicate it
		 */
		

		Transaction tx = pm.currentTransaction();
		List<ProductoOrdenador> productos = new ArrayList<ProductoOrdenador	>();

		try {
			System.out.println("   * Retrieving an Extent for Products.");

			tx.begin();
			Extent<ProductoOrdenador> extent = pm.getExtent(ProductoOrdenador.class, true);

			for (ProductoOrdenador p : extent) {
				System.out.println(p.getNombre());
				if (!(p.isVendido())) {
					ProductoOrdenador p1=new ProductoOrdenador();
					System.out.println("Se añade");
					productos.add(p1);
					p1.setNombre(p.getNombre());
					p1.setCategoria(p.getCategoria());
					p1.setId(p.getId());
					p1.setFechaPubli(p.getFechaPubli());
					p1.setPrecioSalida(p.getPrecioSalida());
					p1.setOfertasRecibidas(p.getOfertasRecibidas());
					p1.setReservado(p.isReservado());
					p1.setVendido(p.isVendido());
					p1.setEmailVendedor(p.getEmailVendedor());
					p1.setCpu(p.getCpu());
					p1.setMemoria(p.getMemoria());
					p1.setGrafica(p.getGrafica());
					p1.setRam(p.getRam());
					p1.setPlacaBase(p.getPlacaBase());
				}
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		for (ProductoOrdenador p1:productos) {
			System.out.println(p1.getNombre());
		}

		return productos;

	}
	
	/**
	 *Metodo que devuelve una lista con todos los productos de tipo ordenador que estan a la venta en la BD (vendido != true)
	 */
	public List<ProductoOrdenador> getProductosOrdenadorEnVenta() {
		PersistenceManager pm = pmf.getPersistenceManager();
		/*
		 * By default only 1 level is retrieved from the db so if we wish to fetch more
		 * than one level, we must indicate it
		 */
		

		Transaction tx = pm.currentTransaction();
		List<ProductoOrdenador> productos = new ArrayList<ProductoOrdenador	>();

		try {
			System.out.println("   * Retrieving an Extent for Products.");

			tx.begin();
			Extent<ProductoOrdenador> extent = pm.getExtent(ProductoOrdenador.class, true);

			for (ProductoOrdenador p : extent) {
				System.out.println(p.getNombre());
				if (!(p.isReservado()||p.isVendido())) {
					ProductoOrdenador p1=new ProductoOrdenador();
					System.out.println("Se anade");
					productos.add(p1);
					p1.setNombre(p.getNombre());
					p1.setCategoria(p.getCategoria());
					p1.setId(p.getId());
					p1.setFechaPubli(p.getFechaPubli());
					p1.setPrecioSalida(p.getPrecioSalida());
					p1.setOfertasRecibidas(p.getOfertasRecibidas());
					p1.setReservado(p.isReservado());
					p1.setVendido(p.isVendido());
					p1.setEmailVendedor(p.getEmailVendedor());
					p1.setCpu(p.getCpu());
					p1.setMemoria(p.getMemoria());
					p1.setGrafica(p.getGrafica());
					p1.setRam(p.getRam());
					p1.setPlacaBase(p.getPlacaBase());
				}
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		for (ProductoOrdenador p1:productos) {
			System.out.println(p1.getNombre());
		}

		return productos;
	}
	
	
	/**
	 * Metodo que devuelve una lista con todos los productos de tipo vehiculo de la BD
	 */
	public List<ProductoVehiculo> getProductosVehiculos() {
		PersistenceManager pm = pmf.getPersistenceManager();
		/*
		 * By default only 1 level is retrieved from the db so if we wish to fetch more
		 * than one level, we must indicate it
		 */

		Transaction tx = pm.currentTransaction();
		List<ProductoVehiculo> productos = new ArrayList<>();

		try {
			System.out.println("   * Retrieving an Extent for Products.");

			tx.begin();
			Extent<ProductoVehiculo> extent = pm.getExtent(ProductoVehiculo.class, true);

			for (ProductoVehiculo p : extent) {
				productos.add(p);
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return productos;
	}
	
	/**
	 * Metodo que devuelve una lista con todos los productos de tipo vehiculo y en venta de la BD (vendido != true) incluyendo los reservados
	 */
	public List<ProductoVehiculo> getProductosVehiculosEnVentaConReservado(){
		PersistenceManager pm = pmf.getPersistenceManager();
		/*
		 * By default only 1 level is retrieved from the db so if we wish to fetch more
		 * than one level, we must indicate it
		 */


		Transaction tx = pm.currentTransaction();
		List<ProductoVehiculo> productos = new ArrayList<ProductoVehiculo>();

		try {
			System.out.println("   * Retrieving an Extent for Products.");

			tx.begin();
			Extent<ProductoVehiculo> extent = pm.getExtent(ProductoVehiculo.class, true);

			for (ProductoVehiculo p : extent) {
				if(!(p.isVendido())) {
					ProductoVehiculo p1=new ProductoVehiculo();
					productos.add(p1);
					p1.setNombre(p.getNombre());
					p1.setCategoria(p.getCategoria());
					p1.setId(p.getId());
					p1.setFechaPubli(p.getFechaPubli());
					p1.setPrecioSalida(p.getPrecioSalida());
					p1.setOfertasRecibidas(p.getOfertasRecibidas());
					p1.setReservado(p.isReservado());
					p1.setVendido(p.isVendido());
					p1.setEmailVendedor(p.getEmailVendedor());
					p1.setKilometros(p.getKilometros());
					p1.setCaballos(p.getCaballos());
					p1.setAnyoFabri(p.getAnyoFabri());
					p1.setMarca(p.getMarca());
					p1.setModelo(p.getModelo());
				}
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return productos;
	}
	
	/**
	 * Metodo que devuelve una lista con todos los productos de tipo vehiculo y a la venta (vendido != true) de la BD
	 */
	public List<ProductoVehiculo> getProductosVehiculosEnVenta() {
		PersistenceManager pm = pmf.getPersistenceManager();
		/*
		 * By default only 1 level is retrieved from the db so if we wish to fetch more
		 * than one level, we must indicate it
		 */


		Transaction tx = pm.currentTransaction();
		List<ProductoVehiculo> productos = new ArrayList<ProductoVehiculo>();

		try {
			System.out.println("   * Retrieving an Extent for Products.");

			tx.begin();
			Extent<ProductoVehiculo> extent = pm.getExtent(ProductoVehiculo.class, true);

			for (ProductoVehiculo p : extent) {
				if(!(p.isReservado() || p.isVendido())) {
					ProductoVehiculo p1=new ProductoVehiculo();
					productos.add(p1);
					p1.setNombre(p.getNombre());
					p1.setCategoria(p.getCategoria());
					p1.setId(p.getId());
					p1.setFechaPubli(p.getFechaPubli());
					p1.setPrecioSalida(p.getPrecioSalida());
					p1.setOfertasRecibidas(p.getOfertasRecibidas());
					p1.setReservado(p.isReservado());
					p1.setVendido(p.isVendido());
					p1.setEmailVendedor(p.getEmailVendedor());
					p1.setKilometros(p.getKilometros());
					p1.setCaballos(p.getCaballos());
					p1.setAnyoFabri(p.getAnyoFabri());
					p1.setMarca(p.getMarca());
					p1.setModelo(p.getModelo());
				}
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error retrieving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return productos;
	}
	
	
	
	

	/** Setter del PersistenceManagerFactory
	 * @param pmf2 Valor que queremos asignar con el setter
	 */
	public void setPmf(PersistenceManagerFactory pmf2) {
		this.pmf=pmf2;
	}
	
}
