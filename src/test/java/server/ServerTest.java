package server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import serialization.Categoria;
import serialization.Compra;
import serialization.Mensaje;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import service.VentasService;

@RunWith(MockitoJUnitRunner.class)
public class ServerTest {
	
	@Mock
	private VentasService vs;
	private ReventaServer rs;
	private Usuario u;
	private Producto p;
	
	@Before	
	public void setUp() {
		rs= new ReventaServer();
		rs.setVs(vs);
		
		u= new Usuario();
		u.setEmail("a");
		u.setPassword("a");
		
		p= new Producto();
		p.setId(1);
	}
	
	@Test
	public void testComprar() {
		Compra c= new Compra();
		Response r=rs.addCompra(c, u.getEmail(), p.getId());
		assertEquals(r.getStatus(), Status.OK.getStatusCode());
	}
	
	@Test
	public void testEnviar() {
		Usuario u2 = new Usuario();
		u2.setEmail("b");
		Mensaje m= new Mensaje();
		Response r=rs.addMensaje(m, u.getEmail(), u2.getEmail());
		assertEquals(r.getStatus(), Status.OK.getStatusCode());
	}
	
	@Test
	public void testGetMensajesRecibidos() {
		ArrayList<Mensaje>men= new ArrayList<>();
		men.add(new Mensaje());
		when(vs.getMensajesRecibidos(u.getEmail())).thenReturn(men);
		List<Mensaje>men2=rs.getMensajesRecibidos(u.getEmail());
		assertEquals(men, men2);
	}
	
	@Test
	public void testGetMensajesEnviados() {
		ArrayList<Mensaje>men= new ArrayList<>();
		men.add(new Mensaje());
		when(vs.getMensajesEnviados(u.getEmail())).thenReturn(men);
		List<Mensaje>men2=rs.getMensajesEnviados(u.getEmail());
		assertEquals(men, men2);
	}
	
	
	@Test
	public void testLogIn() {
		when(vs.logIn(u.getEmail(), u.getPassword())).thenReturn(true);
		Response r=rs.logIn(u);
		assertEquals(r.getStatus(), Status.OK.getStatusCode());
	}
	
	
	@Test
	public void testRegistro() {
		when(vs.registro(u)).thenReturn(true);
		Response r=rs.registro(u);
		assertEquals(r.getStatus(), Status.OK.getStatusCode());
	}
	
	
	@Test
	public void testAddProductoOrdenador() {
		ProductoOrdenador po= new ProductoOrdenador();
		po.setEmailVendedor(u.getEmail());
		Response r=rs.addProductoOrdenador(po);
		assertEquals(r.getStatus(), Status.OK.getStatusCode());
	}
	
	@Test
	public void testAddProductoVehiculo() {
		ProductoVehiculo po= new ProductoVehiculo();
		po.setEmailVendedor(u.getEmail());
		Response r=rs.addProductoVehiculo(po);
		assertEquals(r.getStatus(), Status.OK.getStatusCode());
	}
	
	@Test
	public void testGetProducto() {
		when(vs.getProducto(p.getId())).thenReturn(p);
		Response r=rs.getProducto(p.getId());
		assertEquals(r.getStatus(), Status.OK.getStatusCode());
	}
	
	
	@Test
	public void testGetProductosOrdenador() {
		ArrayList<ProductoOrdenador>men= new ArrayList<>();
		men.add(new ProductoOrdenador());
		when(vs.getProductosOrdenador()).thenReturn(men);
		List<ProductoOrdenador>men2=rs.getProductosOrdenador();
		assertEquals(men, men2);
	}
	
	@Test
	public void testGetProductosVehiculo() {
		ArrayList<ProductoVehiculo>men= new ArrayList<>();
		men.add(new ProductoVehiculo());
		when(vs.getProductosVehiculos()).thenReturn(men);
		List<ProductoVehiculo>men2=rs.getProductosVehiculo();
		assertEquals(men, men2);
	}

	@Test
	public void testGetProductosOrdenadorEnVenta() {
		ArrayList<ProductoOrdenador>men= new ArrayList<>();
		men.add(new ProductoOrdenador());
		when(vs.getProductosOrdenadorEnVenta()).thenReturn(men);
		List<ProductoOrdenador>men2=rs.getProductosOrdenadorEnVenta();
		assertEquals(men, men2);
	}
	
	@Test
	public void testGetProductosVehiculoEnVenta() {
		ArrayList<ProductoVehiculo>men= new ArrayList<>();
		men.add(new ProductoVehiculo());
		when(vs.getProductosVehiculosEnVenta()).thenReturn(men);
		List<ProductoVehiculo>men2=rs.getProductosVehiculoEnVenta();
		assertEquals(men, men2);
	}

	
	@Test
	public void testGetProductosOrdenadorFavoritos() {
		ArrayList<ProductoOrdenador>men= new ArrayList<>();
		men.add(new ProductoOrdenador());
		when(vs.getProductosOrdenadorFavoritos(u.getEmail())).thenReturn(men);
		List<ProductoOrdenador>men2=rs.getProductosOrdenadorFavoritos(u.getEmail());
		assertEquals(men, men2);
	}
	
	@Test
	public void testGetProductosVehiculoFavoritos() {
		ArrayList<ProductoVehiculo>men= new ArrayList<>();
		men.add(new ProductoVehiculo());
		when(vs.getProductosVehiculoFavoritos(u.getEmail())).thenReturn(men);
		List<ProductoVehiculo>men2=rs.getProductosVehiculoFavoritos(u.getEmail());
		assertEquals(men, men2);
	}
	
	@Test
	public void testNumVentas() {
		when(vs.getNumVentas(u.getEmail())).thenReturn(10);
		assertEquals(rs.getNumVentas(u.getEmail()), 10);
	}
	
	
	@Test
	public void testGetCategorias() {
		ArrayList<Categoria>men= new ArrayList<>();
		men.add(new Categoria());
		when(vs.getCategorias()).thenReturn(men);
		List<Categoria>men2=rs.getCategorias();
		assertEquals(men, men2);
	}
	
	@Test
	public void testGetUsuario() {
		when(vs.getUsuario(u.getEmail())).thenReturn(u);
		Response r=rs.getUsuario(u.getEmail());
		assertEquals(r.getStatus(), Status.OK.getStatusCode());
	}
	
	@Test
	public void testGetVentasOrdenador() {
		ArrayList<ProductoOrdenador>men= new ArrayList<>();
		men.add(new ProductoOrdenador());
		when(vs.getProductosOrdenadorVendidos(u.getEmail())).thenReturn(men);
		List<ProductoOrdenador>men2=rs.getVentasOrdenador(u.getEmail());
		assertEquals(men, men2);
	}
	
	@Test
	public void testGetVentasVehiculo() {
		ArrayList<ProductoVehiculo>men= new ArrayList<>();
		men.add(new ProductoVehiculo());
		when(vs.getProductosVehiculoVendidos(u.getEmail())).thenReturn(men);
		List<ProductoVehiculo>men2=rs.getVentasVehiculo(u.getEmail());
		assertEquals(men, men2);
	}
	
	@Test
	public void testAddProductoFav() {
		Response r=rs.addProductoFav(p.getId(), u.getEmail());
		assertEquals(r.getStatus(), Status.OK.getStatusCode());
	}
	
	@Test
	public void testAddUsuarioFav() {
		Response r=rs.addUsuarioFav(u.getEmail(), u.getEmail());
		assertEquals(r.getStatus(), Status.OK.getStatusCode());
	}
	
	
	
}
