package client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import serialization.Categoria;
import serialization.Compra;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import util.ReventaException;

@RunWith(MockitoJUnitRunner.class)
public class ProductoControllerTest {
	
	@Mock
	private WebTarget webTarget;
	@Mock
	private WebTarget webTarget2;
		
	Usuario u1;
	Usuario u2;
	Producto p1;
	Categoria c1;
	ProductoController pc;
	Compra c;
	
	@Mock
	private Invocation.Builder inv;
	@Mock
	private Response response;
	
	
	
	
	@Before
	public void setUp() {
		u1 = new Usuario();
		
		u1.setEmail("j");
		u1.setPassword("j");
				
		u2 = new Usuario();
		u2.setEmail("a");
		u2.setPassword("a");
		
		c1 = new Categoria();
		c1.setNombre("Cat1");
		p1 = new Producto();
		p1.setCategoria(c1);
		p1.setNombre("producto");
		p1.setId(1);
		
		p1.setEmailVendedor("j");
		c= new Compra();
		//c.setProducto(p1);
		c.setPrecio(1.0);
		
		pc = new ProductoController(webTarget,"j");
	}
	
	@Test
	public void testGetCategoria() throws ReventaException {
		when(webTarget.path("reventa/categorias")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		List<Categoria> lCategorias = new ArrayList<>();
		
		when(inv.get(new GenericType<List<Categoria>>() {} )).thenReturn(lCategorias);
		//assertEquals(lCategorias,pc.getCategoria());
		try{
			assertEquals(lCategorias,pc.getCategoria());
		}catch(Exception e) {
			assertTrue(false);
		}
	}
	
	
	@Test
	public void testAddProductoOrdenador() {
		when(webTarget.path("reventa/saleOrdenador")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.post(Entity.entity(p1, MediaType.APPLICATION_JSON))).thenReturn(response);
		try {
			pc.addProductoOrdenador(p1);
			assertTrue(true);
		}catch(Exception ex) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testAddProductoVehiculo() {
		when(webTarget.path("reventa/saleVehiculo")).thenReturn(webTarget2);
		//when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		
		//ProductoVehiculo pv = new ProductoVehiculo();
		//when(inv.post(Entity.entity(pv, MediaType.APPLICATION_JSON)));
		
		try {
			pc.addProductoVehiculo(p1);
			assertTrue(true);
		}catch(Exception ex) {
			assertTrue(false);
		}
	}
	
	@Test
	public void testRegistrar() {
		when(webTarget.path("reventa/registro")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.post(Entity.entity(u1, MediaType.APPLICATION_JSON))).thenReturn(response);
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		
		try {
			pc.registrar(u1);
			when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
			pc.registrar(u1);
			fail();
		} catch (ReventaException e) {
			assertTrue(true);
		}
	}
}
