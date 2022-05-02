package client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import dao.IUsuarioDAO;
import dao.UsuarioDAO;
import serialization.Mensaje;
import serialization.Usuario;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.ws.rs.client.WebTarget;

@RunWith(MockitoJUnitRunner.class)
public class ChatControllerTest {
	@Mock
	Usuario us1;
	@Mock
	Usuario us2;
	private WebTarget webTarget;	
	@Mock
	UsuarioDAO uDao;
	ChatController cc;
	@Before
	public void setUp() {
		cc = new ChatController(webTarget, "a",uDao);
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
		List<Mensaje> aM = new ArrayList<>();
		List<Mensaje> listaFinal = new ArrayList<>();
		Mensaje m1 = new Mensaje();
		Date fecha = new Date(0);
		long fechaLong = fecha.getTime();
		m1.setFecha(fechaLong);
		m1.setContenido("prueba 1");
		
		Mensaje m2 = new Mensaje();
		Date fecha2 = new Date();
		long fecha2Long = fecha2.getTime();
		m2.setFecha(fecha2Long);
		m2.setContenido("prueba 2");
		
		aM.add(m1);
		aM.add(m2);
		us1.setMensajesRecibidos(aM);
		listaFinal = cc.getMensajesRecibidos("a");
		assertTrue(listaFinal.get(0).getFecha()>listaFinal.get(1).getFecha());
		
		
	}
	@Test
	public void testGetMensajesEnviados() {
		List<Mensaje> aM = new ArrayList<>();
		List<Mensaje> listaFinal = new ArrayList<>();
		Mensaje m1 = new Mensaje();
		Date fecha = new Date(0);
		long fechaLong = fecha.getTime();
		m1.setFecha(fechaLong);
		m1.setContenido("prueba 1");
		
		Mensaje m2 = new Mensaje();
		Date fecha2 = new Date();
		long fecha2Long = fecha2.getTime();
		m2.setFecha(fecha2Long);
		m2.setContenido("prueba 2");
		
		aM.add(m1);
		aM.add(m2);
		us1.setMensajesEnviados(aM);
		listaFinal = cc.getMensajesEnviados("a");
		assertTrue(listaFinal.get(0).getFecha()>listaFinal.get(1).getFecha());
	}
}

