package serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ProductoTest {
	private Producto p;
	private Categoria cat;
	private Usuario u;
	private Oferta o;
	
	@Before
	public void setUp() {
		cat=new Categoria();
		cat.setNombre("ordenador");
		cat.setId(0);
		u= new Usuario();
		u.setEmail("a");
		u.setPassword("a");
		p= new Producto();
		o= new Oferta();
		o.setId(1);
	}
	
	@Test
	public void testConstructor() {
		Producto p1= new Producto(200, "acer", cat);
		boolean respuesta=false;
		if (p1.getPrecioSalida()==200 && p1.getCategoria().getId()==0 && p1.getNombre().contentEquals("acer")) {
			respuesta=true;
		}
		assertTrue(respuesta);
	}
	@Test
	public void testID() {
		p.setId(1);
		assertEquals(p.getId(),1);
	}
	
	
	@Test
	public void testPrecio() {
		p.setPrecioSalida(100);
		assertEquals(p.getPrecioSalida(),100,0);
	}
	
	@Test
	public void testFecha() {
		long f=System.currentTimeMillis();
		p.setFechaPubli(f);
		assertEquals(f,p.getFechaPubli());
	}
	
	
	@Test
	public void testReservado() {
		p.setReservado(true);
		assertTrue(p.isReservado());
	}
	
	@Test
	public void testVendido() {
		p.setVendido(true);
		assertTrue(p.isVendido());
	}
	
	
	@Test
	public void testCategoria() {
		p.setCategoria(cat);
		assertEquals(p.getCategoria().getId(), 0);
	}
	
	@Test
	public void testVendedor() {
		p.setEmailVendedor(u.getEmail());
		assertTrue(p.getEmailVendedor().contentEquals(u.getEmail()));
	}
	
	@Test
	public void testNombre() {
		p.setNombre("acer");
		assertTrue(p.getNombre().contentEquals("acer"));
	}
	
	@Test
	public void testListaOfertas() {
		ArrayList<Oferta>ofertas=new ArrayList<>();
		ofertas.add(o);
		p.setOfertasRecibidas(ofertas);
		assertEquals(p.getOfertasRecibidas().size(),1);
	}
	
	
	
	
}
