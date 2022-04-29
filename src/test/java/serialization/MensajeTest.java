package serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class MensajeTest {
	private Mensaje m;
	
	@Before
	public void setUp() {
		m= new Mensaje();
	}
	
	@Test
	public void testID() {
		m.setId(1);
		assertEquals(m.getId(),1);
	}
	
	
	@Test
	public void testContenido() {
		m.setContenido("Prueba");
		assertTrue(m.getContenido().contentEquals("Prueba"));
	}
	
	@Test
	public void testPrecio() {
		long f=System.currentTimeMillis();
		m.setFecha(f);
		assertEquals(f,m.getFecha());
	}
	
}
