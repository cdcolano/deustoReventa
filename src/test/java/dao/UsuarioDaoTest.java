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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dao.CategoriaDAOTest.ExtentPruebas;
import serialization.Categoria;
import serialization.Compra;
import serialization.Producto;
import serialization.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioDaoTest {
	@Mock
	PersistenceManagerFactory pmf;
	@Mock
	PersistenceManager pm;
	@Mock
	Transaction tx;
	
	UsuarioDAO dao;
	Usuario u;
	
	@Before
	public void setup() {
		dao= new UsuarioDAO();
		dao.setPmf(pmf);
		u= new Usuario();
		u.setEmail("a");
	}
	
	
		
		@Test
		public void testStoreUsuario() {
			when(pmf.getPersistenceManager()).thenReturn(pm);
			when(pm.currentTransaction()).thenReturn(tx);
			Usuario c1= new Usuario();
			when(pm.makePersistent(c1)).thenThrow(JDOUserException.class);
			when(tx.isActive()).thenReturn(true);
			dao.storeUsuario(u);
			when(tx.isActive()).thenReturn(false);
			dao.storeUsuario(c1);
			assertTrue(true);
		}


		@Test
		public void testGetUsuarios() {
			ExtentPruebas usuarios= new ExtentPruebas();
			usuarios.addUsuario(u);
			when(pmf.getPersistenceManager()).thenReturn(pm);
			when(pm.currentTransaction()).thenReturn(tx);
			when(tx.isActive()).thenReturn(true);
			when(pm.getExtent(Usuario.class,true)).thenReturn(usuarios);
			List<Usuario>usuariosLista=dao.getUsuarios();
			when(pm.getExtent(Usuario.class,true)).thenThrow(JDOUserException.class);
			dao.getUsuarios();
			assertEquals(usuariosLista.size(), 1);
		}
		
		
		




		@Test
		public void testGetUsuario() {
			when(pmf.getPersistenceManager()).thenReturn(pm);
			when(pm.getObjectById(Usuario.class, "a")).thenReturn(u);
			when(pm.getObjectById(Usuario.class, "b")).thenThrow(JDOUserException.class);
			Usuario u1=dao.getUsuario("a");
			assertEquals(u,u1);
			Usuario u2=dao.getUsuario("b");
			assertEquals(null,u2);
		}
		
		
		
		@Test
		public void testPmf() {
			dao.setPmf(pmf);
			assertEquals(pmf, dao.getPmf());
		}
	
	
	
	class ExtentPruebas implements Extent<Usuario> {
		ArrayList<Usuario>lista= new ArrayList<>();
		
		public void addUsuario(Usuario cat) {
			lista.add(cat);
		}
		
		@Override
		public Iterator<Usuario> iterator() {
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
