package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import serialization.Categoria;
import serialization.Compra;
import serialization.Producto;

public class ServiceTest {
	@Mock
	Producto producto;
	Compra c;
	
	@Before
	public void setUp() {
		c = new Compra();
		Categoria cat= new Categoria();
		cat.setId(0);
		cat.setNombre("ordenador");
		producto= new Producto(100, "acer",cat);
	}
	
	@Test
	public void testID() {
		c.setId(1);
		assertEquals(c.getId(),1);
	}
	
	
	@Test
	public void testOpinion() {
		c.setOpinion("Prueba");
		assertTrue(c.getOpinion().contentEquals("Prueba"));
	}
	
	@Test
	public void testPrecio() {
		c.setPrecio(100);
		assertEquals(c.getPrecio(),100,2);
	}
	
	@Test
	public void testProducto() {
		c.setProducto(producto);
		assertTrue(c.getProducto().getNombre().contentEquals("acer"));
	}
	
}
