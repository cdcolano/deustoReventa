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
import serialization.Mensaje;
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
	public void testEnviarMensajes(){
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Usuario.class, "a")).thenReturn(u);
		when(pm.getObjectById(Usuario.class, "c")).thenReturn(null);
		when(pm.getObjectById(Usuario.class, "c")).thenThrow(JDOUserException.class);
		Usuario u2= new Usuario();
		u2.setEmail("b");
		when(pm.getObjectById(Usuario.class, "b")).thenReturn(u2);
		int mensajesEnviadosA=u.getMensajesEnviados().size();
		int mensajesRecibidosB=u2.getMensajesRecibidos().size();
		vs.enviarMensaje(u.getEmail(),u2.getEmail(),"hola",1);
		assertEquals(mensajesEnviadosA+1, u.getMensajesEnviados().size());
		assertEquals(mensajesRecibidosB+1, u2.getMensajesRecibidos().size());
		try {
			vs.enviarMensaje("c",u2.getEmail(),"hola",1);
			vs.enviarMensaje(u.getEmail(),"c","hola",1);
			assertTrue(true);
		}catch(Exception e) {
			assertTrue(false);
		}
		
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
	public void testNumVentas() {
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Usuario.class, "a")).thenReturn(u);
		when(pm.getObjectById(Usuario.class, "c")).thenReturn(null);
		when(pm.getObjectById(Usuario.class, "c")).thenThrow(JDOUserException.class);
		
		p.setVendido(true);
		Producto p2= new Producto();
		p2.setVendido(false);
		u.getProductos().add(p2);
		u.getProductos().add(p);
		int n=vs.getNumVentas("a");
		int c=vs.getNumVentas("c");
		assertEquals(c, 0);
		assertEquals(n, 1);
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
	
	public List<ProductoVehiculo> getProductosVehiculoFavoritos(String x) {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoVehiculo>po= new ArrayList<ProductoVehiculo>();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			for(Producto p:u.getProductosFavoritos()) {
				if (p instanceof ProductoVehiculo) {
					ProductoVehiculo prod=(ProductoVehiculo)p;
					po.add(prod);
				}
			}
		}catch(Exception e){
				System.out.println("Error al realizar la compra no existe ese usuario");
		}finally {
			pm.close();
		}
		return po;
	}
	
	@Test
	public void testGetProductosOrdenadorFavoritos() {
		ProductoOrdenador p= new ProductoOrdenador();
		p.setId(2);
		u.getProductosFavoritos().add(p);
		u.getProductosFavoritos().add(this.p);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Usuario.class,"a")).thenReturn(u);
		when(pm.getObjectById(Usuario.class,"c")).thenReturn(null);
		when(pm.getObjectById(Usuario.class,"c")).thenThrow(JDOUserException.class);
		List<ProductoOrdenador>prods=vs.getProductosOrdenadorFavoritos("a");
		assertEquals(prods.size(),1);
		List<ProductoOrdenador>prods2=vs.getProductosOrdenadorFavoritos("c");
	}
	
	@Test
	public void testGetProductosVehiculoFavoritos() {
		ProductoVehiculo p= new ProductoVehiculo();
		p.setId(2);
		u.getProductosFavoritos().add(p);
		u.getProductosFavoritos().add(this.p);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Usuario.class,"a")).thenReturn(u);
		when(pm.getObjectById(Usuario.class,"c")).thenReturn(null);
		when(pm.getObjectById(Usuario.class,"c")).thenThrow(JDOUserException.class);
		List<ProductoVehiculo>prods=vs.getProductosVehiculoFavoritos("a");
		assertEquals(prods.size(),1);
		List<ProductoVehiculo>prods2=vs.getProductosVehiculoFavoritos("c");
	}
	
	@Test
	public void testAddProductoFav() {
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Usuario.class, "a")).thenReturn(u);
		when(pm.getObjectById(Usuario.class, "b")).thenThrow(JDOUserException.class);
		when(pm.getObjectById(Producto.class, 1)).thenReturn(p);
		when(pm.getObjectById(Producto.class, 2)).thenThrow(JDOUserException.class);
		int sizeU=u.getProductosFavoritos().size();
		vs.addProductoFav( 1, "a");
		assertEquals(sizeU+1, u.getProductosFavoritos().size());
		try{
		vs.addProductoFav(1,"b");
		vs.addProductoFav(2,"a");
		}catch(Exception ex) {
			assertTrue(false);
		}
	}
	
	public List<Mensaje> getMensajesEnviados(String x) {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<Mensaje>men= new ArrayList<Mensaje>();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			men= u.getMensajesEnviados();
		}catch(Exception e){
				System.out.println("Error al realizar la compra no existe ese usuario");
		}finally {
			pm.close();
		}
		return men;
	}
	
	@Test
	public void testGetMensajesEnviados() {
		Mensaje m= new Mensaje();
		m.setId(1);
		u.getMensajesEnviados().add(m);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Usuario.class,"a")).thenReturn(u);
		when(pm.getObjectById(Usuario.class,"c")).thenReturn(null);
		when(pm.getObjectById(Usuario.class,"c")).thenThrow(JDOUserException.class);
		List<Mensaje>men=vs.getMensajesEnviados("a");
		assertEquals(men.size(),1);
		try {
			List<Mensaje>men2=vs.getMensajesEnviados("c");
		}catch(Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testGetMensajesRecibidos() {
		Mensaje m= new Mensaje();
		m.setId(1);
		u.getMensajesRecibidos().add(m);
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Usuario.class,"a")).thenReturn(u);
		when(pm.getObjectById(Usuario.class,"c")).thenReturn(null);
		when(pm.getObjectById(Usuario.class,"c")).thenThrow(JDOUserException.class);
		List<Mensaje>men=vs.getMensajesRecibidos("a");
		assertEquals(men.size(),1);
		try {
			List<Mensaje>men2=vs.getMensajesRecibidos("c");
		}catch(Exception e) {
			assertTrue(false);
		}
	}
	
	

	
	@Test
	public void testAddUsuarioFav() {
		when(pmf.getPersistenceManager()).thenReturn(pm);
		when(pm.getObjectById(Usuario.class, "a")).thenReturn(u);
		Usuario u2= new Usuario();
		u2.setEmail("b");
		when(pm.getObjectById(Usuario.class, "b")).thenReturn(u2);
		when(pm.getObjectById(Usuario.class, "c")).thenReturn(null);
		when(pm.getObjectById(Usuario.class, "c")).thenThrow(JDOUserException.class);
		
		int sizeU=u.getVendedoresLike().size();
		vs.addUsuarioFav("b","a");
		assertEquals(sizeU+1, u.getVendedoresLike().size());
		try {
			vs.addUsuarioFav("c", "a");
			vs.addUsuarioFav("a","c");
		}catch(Exception ex) {
			assertTrue(false);
		}
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
