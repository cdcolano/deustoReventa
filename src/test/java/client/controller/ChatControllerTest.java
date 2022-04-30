package client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mock;

import dao.IUsuarioDAO;
import serialization.Mensaje;
import serialization.Usuario;

public class ChatControllerTest {
	@Mock
	Usuario us1;
	@Mock
	Usuario us2;
	
	@Test
	public void testGetUsuario() {
		
	}
	@Test
	public void testGetConversacion() {
		us1 = new Usuario();
		us1.setEmail("a");
		us1.setPassword("a");
		
		us2 = new Usuario();
		us2.setEmail("b");
		us2.setPassword("b");
		
		Mensaje m1 = new Mensaje();
		m1.setId(1);
		m1.setContenido("Prueba del mensaje 1");
		
		Mensaje m2 = new Mensaje();
		m2.setId(2);
		m2.setContenido("Prueba del mensaje 2");
		
		us1.getMensajesEnviados().add(m1);
		us2.getMensajesRecibidos().add(m1);
		us1.getMensajesRecibidos().add(m2);
		us2.getMensajesEnviados().add(m2);
		
		assertTrue(us1.getMensajesEnviados().get(0).getId()==us2.getMensajesRecibidos().get(0).getId());
		assertTrue(us2.getMensajesEnviados().get(0).getId()==us1.getMensajesRecibidos().get(0).getId());
		
		ArrayList<Mensaje> lm = new ArrayList<>();
		
		
	}
}

