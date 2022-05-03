package client.gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import client.controller.ProductoController;
import serialization.Categoria;
import serialization.Mensaje;
import serialization.Usuario;
import util.ReventaException;

@RunWith(MockitoJUnitRunner.class)
public class TestVentanaProducto {
	@Mock
	Client cliente;
	@Mock
	WebTarget webTarget;
	@Mock
	ProductoController  pc;
	@Mock
	private Invocation.Builder builder;
	
	@Before
	public void setUp() {
		
	}
	@Test
	public void test(){
		try {
			Categoria c= new Categoria();
			c.setId(0);
			c.setNombre("j");
			ArrayList<Categoria>cat= new ArrayList<>();
			cat.add(c);
			when(pc.getCategoria()).thenReturn(cat);
			VentanaProducto vp = new VentanaProducto(pc,cliente, webTarget, "u@gmail.com");
			vp.dispose();
			when(pc.getCategoria()).thenThrow(ReventaException.class);
			VentanaProducto vp2= new VentanaProducto(pc,cliente,webTarget,"j");
		}catch(ReventaException e) {
			assertTrue(false);
		}
	}
	/*@Test
	public void getCategoria() {
		when(webTarget.path("reventa/categorias")).thenReturn(webTarget);
		when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
		List<Categoria> cat = new ArrayList<>();
		when(builder.get(new GenericType<List<Categoria>>() {})).thenReturn(cat);
		List<Categoria> listaFinal = new ArrayList<>();
		Categoria c1 = new Categoria();
		cat.add(c1);
		try {
			vp.getCategoria();
			assertEquals(cat, listaFinal);
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			fail();
		}
	}*/
}
