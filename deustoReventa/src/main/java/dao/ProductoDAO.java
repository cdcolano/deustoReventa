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
import serialization.Usuario;


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
	
	public void deleteAllProductos() {
		System.out.println("- Cleaning the DB...");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			
			// Getting ready for removing objects - Remove Relationships between User and other things
			Extent<Producto> extentU = pm.getExtent(Producto.class, true);
			
		
			// Updating the database so changes are considered before commit
			pm.flush();

			// Deleting All Products - Copies in Books will be deleted due to 'delete on cascade'
			Query<Producto> query2 = pm.newQuery(Producto.class);
			System.out.println(" * '" + query2.deletePersistentAll() + "' products deleted from the DB.");

			tx.commit();
		} catch (Exception ex) {
			System.err.println(" $ Error cleaning the DB: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			if (pm != null && !pm.isClosed()) {
				pm.close();
			}
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
	

	public void updateProducto(Producto prod) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			pm.makePersistent(prod);
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