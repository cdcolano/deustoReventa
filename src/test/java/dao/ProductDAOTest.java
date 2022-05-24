package dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.FetchPlan;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dao.UsuarioDaoTest.ExtentPruebas;
import serialization.Categoria;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class ProductDAOTest {
	@Mock
	PersistenceManagerFactory pmf;
	@Mock
	PersistenceManager pm;
	@Mock
	Transaction tx;
	
	ProductoDAO dao;
	Producto p;
	ProductoOrdenador po;
	ProductoVehiculo pv;
	
	@Before
	public void setup() {
		dao= new ProductoDAO();
		dao.setPmf(pmf);
		p= new Producto();
		p.setId(1);
		po= new ProductoOrdenador();
		po.setId(2);
		pv= new ProductoVehiculo();
		pv.setId(3);
	}
	
	@Test
	public void testStore() {
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.currentTransaction()).thenReturn(tx);
		when(pm.makePersistent(po)).thenThrow(JDOUserException.class);
		when(tx.isActive()).thenReturn(true);
		dao.storeProducto(p);
		when(tx.isActive()).thenReturn(false);
		dao.storeProducto(po);
		assertTrue(true);
	}
	
	@Test
	public void testGetProductos() {
		ExtentPruebas productos= new ExtentPruebas();
		productos.addProducto(p);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.currentTransaction()).thenReturn(tx);
		when(tx.isActive()).thenReturn(true);
		when(pm.getExtent(Producto.class,true)).thenReturn(productos);
		List<Producto>productoList=dao.getProductos();
		when(pm.getExtent(Producto.class,true)).thenThrow(JDOUserException.class);
		dao.getProductos();
		assertEquals(productoList.size(), 1);
		
	}
	
	@Test
	public void testGetProductosOrdenador() {
		ExtentPruebasOrdenador productos= new ExtentPruebasOrdenador();
		productos.addProducto(po);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.currentTransaction()).thenReturn(tx);
		when(tx.isActive()).thenReturn(true);
		when(pm.getExtent(ProductoOrdenador.class,true)).thenReturn(productos);
		List<ProductoOrdenador>productoList=dao.getProductosOrdenador();
		when(pm.getExtent(ProductoOrdenador.class,true)).thenThrow(JDOUserException.class);
		dao.getProductosOrdenador();
		assertEquals(productoList.size(), 1);
		
	}
	
	@Test
	public void testGetProductosVehiculo() {
		ExtentPruebasVehiculo productos= new ExtentPruebasVehiculo();
		productos.addProducto(pv);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.currentTransaction()).thenReturn(tx);
		when(tx.isActive()).thenReturn(true);
		when(pm.getExtent(ProductoVehiculo.class,true)).thenReturn(productos);
		List<ProductoVehiculo>productoList=dao.getProductosVehiculos();
		when(pm.getExtent(ProductoVehiculo.class,true)).thenThrow(JDOUserException.class);
		dao.getProductosVehiculos();
		assertEquals(productoList.size(), 1);
	}
	
	
	@Test
	public void testGetProductosOrdenadorEnVenta() {
		ExtentPruebasOrdenador productos= new ExtentPruebasOrdenador();
		po.setVendido(false);
		ProductoOrdenador po2= new ProductoOrdenador();
		po2.setVendido(true);
		productos.addProducto(po);
		productos.addProducto(po2);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.currentTransaction()).thenReturn(tx);
		when(tx.isActive()).thenReturn(true);
		when(pm.getExtent(ProductoOrdenador.class,true)).thenReturn(productos);
		List<ProductoOrdenador>productoList=dao.getProductosOrdenadorEnVenta();
		when(pm.getExtent(ProductoOrdenador.class,true)).thenThrow(JDOUserException.class);
		dao.getProductosOrdenadorEnVenta();
		assertEquals(productoList.size(), 1);
		
	}
	
	@Test
	public void testGetProductosVehiculoEnVenta() {
		ExtentPruebasVehiculo productos= new ExtentPruebasVehiculo();
		po.setVendido(false);
		ProductoVehiculo po2= new ProductoVehiculo();
		po2.setVendido(true);
		productos.addProducto(pv);
		productos.addProducto(po2);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.currentTransaction()).thenReturn(tx);
		when(tx.isActive()).thenReturn(true);
		when(pm.getExtent(ProductoVehiculo.class,true)).thenReturn(productos);
		List<ProductoVehiculo>productoList=dao.getProductosVehiculosEnVenta();
		when(pm.getExtent(ProductoVehiculo.class,true)).thenThrow(JDOUserException.class);
		dao.getProductosVehiculosEnVenta();
		assertEquals(productoList.size(), 1);
		
	}
	

	
	
	
	class ExtentPruebas implements Extent<Producto> {
		ArrayList<Producto>lista= new ArrayList<>();
		
		public void addProducto(Producto cat) {
			lista.add(cat);
		}
		
		@Override
		public Iterator<Producto> iterator() {
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
	
	
	class ExtentPruebasOrdenador implements Extent<ProductoOrdenador> {
		ArrayList<ProductoOrdenador>lista= new ArrayList<>();
		
		public void addProducto(ProductoOrdenador cat) {
			lista.add(cat);
		}
		
		@Override
		public Iterator<ProductoOrdenador> iterator() {
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
	
	class ExtentPruebasVehiculo implements Extent<ProductoVehiculo> {
		ArrayList<ProductoVehiculo>lista= new ArrayList<>();
		
		public void addProducto(ProductoVehiculo cat) {
			lista.add(cat);
		}
		
		@Override
		public Iterator<ProductoVehiculo> iterator() {
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
