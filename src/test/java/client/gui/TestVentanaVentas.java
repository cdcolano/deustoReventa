package client.gui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import categories.GuiTest;
import client.controller.VentasController;
import serialization.Categoria;
import serialization.Producto;
import util.ReventaException;

@RunWith(MockitoJUnitRunner.class)
@Category(GuiTest.class)
public class TestVentanaVentas {
	@Mock
	Client cliente;
	@Mock
	WebTarget webtarget;
	@Mock
	VentasController vc;
	
	
	
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
			when(vc.getListaProductosVendidos("a")).thenReturn(listaPro);
			VentanaVentas vs = new VentanaVentas(vc,cliente, webtarget,"a");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			assertTrue(false);
		}
	}
	
	/*@Test
	public void test() {
		try {
			VentasController vc = new VentasController(webtarget, "U@gmail.com");
		}catch(Exception e) {
			assertTrue(false);
		}
	}*/

}
