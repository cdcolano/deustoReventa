package dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import serialization.Categoria;
import serialization.*;

/**Clase que crea un dao de Usuario
 * @author Jon Eguiluz
 *
 */
public class UsuarioDAO implements IUsuarioDAO {
	

	private PersistenceManagerFactory pmf;

	/**
	 * Constructor de la clase
	 */
	public UsuarioDAO() {
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}

	/**
	 * Metodo para almacenar un usuario en la BD
	 */
	public void storeUsuario(Usuario usuario) {
		this.storeObject(usuario);
	}

	/** Metodo que almacena un objeto en la BD
	 * @param object objeto de cualquier clase a guardar en la BD
	 */
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

	/**
	 * Metodo que devuelve una lista con todos los usuarios de la BD
	 */
	public List<Usuario> getUsuarios() {
		PersistenceManager pm = pmf.getPersistenceManager();
		/*
		 * By default only 1 level is retrieved from the db so if we wish to fetch more
		 * than one level, we must indicate it
		 */

		Transaction tx = pm.currentTransaction();
		List<Usuario> usuarios = new ArrayList<>();

		try {
			System.out.println("   * Retrieving an Extent for Products.");

			tx.begin();
			Extent<Usuario> extent = pm.getExtent(Usuario.class, true);

			for (Usuario u : extent) {
				usuarios.add(u);
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

		return usuarios;
	}
	
	
	




	
	
	@SuppressWarnings("unchecked")
/*	public List<Product> getProducts(String condition) {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);

		Transaction tx = pm.currentTransaction();
		List<Product> products = new ArrayList<Product>();

		try {
			System.out.println("   * Executing a Query for Products given a condition: " + condition);

			tx.begin();
			Extent<Product> extent = pm.getExtent(Product.class, true);
			Query<Product> query = pm.newQuery(extent, condition);

			for (Product product : (List<Product>) query.execute()) {
				products.add(product);
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return products;
	}*/
	
	/**
	 * Metodo que devuelve un objeto usuario de la BD a traves de su email
	 */

	public Usuario getUsuario(String email) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Usuario usuario = null;

		try {
			usuario=pm.getObjectById(Usuario.class, email);
			pm.makeTransient(usuario);
//			System.out.println(usuario.getEmail());

		} catch (Exception ex) {
			System.out.println("   $ Error no existe ese usuario: " + ex.getMessage());
			return null;
		} finally {
			//System.out.println(usuario.getEmail());
			pm.close();
		}

		return usuario;
	}
	
	
	/*public int getComprasSize() {
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);

		Transaction tx = pm.currentTransaction();
		int resultado=0;

		try {
			System.out.println("   * Querying a Compras Size: ");

			tx.begin();
			Query<?> query = pm.newQuery("SELECT COUNT(*)  FROM " + Compra.class.getName());
			resultado = (Integer) query.execute();
			tx.commit();

		} catch (Exception ex) {
			System.out.println("   $ Error retreiving an extent: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}

		return resultado;
	}
	*/
	/*public void updateUsuario(Usuario usuario, Compra c) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Usuario u=pm.getObjectById(Usuario.class,usuario.getEmail());
		Usuario temporal=u.clone();
		temporal.getCompras().add(c);
		pm.deletePersistent(u);
		pm.makePersistent(temporal);
		pm.close();
	}
	*/
	
	

	/*public void deleteUsuario(Usuario usuario) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			pm.deletePersistent(usuario);
			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error updating: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}*/
	
	/** getter del PersistanceManagerFactory
	 * @return devuelve el PersistanceManagerFactory
	 */
	public PersistenceManagerFactory getPmf() {
		return pmf;
	}

	/** setter del PersistanceManagerFactory
	 * @param pmf recibe como parametro el valor a asignar al PersistanceManagerFactory
	 */
	public void setPmf(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}

/*	public void updateVentasUsuario(String email, Producto p) {
		PersistenceManager pm = pmf.getPersistenceManager();
		try {
			Usuario u=pm.getObjectById(Usuario.class,email);
			u.getProductos().add(p);
		} catch (Exception ex) {
			System.out.println("   $ Error updating: " + ex.getMessage());
		} finally {
			pm.close();
		}
	}

	public void deleteAllUsuarios() {
		System.out.println("- Cleaning the DB...");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			
			// Getting ready for removing objects - Remove Relationships between User and other things
			Extent<Usuario> extentU = pm.getExtent(Usuario.class, true);
			
		
			// Updating the database so changes are considered before commit
			pm.flush();

			// Deleting All Products - Copies in Books will be deleted due to 'delete on cascade'
			Query<Usuario> query2 = pm.newQuery(Usuario.class);
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
	}*/

}
