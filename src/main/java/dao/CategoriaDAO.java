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
import serialization.Usuario;


/**Clase que crea un dao de Categoria
 * @author Ander
 *
 */
public class CategoriaDAO implements ICategoriaDAO {
	
	private PersistenceManagerFactory pmf;

	/**
	 * Metodo que construye una CategoriaDao
	 */
	public CategoriaDAO() {
		// TODO Auto-generated constructor stub
		pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}
	
	/**
	 *Metodo que guarda una categoria en la Bd
	 */
	public void storeCategoria(Categoria categoria) {
		this.storeObject(categoria);
	}
	
	/**Metodo que guarda un objeto en BD
	 * @param object objeto de cualquier clase
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
	 *Metodo que saca la lista de categorias
	 */
	public List<Categoria> getCategorias() {
		PersistenceManager pm = pmf.getPersistenceManager();
		/*
		 * By default only 1 level is retrieved from the db so if we wish to fetch more
		 * than one level, we must indicate it
		 */
		

		Transaction tx = pm.currentTransaction();
		List<Categoria> categorias = new ArrayList<>();

		try {
			System.out.println("   * Retrieving an Extent for Products.");

			tx.begin();
			Extent<Categoria> extent = pm.getExtent(Categoria.class, true);

			for (Categoria c : extent) {
				categorias.add(c);
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

		return categorias;
	}

	/**Metodo get de persistenceManagerFactory
	 * @return devuelve el persistenceManagerFatory
	 */
	public PersistenceManagerFactory getPmf() {
		return pmf;
	}

	/**Metodo set de persistenceManagerFactory
	 * @param pmf persistenceManagerFactory que se hace set
	 */
	public void setPmf(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}
}
