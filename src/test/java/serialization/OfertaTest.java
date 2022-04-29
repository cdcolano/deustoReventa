package serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class OfertaTest {
	private Oferta o;
	
	@Before
	public void setUp() {
		o= new Oferta();
	}
	
	@Test
	public void testID() {
		o.setId(1);
		assertEquals(o.getId(),1);
	}
	
	
	@Test
	public void testCantidad() {
		o.setCantidad(10);
		assertEquals(o.getCantidad(),10,0);
	}
	
	@Test
	public void testFecha() {
		long f=System.currentTimeMillis();
		o.setFecha(f);
		assertEquals(f,o.getFecha());
	}
	
	@Test
	public void testEstado() {
		o.setEstado(true);
		assertTrue(o.isEstado());
	}
}
