package dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.FetchPlan;
import javax.jdo.JDOHelper;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.datanucleus.api.jdo.JDOExtent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import serialization.Categoria;
import serialization.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class CategoriaDAOTest {
	@Mock
	PersistenceManagerFactory pmf;
	@Mock
	PersistenceManager pm;
	@Mock
	Transaction tx;
	
	CategoriaDAO dao;
	Categoria cat;
	
	@Before
	public void setup() {
		dao= new CategoriaDAO();
		dao.setPmf(pmf);
		cat= new Categoria();
		cat.setId(1);
	}
	
	@Test
	public void testStoreCategoria() {
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.currentTransaction()).thenReturn(tx);
		Categoria c1= new Categoria();
		when(pm.makePersistent(c1)).thenThrow(JDOUserException.class);
		when(tx.isActive()).thenReturn(true);
		dao.storeCategoria(cat);
		when(tx.isActive()).thenReturn(false);
		dao.storeCategoria(c1);
		assertTrue(true);
	}
	
	
	@Test
	public void testGetCategorias() {
		ExtentPruebas categorias= new ExtentPruebas();
		categorias.addCategoria(cat);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.currentTransaction()).thenReturn(tx);
		when(tx.isActive()).thenReturn(true);
		when(pm.getExtent(Categoria.class,true)).thenReturn(categorias);
		List<Categoria>categoriasLista=dao.getCategorias();
		when(pm.getExtent(Categoria.class,true)).thenThrow(JDOUserException.class);
		dao.getCategorias();
		assertEquals(categoriasLista.size(), 1);
		
	}
	
	
	@Test
	public void testPmf() {
		dao.setPmf(pmf);
		assertEquals(pmf, dao.getPmf());
	}

	
	
	
	class ExtentPruebas implements Extent<Categoria> {
		ArrayList<Categoria>lista= new ArrayList<>();
		
		public void addCategoria(Categoria cat) {
			lista.add(cat);
		}
		
		@Override
		public Iterator<Categoria> iterator() {
			return lista.iterator();
		}

		@Override
		public boolean hasSubclasses() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Class getCandidateClass() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PersistenceManager getPersistenceManager() {
			// TODO Auto-generated method stub
			return pm;
		}

		@Override
		public void closeAll() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void close(Iterator it) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void close() throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public FetchPlan getFetchPlan() {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
}


