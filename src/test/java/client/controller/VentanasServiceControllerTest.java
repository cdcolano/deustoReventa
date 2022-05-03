package client.controller;

import static org.junit.Assert.assertEquals;
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
import org.mockito.Mock;

import dao.UsuarioDAO;
import serialization.Mensaje;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import util.ReventaException;

public class VentanasServiceControllerTest {
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
	@Before
	public void setUp() {
		cc = new ChatController(webTarget1, "a",uDao);
		us1 = new Usuario();
		us2 = new Usuario();
		
		us1.setEmail("a");
		us1.setPassword("a");
		
		us2.setEmail("b");
		us2.setPassword("b");
		
	}
	
	
public List<Producto>  getListaProductosVendidos(String email)throws ReventaException {
	WebTarget webTarget = this.webTarget.path("reventa/ventas/productoOrdenador/"+email);
	List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
     } );
	WebTarget webTarget2 = this.webTarget.path("reventa/ventas/productoVehiculo/"+email);
	List<ProductoVehiculo>lProductosVehiculo = webTarget2.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoVehiculo>>() {
    } );
	List<Producto>lProductos= new ArrayList<>();
	lProductos.addAll(lProductosOrdenador);
	lProductos.addAll(lProductosVehiculo);
	return lProductos;
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
		when(builder.get(new GenericType <List<ProductoVehiculo>>() {})).thenReturn(pv);
		List<Mensaje> aM = new ArrayList<>();
		when(builder.get(new GenericType<List<Mensaje>>() {})).thenReturn(aM);
		List<Mensaje> listaFinal = new ArrayList<>();
		Mensaje m1 = new Mensaje();
		Date fecha = new Date(0);
		long fechaLong = 2;
		m1.setFecha(fechaLong);
		m1.setContenido("prueba 1");
		
		Mensaje m2 = new Mensaje();
		Date fecha2 = new Date();
		long fecha2Long = 1;
		m2.setFecha(fecha2Long);
		m2.setContenido("prueba 2");
		
		aM.add(m1);
		aM.add(m2);
		
		us1.setMensajesEnviados(aM);
		//cc.getMensajesRecibidos(us1.getEmail());
		listaFinal = cc.getMensajesEnviados("a");
		assertEquals(aM, listaFinal);
	}
}
