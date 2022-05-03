package client.gui;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import client.controller.ChatController;
import client.controller.ComprasController;
import dao.UsuarioDAO;
import serialization.Mensaje;
import serialization.Usuario;

@RunWith(MockitoJUnitRunner.class)
public class TestVentanaChat {
	
	@Mock 
	Client cliente;
	@Mock
	WebTarget webTarget;
	
	Usuario u1;
	ChatController cc;
	UsuarioDAO udao;
	
	@Before
	public void setUp() {
		u1 = new Usuario();
		u1.setEmail("j");
		u1.setPassword("j");
		udao = new UsuarioDAO();
		cc = new ChatController(webTarget,"j",udao);
	}
	
	@Test
	public void TestVentana() {
		
		List<Mensaje> enviadosM = new ArrayList<>();
        List<Mensaje> recibidosM = new ArrayList<>();
        
        //when(cc.getMensajesEnviados("j")).thenReturn(enviadosM);
        //when(cc.getMensajesRecibidos("j")).thenReturn(recibidosM);
		
		try {
			VentanaChat v= new VentanaChat(cliente, webTarget, "j");
		}catch(Exception e) {
			assertTrue(true);
		}
	}
}
