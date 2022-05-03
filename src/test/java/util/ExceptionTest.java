package util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExceptionTest {
	
	@Test
	public void testReventa() {
		ReventaException e= new ReventaException("Hola");
		assertTrue(e.getMessage().contentEquals("Hola"));
	}
}
