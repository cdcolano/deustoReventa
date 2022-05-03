package client.gui;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;
import org.mockito.Mock;



public class TestVentanaLogin {
	@Mock
	Client cliente;
	@Mock
	WebTarget webTarget;

	
	//Client cliente, WebTarget webTarget
	@Test
	public void test(){
		try {
			VentanaLogin vl = new VentanaLogin(cliente, webTarget);
		}catch(Exception e) {
			assertTrue(false);
		}
	}

}
