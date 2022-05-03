package serialization;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.junit.Before;
import org.junit.Test;

import javassist.expr.NewArray;

public class UsuarioTest {
	private Tarjeta tarjeta;
	private int denuncias;
	private Oferta o;
	private Mensaje m;
	private Compra c;
	private Producto p;
	private Usuario u;
	
	
	@Before
	public void setUp() {
		tarjeta= new Tarjeta();
		tarjeta.setNombre("73M");
		m=new Mensaje();
		m.setId(1);
		p= new Producto();
		p.setId(1);
		c = new Compra();
		c.setId(1);
		o= new Oferta();
		o.setId(1);
		u= new Usuario();
	}
	
	@Test
	public void testContructor() {	
		boolean asserto=u.getProductosVendidos().size()==0 && u.getProductosEnVenta().size()==0 &&
				u.getMensajesEnviados().size()==0 && u.getProductosFavoritos().size()==0 &&
				u.getCompras().size()==0 && u.getVendedoresLike().size()==0 && u.getOfertasEnviadas().size()==0;
		assertTrue(asserto);
	}
	
	
	@Test
	public void testEmail() {
		u.setEmail("a");
		assertTrue(u.getEmail().contentEquals("a"));
	}
	
	@Test
	public void testPassword() {
		u.setPassword("a");
		assertTrue(u.getPassword().contentEquals("a"));
	}
	
	@Test
	public void testProductosEnVenta() {
		ArrayList<Producto>productos= new ArrayList<>();
		productos.add(p);
		u.setProductosEnVenta(productos);
		assertEquals(u.getProductosEnVenta().size(),1);
	}
	
	@Test
	public void testAddProductoEnVenta() {
		ArrayList<Producto>productos= new ArrayList<>();
		u.addProductoEnVenta(p);
		assertEquals(u.getProductosEnVenta().size(), 1);
	}
	

	@Test
	public void testProductosVendidos() {
		ArrayList<Producto>productos= new ArrayList<>();
		productos.add(p);
		u.setProductosVendidos(productos);
		assertEquals(u.getProductosVendidos().size(),1);
	}

	@Test
	public void MensajesRecibidos() {
		ArrayList<Mensaje>mensajes= new ArrayList<>();
		mensajes.add(m);
		u.setMensajesRecibidos(mensajes);
		assertEquals(u.getMensajesRecibidos().size(),1);
	}
	
	@Test
	public void MensajesEnviados() {
		ArrayList<Mensaje>mensajes= new ArrayList<>();
		mensajes.add(m);
		u.setMensajesEnviados(mensajes);
		assertEquals(u.getMensajesEnviados().size(),1);
	}
	
	@Test
	public void testProductosFavoritos() {
		ArrayList<Producto>productos= new ArrayList<>();
		productos.add(p);
		u.setProductosFavoritos(productos);
		assertEquals(u.getProductosFavoritos().size(),1);
	}
	

	@Test
	public void testDenuncias() {
		u.setDenuncias(10);
		assertEquals(u.getDenuncias(),10);
	}
	
	@Test
	public void testVendedoresLike() {
		ArrayList<Usuario>usuarios= new ArrayList<>();
		usuarios.add(u);
		u.setVendedoresLike(usuarios);;
		assertEquals(u.getVendedoresLike().size(),1);
	}
	
	@Test
	public void testOfertasEnviadas() {
		ArrayList<Oferta>ofertas= new ArrayList<>();
		ofertas.add(o);
		u.setOfertasEnviadas(ofertas);;
		assertEquals(u.getOfertasEnviadas().size(),1);
	}

	
	@Test
	public void testTarjeta() {
		u.setTarjeta(tarjeta);
		assertEquals(u.getTarjeta().getNombre(), "73M");
	}
	
	@Test
	public void testClone() {
		u.setTarjeta(tarjeta);
		u.setDenuncias(100);
		u.setEmail("a");
		ArrayList<Compra>compras= new ArrayList<>();
		compras.add(c);
		u.setCompras(compras);
		
		ArrayList<Mensaje>mensajes= new ArrayList<>();
		mensajes.add(m);
		u.setMensajesEnviados(mensajes);
		u.setMensajesRecibidos(mensajes);
		
		ArrayList<Producto>productos=new ArrayList<Producto>();
		productos.add(p);
		u.setProductos(productos);
		u.setProductosEnVenta(productos);
		u.setProductosFavoritos(productos);
		u.setProductosVendidos(productos);
		
		
		ArrayList<Oferta>ofertas= new ArrayList<>();
		ofertas.add(o);
		u.setOfertasEnviadas(ofertas);
		
		ArrayList<Usuario>usuarios= new ArrayList<>();
		usuarios.add(u);
		u.setVendedoresLike(usuarios);
			
		Usuario u2=u.clone();
		assertEquals(u2.getTarjeta().getNombre(), "73M");
		assertEquals(u2.getPassword(), u.getPassword());
		assertEquals(u2.getDenuncias(), u.getDenuncias());
		assertTrue(u2.getEmail().contentEquals(u.getEmail()));
		assertEquals(u2.getCompras().size(), u.getCompras().size());
		assertEquals(u2.getMensajesEnviados().size(), u.getMensajesEnviados().size());
		assertEquals(u2.getMensajesRecibidos().size(), u.getMensajesRecibidos().size());
		assertEquals(u2.getOfertasEnviadas().size(), u.getOfertasEnviadas().size());
		assertEquals(u2.getProductos().size(), u.getProductos().size());
		assertEquals(u2.getProductosEnVenta().size(), u.getProductosEnVenta().size());
		assertEquals(u2.getProductosVendidos().size(), u.getProductosVendidos().size());
		assertEquals(u2.getProductosFavoritos().size(), u.getProductosFavoritos().size());
		assertEquals(u2.getVendedoresLike().size(), u.getVendedoresLike().size());
	}

	@Test
	public void testCompras() {
		ArrayList<Compra>compras= new ArrayList<>();
		compras.add(c);
		u.setCompras(compras);
		assertEquals(u.getCompras().size(),1);
	}

	@Test
	public void getProductos() {
		ArrayList<Producto>productos= new ArrayList<>();
		productos.add(p);
		u.setProductos(productos);
		assertEquals(u.getProductos().size(),1);
	}
	
	
	
	
}
