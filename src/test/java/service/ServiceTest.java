package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dao.CategoriaDAO;
import dao.ICategoriaDAO;
import dao.IProductoDAO;
import dao.IUsuarioDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import serialization.Categoria;
import serialization.Compra;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class ServiceTest {
	@Mock
	IProductoDAO productDao;
	@Mock
	IUsuarioDAO	usuarioDao;
	@Mock
	ICategoriaDAO categoriaDao;
	@Mock
	PersistenceManagerFactory pmf;
	@Mock
	PersistenceManager pm;
	
	
	VentasService vs;
	Usuario u;
	Producto p;
	Categoria c;
	@Before
	public void setUp() {
		vs= new VentasService();
		vs.setCategoriaDao(categoriaDao);
		vs.setPmf(pmf);
		vs.setProductDao(productDao);
		vs.setUsuarioDao(usuarioDao);
		
		u= new Usuario();
		u.setEmail("a");
		u.setPassword("a");
		
		p= new Producto();
		p.setId(1);
		p.setNombre("acer");
		
		c= new Categoria();
		c.setId(1);
		c.setNombre("ordenadores");
	}
	
	@Test
	public void testLogIn() {	
		when(usuarioDao.getUsuario("a")).thenReturn(u);
		when(usuarioDao.getUsuario("b")).thenThrow(JDOUserException.class);
		assertTrue(vs.logIn("a", "a"));
		assertFalse(vs.logIn(null, "a"));
		assertFalse(vs.logIn("a", "b"));
		assertFalse(vs.logIn("b", "b"));
		
	}
	
	@Test
	public void testGetCategorias(){
		ArrayList<Categoria>c=new ArrayList<>();
		Categoria c1= new Categoria();
		c1.setId(1);
		c1.setNombre("ordenador");
		c.add(c1);
		when(categoriaDao.getCategorias()).thenReturn(c);
		assertEquals(vs.getCategorias().size(), 1);
	}
	
	@Test
	public void testRegistro() {
		when(usuarioDao.getUsuario("a")).thenReturn(u);
		when(usuarioDao.getUsuario("b")).thenReturn(null);
		Usuario u1= new Usuario();
		u1.setEmail("b");
		assertFalse(vs.registro(u));
		assertTrue(vs.registro(u1));
	}
	
	@Test
	public void testComprarProducto() {
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Usuario.class, "a")).thenReturn(u);
		when(pm.getObjectById(Usuario.class, "b")).thenReturn(null);
		when(pm.getObjectById(Producto.class, 1)).thenReturn(p);
		when(pm.getObjectById(Producto.class, 3)).thenReturn(null);
		
		int sizeU=u.getCompras().size();
		vs.comprarProducto("a", 1, 100);
		assertEquals(sizeU+1, u.getCompras().size());
		assertEquals(u.getCompras().get(sizeU).getPrecio(), 100,0);
		assertEquals(u.getCompras().get(sizeU).getProducto().getId(),1);
		assertTrue(p.isVendido());
		
		vs.comprarProducto("a", 3, 100);
		vs.comprarProducto("b", 3, 100);
		vs.comprarProducto("b", 1, 100);
	}
	
	@Test
	public void testPonerALaVenta() {
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Usuario.class, "a")).thenReturn(u);
		when(pm.getObjectById(Usuario.class, "b")).thenThrow(JDOUserException.class);
		int sizeU=u.getProductos().size();
		vs.ponerALaVenta("a", p);
		assertEquals(sizeU+1, u.getProductos().size());
		vs.ponerALaVenta("b", p);
	}

	@Test
	public void testGetProducto() {
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Producto.class, 1)).thenReturn(p);
		when(pm.getObjectById(Producto.class, 2)).thenThrow(JDOUserException.class);
		
		Producto p1=vs.getProducto(1);
		Producto p2=vs.getProducto(2);
		assertEquals(p2, null);
		assertEquals(p1,p);
		
	}
	
	@Test
	public void testGetProductos() {
		ArrayList<Producto>prods= new ArrayList<>();
		prods.add(p);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(productDao.getProductos()).thenReturn(prods);
		
		List<Producto> prod=vs.getProductos();
		assertEquals(p, prod.get(0));
		when(productDao.getProductos()).thenThrow(JDOUserException.class);
		assertEquals(vs.getProductos().size(),0);
	}
	
	@Test
	public void testGetProductosOrdenador() {
		ArrayList<ProductoOrdenador>prods= new ArrayList<>();
		ProductoOrdenador p= new ProductoOrdenador();
		p.setId(2);
		prods.add(p);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(productDao.getProductosOrdenador()).thenReturn(prods);
		
		List<ProductoOrdenador> prod=vs.getProductosOrdenador();
		assertEquals(p, prod.get(0));
		when(productDao.getProductosOrdenador()).thenThrow(JDOUserException.class);
		assertEquals(vs.getProductosOrdenador().size(),0);
		
	}
	
	
	@Test
	public void testGetProductosOrdenadorEnVenta() {
		ArrayList<ProductoOrdenador>prods= new ArrayList<>();
		ProductoOrdenador p= new ProductoOrdenador();
		p.setVendido(false);
		p.setId(2);
		prods.add(p);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(productDao.getProductosOrdenadorEnVenta()).thenReturn(prods);
		
		List<ProductoOrdenador> prod=vs.getProductosOrdenadorEnVenta();
		assertEquals(p, prod.get(0));
		when(productDao.getProductosOrdenadorEnVenta()).thenThrow(JDOUserException.class);
		assertEquals(vs.getProductosOrdenadorEnVenta().size(),0);
		
	}
	
	@Test
	public void testGetProductosVehiculos() {
		ArrayList<ProductoVehiculo>prods= new ArrayList<>();
		ProductoVehiculo p= new ProductoVehiculo();
		p.setVendido(false);
		p.setId(2);
		prods.add(p);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(productDao.getProductosVehiculos()).thenReturn(prods);
		
		List<ProductoVehiculo> prod=vs.getProductosVehiculos();
		assertEquals(p, prod.get(0));
		when(productDao.getProductosVehiculos()).thenThrow(JDOUserException.class);
		assertEquals(vs.getProductosVehiculos().size(),0);
		
	}
	
	@Test
	public void testGetProductosVehiculosEnVenta() {
		ArrayList<ProductoVehiculo>prods= new ArrayList<>();
		ProductoVehiculo p= new ProductoVehiculo();
		p.setVendido(false);
		p.setId(2);
		prods.add(p);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(productDao.getProductosVehiculosEnVenta()).thenReturn(prods);
		
		List<ProductoVehiculo> prod=vs.getProductosVehiculosEnVenta();
		assertEquals(p, prod.get(0));
		when(productDao.getProductosVehiculosEnVenta()).thenThrow(JDOUserException.class);
		assertEquals(vs.getProductosVehiculosEnVenta().size(),0);
		
	}
	
	@Test
	public void testGetUsuario() {
		when(usuarioDao.getUsuario("a")).thenReturn(u);
		when(usuarioDao.getUsuario("b")).thenReturn(null);
		Usuario u1=vs.getUsuario("a");
		Usuario u2=vs.getUsuario("b");
		assertEquals(u1, u);
		assertEquals(u2, null);
	}
	
	@Test
	public void testPMF() {
		vs.setPmf(pmf);
		assertEquals(pmf, vs.getPmf());
	}
	
	@Test
	public void testUsuarioDao() {
		vs.setUsuarioDao(usuarioDao);
		assertEquals(usuarioDao, vs.getUsuarioDao());
	}
	
	@Test
	public void testProductoDao() {
		vs.setProductDao(productDao);
		assertEquals(productDao, vs.getProductDao());
	}
	
	@Test
	public void testCategoriaDao() {
		vs.setCategoriaDao(categoriaDao);
		assertEquals(categoriaDao, vs.getCategoriaDao());
	}
	
	
	
	
	
	
	
}
