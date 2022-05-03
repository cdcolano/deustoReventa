package client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOUserException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dao.ProductoDAO;
import dao.UsuarioDAO;
import serialization.Categoria;
import serialization.Compra;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import util.ReventaException;

@RunWith(MockitoJUnitRunner.class)
public class ComprasControllerTest {
	@Mock
	private WebTarget webTarget;
	@Mock
	private WebTarget webTarget2;
	@Mock
	private WebTarget webTarget3;
	
	Usuario u1;
	Usuario u2;
	Producto p1,p2;
	Categoria c1;
	ComprasController cc;
	ComprasController cc1;
	Compra c;
	
	@Mock
	private Invocation.Builder inv;
	@Mock
	private Invocation.Builder inv2;
	@Mock
	private Response response;
	@Mock
	private Response response2;
	@Mock
	Client cliente;
	
	JPanel panel;
	
	@Before
	public void setUp() {
		u1 = new Usuario();
		
		u1.setEmail("j");
		u1.setPassword("j");
		
		panel = new JPanel();
		
		u2 = new Usuario();
		u2.setEmail("a");
		u2.setPassword("a");
		
		c1 = new Categoria();
		c1.setNombre("Cat1");
		p1 = new Producto();
		p1.setCategoria(c1);
		p1.setNombre("producto");
		p1.setId(1);
		p2 = new Producto();
		p2.setCategoria(c1);
		p2.setNombre("producto1");
		p2.setId(2);
		
		c= new Compra();
		//c.setProducto(p1);
		c.setPrecio(1.0);
		
		cc = new ComprasController(webTarget,"j");
		cc1 = new ComprasController(webTarget2,"j");
		
	}
	
	
	
