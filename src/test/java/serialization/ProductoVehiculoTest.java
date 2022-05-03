package serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ProductoVehiculoTest {
	private ProductoVehiculo p;
	
	@Before
	public void setUp() {
		p= new ProductoVehiculo();
	}
	
	@Test
	public void testAnyo() {
		p.setAnyoFabri(2008);
		assertEquals(p.getAnyoFabri(),2008);
	}
	
	@Test
	public void testCaballos() {
		p.setCaballos(95);
		assertEquals(p.getCaballos(),95);
	}
	
	@Test
	public void testKm() {
		p.setKilometros(95000);
		assertEquals(p.getKilometros(),95000);
	}
	
	
	@Test
	public void testMarca() {
		p.setMarca("Prueba");
		assertTrue(p.getMarca().contentEquals("Prueba"));
	}
	
	@Test
	public void testModelo() {
		p.setModelo("Prueba");
		assertTrue(p.getModelo().contentEquals("Prueba"));
	}
	
	
	
}
