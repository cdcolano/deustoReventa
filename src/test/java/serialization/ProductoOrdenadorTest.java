package serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ProductoOrdenadorTest {
	private ProductoOrdenador p;
	
	@Before
	public void setUp() {
		p= new ProductoOrdenador();
	}
	
	@Test
	public void testRam() {
		p.setRam(8);
		assertEquals(p.getRam(),8);
	}
	@Test
	public void testMemoria() {
		p.setMemoria(64);
		assertEquals(p.getMemoria(),64);
	}
	
	
	@Test
	public void testCPU() {
		p.setCpu("Prueba");
		assertTrue(p.getCpu().contentEquals("Prueba"));
	}
	
	@Test
	public void testGrafica() {
		p.setGrafica("Prueba");
		assertTrue(p.getGrafica().contentEquals("Prueba"));
	}
	
	@Test
	public void testContenidp() {
		p.setPlacaBase("Prueba");
		assertTrue(p.getPlacaBase().contentEquals("Prueba"));
	}
	
	
}
