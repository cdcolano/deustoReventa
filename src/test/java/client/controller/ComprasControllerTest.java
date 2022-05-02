package client.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class ComprasControllerTest {
	@Mock
	private WebTarget webTarget;
	@Mock
	private WebTarget webTarget2;
	@Mock
	private WebTarget webTarget3;
	
	Usuario u1;
	Producto p1;
	Categoria c1;
	ComprasController cc;
	
	@Mock
	private Invocation.Builder inv;
	@Mock
	private Response response;
	@Mock
	Client cliente;
	
	@Before
	public void setUp() {
		u1 = new Usuario();
		u1.setEmail("j");
		u1.setPassword("a");
		
		c1 = new Categoria();
		c1.setNombre("Cat1");
		p1 = new Producto();
		p1.setCategoria(c1);
		p1.setNombre("producto");
		p1.setId(1);
		
		cc = new ComprasController(webTarget,"j");
		
	}
	
	@Test
	public void testComprar() {
		when(webTarget.path("reventa/comprar/a/1")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.post(Entity.entity(c1, MediaType.APPLICATION_JSON))).thenReturn(response);
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		
		
		try {
			cc.comprar("j", 1, 1.0);
		}catch(Exception ex) {
			assertTrue(false);
		}
		when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
		try {
			cc.comprar("j", 1, 1.0);
			assertFalse(true);
		}catch(Exception ex) {
			
		}
	}
	
	@Test
	public void testGetProductos() {
		when(webTarget.path("reventa/productosOrdenador")).thenReturn(webTarget2);
		when(webTarget.path("reventa/productosVehiculo")).thenReturn(webTarget3);
		List<ProductoOrdenador> lpo = new ArrayList<>();
		when(webTarget2.request(MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {})).thenReturn(lpo);
		List<ProductoVehiculo> lpv = new ArrayList<>(); 
		when(webTarget3.request(MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoVehiculo>>() {})).thenReturn(lpv);
		
	}
}
