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
import util.ReventaException;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@RunWith(MockitoJUnitRunner.class)
public class ChatControllerTest {
	
	Usuario us1;
	
	Usuario us2;
	@Mock
	private WebTarget webTarget1;
	@Mock
	private WebTarget webTarget2;
	@Mock
	private Invocation.Builder builder;
	
	@Mock
	UsuarioDAO uDao;
	
	ChatController cc;
	@Before
	public void setUp() {
		cc = new ChatController(webTarget1, "a",uDao);
		us1 = new Usuario();
		us2 = new Usuario();
		
		us1.setEmail("a");
		us1.setPassword("a");
		
		us2.setEmail("b");
		us2.setPassword("b");
		
	}
	
	
	@Test
	public void testEnviar() {
	//no se puede hacer porque se crea un objeto dentro de el	
	}
	
	
	@Test
	public void testGetMensajesRecibidos() {
		when(webTarget1.path("reventa/mensajesRecibidos/a")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
		List<Mensaje> aM = new ArrayList<>();
		when(builder.get(new GenericType<List<Mensaje>>() {})).thenReturn(aM);
		List<Mensaje> listaFinal = new ArrayList<>();
		Mensaje m1 = new Mensaje();
		Date fecha = new Date(0);
		long fechaLong = 2;
		m1.setFecha(fechaLong);
		m1.setContenido("prueba 1");
		
		Mensaje m2 = new Mensaje();
		Date fecha2 = new Date();
		long fecha2Long = 1;
		m2.setFecha(fecha2Long);
		m2.setContenido("prueba 2");
		
		aM.add(m1);
		aM.add(m2);
		
		us1.setMensajesRecibidos(aM);
		//cc.getMensajesRecibidos(us1.getEmail());
		listaFinal = cc.getMensajesRecibidos("a");
		assertEquals(aM, listaFinal);
		
	}
	@Test
	public void testGetMensajesEnviados() {
		when(webTarget1.path("reventa/mensajesEnviados/a")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
		List<Mensaje> aM = new ArrayList<>();
		when(builder.get(new GenericType<List<Mensaje>>() {})).thenReturn(aM);
		List<Mensaje> listaFinal = new ArrayList<>();
		Mensaje m1 = new Mensaje();
		Date fecha = new Date(0);
		long fechaLong = 2;
		m1.setFecha(fechaLong);
		m1.setContenido("prueba 1");
		
		Mensaje m2 = new Mensaje();
		Date fecha2 = new Date();
		long fecha2Long = 1;
		m2.setFecha(fecha2Long);
		m2.setContenido("prueba 2");
		
		aM.add(m1);
		aM.add(m2);
		
		us1.setMensajesEnviados(aM);
		//cc.getMensajesRecibidos(us1.getEmail());
		listaFinal = cc.getMensajesEnviados("a");
		assertEquals(aM, listaFinal);
	}
	
	@Test
	public void testGetEmails() {
		ArrayList<Usuario>usuarios= new ArrayList<>();
		usuarios.add(us1);
		usuarios.add(us2);
		when(uDao.getUsuarios()).thenReturn(usuarios);
		List<String>listaEmails=cc.getEmailUsuarios();
		assertEquals(listaEmails.size(),usuarios.size());
	}
}