	@Test
	public void testComprar() {
		when(webTarget.path("reventa/comprar/j/1")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		
		try {
			cc.comprar("j", 1, 1.0);
		}catch(Exception ex) {
		}
	}
	
	
	@Test
	public void testGetProductos() {
		when(webTarget.path("reventa/productosOrdenador")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(webTarget.path("reventa/productosVehiculo")).thenReturn(webTarget3);
		when(webTarget3.request(MediaType.APPLICATION_JSON)).thenReturn(inv2);
		
		List<ProductoOrdenador> lpo = new ArrayList<>();
		when(inv.get( new GenericType<List<ProductoOrdenador>>() {})).thenReturn(lpo);
		List<ProductoVehiculo> lpv = new ArrayList<>(); 
		when(inv2.get( new GenericType<List<ProductoVehiculo>>() {})).thenReturn(lpv);
		
		List<Producto> listaFinal = new ArrayList<>();
		
		listaFinal.addAll(lpo);
		listaFinal.addAll(lpv);
		try {
			assertEquals(listaFinal,cc.getProductos());
		} catch (ReventaException e) {
		}
	}
	
	@Test
	public void testGetProductosFavoritos() {
		when(webTarget.path("reventa/productosOrdenador/favoritos/j")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(webTarget.path("reventa/productosVehiculo/favoritos/j")).thenReturn(webTarget3);
		when(webTarget3.request(MediaType.APPLICATION_JSON)).thenReturn(inv2);
		
		List<ProductoOrdenador> lpo = new ArrayList<>();
		when(inv.get( new GenericType<List<ProductoOrdenador>>() {})).thenReturn(lpo);
		List<ProductoVehiculo> lpv = new ArrayList<>(); 
		when(inv2.get( new GenericType<List<ProductoVehiculo>>() {})).thenReturn(lpv);
		
		List<Producto> listaFinal = new ArrayList<>();
		
		listaFinal.addAll(lpo);
		listaFinal.addAll(lpv);
		try {
			assertEquals(listaFinal,cc.getProductosFavoritos());
		} catch (ReventaException e) {
		}
	}
	
	@Test
	public void testAnadirFav() {
		when(webTarget.path("reventa/anadirFav/j")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.post(Entity.entity(p1.getId(), MediaType.APPLICATION_JSON))).thenReturn(response);
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		
		try {
			cc.anadirFav(p1, "j");
		}catch(ReventaException e){
			e.printStackTrace();
			assertTrue(false);
		}
		when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
		try {
			cc.anadirFav(p1, "j");
		}catch(Exception ex) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void testAnadirUsuarioFav() {
		when(webTarget.path("reventa/anadirUsuarioFav/j")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.post(Entity.entity("a", MediaType.APPLICATION_JSON))).thenReturn(response);
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		
		try {
			cc.anadirUsuarioFav("a", "j");
		}catch(Exception e) {
			assertTrue(false);
		}
		when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
		try {
			cc.anadirUsuarioFav("a", "j");
		}catch(Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void testAnadirUsuarioFav1() {
		when(webTarget.path("reventa/anadirUsuarioFav/j")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.post(Entity.entity(u2, MediaType.APPLICATION_JSON))).thenReturn(response);
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		
		try {
			cc.anadirUsuarioFav(u2, "j");
		}catch(Exception ex) {
			assertTrue(false);
		}
		when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
		try {
			cc.anadirUsuarioFav(u2, "j");
		}catch(Exception e) {
			assertTrue(true);
		}
		
	}
	
	@Test
	public void testGetProducto() {
		when(webTarget.path("collector/getProducto/1")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.get()).thenReturn(response);
		when(response.readEntity(Producto.class)).thenReturn(p1);
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		
		try {
			assertEquals(cc.getProducto(1),p1);
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
		}
		when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
		try {
			cc.getProducto(1);
			assertTrue(false);
		}catch(Exception ex) {
			assertTrue(true);
		}
	}
	@Test
	public void testGetProductosEnVenta() {
		when(webTarget.path("reventa/productosOrdenador/venta")).thenReturn(webTarget2);
		when(webTarget.path("reventa/productosVehiculo/venta")).thenReturn(webTarget3);
		List<ProductoOrdenador> lpo = new ArrayList<>();
		when(webTarget2.request(MediaType.APPLICATION_JSON )).thenReturn(inv);
		when(inv.get(new GenericType<List<ProductoOrdenador>>() {})).thenReturn(lpo);
		List<ProductoVehiculo> lpv = new ArrayList<>(); 
		when(webTarget3.request(MediaType.APPLICATION_JSON )).thenReturn(inv);
		when(inv.get(new GenericType<List<ProductoVehiculo>>() {})).thenReturn(lpv);

		List<Producto> lp = new ArrayList<>();
		lp.addAll(lpo);
		lp.addAll(lpv);

		try {
			List<Producto> listaFinal = cc.getProductosEnVenta();
			assertEquals(lp.size(), listaFinal.size());
		} catch (ReventaException e) {
			fail();
		}


	}
	
	@Test
	public void testGetVentas() {
		when(webTarget.path("reventa/numVentas/j")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.get()).thenReturn(response);
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		int u = 0;
		when(response.readEntity(Integer.class)).thenReturn(u);
		
		try {
			assertEquals(cc.getVentas("j"),0);
		}catch(Exception ex) {
			assertTrue(false);
		}
		when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
		try {
			assertEquals(cc.getVentas("j"),0);
			assertTrue(false);
		}catch(Exception ex) {
			assertTrue(true);
		}
	}
	

	@Test
	public void testCrearPanel() {
		JPanel pn1 = new JPanel();
		
		try {
			cc.crearPanel(p1, pn1);
		}catch(Exception e) {
			fail();
		}
		assertEquals(pn1.getComponents().length,1);
	}
	@Test
	public void testOrdenarPorVentas() {
		when(webTarget.path("reventa/numVentas/j")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.get()).thenReturn(response);
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		when(webTarget.path("reventa/numVentas/b")).thenReturn(webTarget3);
		when(webTarget3.request(MediaType.APPLICATION_JSON)).thenReturn(inv2);
		when(inv2.get()).thenReturn(response2);
		when(response2.getStatus()).thenReturn(Status.OK.getStatusCode());
		when(response.readEntity(Integer.class)).thenReturn(1);
		when(response2.readEntity(Integer.class)).thenReturn(2);
		
		List<Producto> lp = new ArrayList<>();
		lp.add(p1);
		p1.setEmailVendedor("j");
		lp.add(p2);
		p2.setEmailVendedor("b");
		cc.setProductos(lp);
		cc.ordenarPorVentas(panel);
		assertEquals(lp.get(0), p2);
	}
	@Test
	public void  testOrdenarPorFechaAsc() {
		List<Producto> lp = new ArrayList<>();
		p1.setEmailVendedor("j");
		p1.setFechaPubli(0);
		lp.add(p1);
		p2.setEmailVendedor("b");
		p2.setFechaPubli(1);
		lp.add(p2);
		cc.setProductos(lp);
		cc.ordenarPorFechaAsc(panel);
		assertEquals(lp.get(0),p1);
	}
	@Test
	public void testOrdenarPorFechaDesc() {
		List<Producto> lp = new ArrayList<>();
		p1.setEmailVendedor("j");
		p1.setFechaPubli(0);
		lp.add(p1);
		p2.setEmailVendedor("b");
		p2.setFechaPubli(1);
		lp.add(p2);
		cc.setProductos(lp);
		cc.ordenarPorFechaDesc(panel);
		assertEquals(lp.get(0),p2);
	}
	@Test
	public void testMostrarFavoritos() {
		when(webTarget.path("reventa/productosOrdenador/favoritos/j")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(webTarget.path("reventa/productosVehiculo/favoritos/j")).thenReturn(webTarget3);
		when(webTarget3.request(MediaType.APPLICATION_JSON)).thenReturn(inv2);
		
		List<ProductoOrdenador> lpo = new ArrayList<>();
		when(inv.get( new GenericType<List<ProductoOrdenador>>() {})).thenReturn(lpo);
		List<ProductoVehiculo> lpv = new ArrayList<>(); 
		when(inv2.get( new GenericType<List<ProductoVehiculo>>() {})).thenReturn(lpv);
		
		List<Producto> listaFinal = new ArrayList<>();
		
		ProductoOrdenador p3 = new ProductoOrdenador();
		p3.setNombre("a");
		p3.setPrecioSalida(100);
		p3.setId(0);
		p3.setEmailVendedor("j");
		ProductoVehiculo p4 = new ProductoVehiculo();
		p4.setNombre("b");
		p4.setPrecioSalida(90);
		p4.setId(1);
		p4.setEmailVendedor("j");
		lpo.add(p3);
		lpv.add(p4);
		
		listaFinal.addAll(lpo);
		listaFinal.addAll(lpv);
		
		cc.mostrarFavoritos(panel, "j");
		
		assertEquals(listaFinal.size(),panel.getComponents().length);
		
	}
	/*@Test
	public void testSetProductos() {
		List<Producto> a = new ArrayList<>();
		a.add(p1);
		List<Producto> b = new ArrayList<>();
		assertEquals(cc.setProductos(a),a);
	}*/
}
