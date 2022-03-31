package dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;


import deustoReventa.serialization.Producto;
import deustoReventa.serialization.Usuario;

public class ProductoDAO implements IProductoDAO{

	private PersistenceManagerFactory pmf;

	public ProductoDAO() {
		// TODO Auto-generated constructor stub
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}
	
	public void storeProducto(Producto producto) {
		this.storeObject(producto);
	}
	
	private void storeObject(Object object) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			System.out.println("   * Storing an object: " + object);
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
	
	public List<Producto> getProductos() {
		PersistenceManager pm = pmf.getPersistenceManager();
		/*
		 * By default only 1 level is retrieved from the db so if we wish to fetch more
		 * than one level, we must indicate it
		 */
		pm.getFetchPlan().setMaxFetchDepth(3);

		Transaction tx = pm.currentTransaction();
		List<Producto> productos = new ArrayList<>();

		try {
			System.out.println("   * Retrieving an Extent for Products.");

			tx.begin();
			Extent<Producto> extent = pm.getExtent(Producto.class, true);

			for (Producto p : extent) {
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
	

	public void updateProducto(Producto producto) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			pm.makePersistent(producto);
			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error updating: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
	}
	
}