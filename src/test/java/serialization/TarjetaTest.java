package serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TarjetaTest {
	private String nombre;
	private int codigoSecreto;
	private int mesVencimiento;
	private int anyoVencimiento;
	
	private Tarjeta t;
	
	@Before
	public void setUp() {
		t= new Tarjeta();
	}
	
	@Test
	public void testAnyo() {
		t.setAnyoVencimiento(2008);
		assertEquals(t.getAnyoVencimiento(),2008);
	}
	@Test
	public void testMes() {
		t.setMesVencimiento(5);
		assertEquals(t.getMesVencimiento(),5);
	}
	
	@Test
	public void testCodigo() {
		t.setCodigoSecreto(955);
		assertEquals(t.getCodigoSecreto(),955);
	}
	
	
	
	@Test
	public void testNombre() {
		t.setNombre("Prueba");
		assertTrue(t.getNombre().contentEquals("Prueba"));
	}
	
	
}
