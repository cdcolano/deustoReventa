package client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dao.UsuarioDAO;
import serialization.Mensaje;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import util.ReventaException;

@RunWith(MockitoJUnitRunner.class)
public class VentasControllerTest {
	Usuario us1;
	
	Usuario us2;
	@Mock
	private WebTarget webTarget1;
	@Mock
	private WebTarget webTarget2;
	@Mock
	private Invocation.Builder builder;
	
	@Mock
	UsuarioDAO uDao;
	
	ChatController cc;

	private VentasController vs;
	@Before
	public void setUp() {
		cc = new ChatController(webTarget1, "a",uDao);
		us1 = new Usuario();
		us2 = new Usuario();
		
		us1.setEmail("a");
		us1.setPassword("a");
		
		us2.setEmail("b");
		us2.setPassword("b");
		vs= new VentasController(webTarget1, "a");
		
	}
	
	

	@Test
	public void testProductosVendidos() {
		when(webTarget1.path("reventa/ventas/productoOrdenador/a")).thenReturn(webTarget2);
		when(webTarget1.path("reventa/ventas/productoVehiculo/a")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
		ArrayList<ProductoOrdenador>po= new ArrayList<>();
		po.add(new ProductoOrdenador());
		when(builder.get(new GenericType <List<ProductoOrdenador>>() {})).thenReturn(po);
		ArrayList<ProductoVehiculo>pv= new ArrayList<>();
		pv.add(new ProductoVehiculo());
		when(builder.get(new GenericType <List<ProductoVehiculo>>() {})).thenReturn(pv);
		
		List<Producto> productos=new ArrayList<Producto>();
		try {
			productos = vs.getListaProductosVendidos("a");
		} catch (ReventaException e) {
			fail();
		}
		assertEquals(productos.size(), 2);
	}
}
