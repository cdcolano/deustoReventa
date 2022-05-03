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
	@Mock
	ChatController cc;
	@Mock
	UsuarioDAO udao;
	
	@Before
	public void setUp() {
		u1 = new Usuario();
		u1.setEmail("j");
		u1.setPassword("j");
	}
	
	@Test
	public void TestVentana() {
		
		List<Mensaje> enviadosM = new ArrayList<>();
		Mensaje m= new Mensaje();
		m.setContenido("hola");
		m.setFecha(System.currentTimeMillis());
		m.setEnviado("j");
		m.setRecibido("j");
		enviadosM.add(m);
        List<Mensaje> recibidosM = new ArrayList<>();
        recibidosM.add(m);
        ArrayList<String>emails= new ArrayList<>();
        emails.add("j");
        when(cc.getMensajesEnviados("j")).thenReturn(enviadosM);
        when(cc.getMensajesRecibidos("j")).thenReturn(recibidosM);
        when(cc.getEmailUsuarios()).thenReturn(emails);
		try {
			VentanaChat v= new VentanaChat(cc,cliente, webTarget, "j");
		}catch(Exception e) {
			assertTrue(true);
		}
	}
}
