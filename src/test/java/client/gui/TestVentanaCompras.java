package client.gui;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import client.controller.ComprasController;
import serialization.Categoria;
import serialization.Producto;
import util.ReventaException;

@RunWith(MockitoJUnitRunner.class)
public class TestVentanaCompras {
	
	@Mock
	Client cliente;
	@Mock
	WebTarget webtarget;
	@Mock
	ComprasController cc;
	
	@Test
	public void test() {
		
		Producto p = new Producto();
		Categoria c = new Categoria();
		c.setId(1);
		p.setNombre("a");
		p.setPrecioSalida(10.0);
		p.setCategoria(c);
		List<Producto> listaPro = new ArrayList<>();
		listaPro.add(p);
		
		try {
			when(cc.getProductosEnVenta()).thenReturn(listaPro);
			VentanaCompras vs = new VentanaCompras(cc,cliente, webtarget, "j");
		} catch (Exception e1) {
			assertTrue(false);
		}
	}
	
	

}
