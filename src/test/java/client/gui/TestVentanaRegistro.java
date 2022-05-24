package client.gui;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import categories.GuiTest;

@Category(GuiTest.class)
public class TestVentanaRegistro {
	@Mock
	Client cliente;
	@Mock
	WebTarget webTarget;
	@Mock
	VentanaRegistro vr;
	
	@Test
	public void test() {
		try {
			VentanaRegistro vr = new VentanaRegistro(cliente, webTarget);
		}catch(Exception e) {
			assertTrue(false);
		}
	}
	

}
