package client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dao.IUsuarioDAO;
import serialization.Mensaje;
import serialization.Usuario;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class ChatControllerTest {
	@Mock
	Usuario us1;
	@Mock
	Usuario us2;
	
	@Before
	public void setUp() {
		us1 = new Usuario();
		us2 = new Usuario();
		
		us1.setEmail("a");
		us1.setPassword("a");
		
		us2.setEmail("b");
		us2.setPassword("b");
		
	}
	
	@Test
	public void testGetUsuario() {
		
	}
	@Test
	public void testGetMensajesRecibidos() {
		ArrayList<Mensaje> aM = new ArrayList<>();
		Mensaje m1 = new Mensaje();
		m1.setContenido("Mensaje de prueba");
		Date fecha = new Date(0);
		long fechaLong = fecha.getTime();
		m1.setFecha(fechaLong);
		aM.add(m1);
		when(us1.getMensajesRecibidos()).thenReturn(aM);
		assertEquals(us1.getMensajesRecibidos().size(),1);
		
		
	}
	@Test
	public void testGetMensajesEnviados() {
		ArrayList<Mensaje> aM1 = new ArrayList<>();
		Mensaje m1 = new Mensaje();
		m1.setContenido("Mensaje de prueba");
		Date fecha = new Date(0);
		long fechaLong = fecha.getTime();
		m1.setFecha(fechaLong);
		aM1.add(m1);
		when(us1.getMensajesRecibidos()).thenReturn(aM1);
		assertEquals(us1.getMensajesEnviados().size(),1);
	}
}

