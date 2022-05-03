package client.gui;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
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
			vs.dispose();
			when(cc.getProductosEnVenta()).thenThrow(ReventaException.class);
			VentanaCompras vs2 = new VentanaCompras(cc,cliente, webtarget, "j");
			JComboBox<String> jc= new JComboBox<String>();
			jc.addItem("Ordenar por Ventas del Vendedor");
			jc.addItem("Ordenar por Fecha de Publicacion ascendente");
			jc.addItem("Ordenar por Fecha de Publicacion descendente");
			jc.addItem("Favoritos");
			JPanel panel= new JPanel();
			for (int i=0;i<4;i++) {
				jc.setSelectedIndex(i);
				vs2.itemStateChangedVentana(jc, panel, cc, "j", vs2);
			}	
			vs2.dispose();
		} catch (Exception e1) {
			assertTrue(false);
		}
	}
	
	
	
	

}
