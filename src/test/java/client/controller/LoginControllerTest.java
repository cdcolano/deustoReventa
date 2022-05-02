package client.controller;

import serialization.Usuario;

import util.ReventaException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;

import javax.jdo.JDOUserException;
import javax.swing.JLabel;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.client.Client;

import client.gui.VentanaLogin;
import dao.IUsuarioDAO;
import dao.UsuarioDAO;


@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
	
	Usuario us;
	LoginController lc;
	@Mock
	private WebTarget webTarget;
	@Mock
	private WebTarget webTarget2;
	@Mock
	VentanaLogin vl;
	
	
	JLabel jl;
	@Mock
	UsuarioDAO	usuarioDao;
	@Mock
	private Invocation.Builder inv;
	@Mock
	private Response response;
	@Mock
	Client cliente;
	
	@Before
	public void setUp() {
		us = new Usuario();
		us.setEmail("j");
		us.setPassword("a");
		lc = new LoginController(cliente,webTarget);
		jl = new JLabel();
		
	}
	
	@Test
	public void testLogIn() throws ReventaException{
		when(webTarget.path("/reventa/logIn")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.post(Entity.entity(us, MediaType.APPLICATION_JSON))).thenReturn(response);
		
		
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		
		//when(inv.get()).thenReturn(response);
		
		when(usuarioDao.getUsuario("j")).thenReturn(us);
		when(usuarioDao.getUsuario("b")).thenThrow(JDOUserException.class);
		
		JLabel label = new JLabel();
		VentanaLogin v = new VentanaLogin(cliente, webTarget);
		
		try {
			lc.logIn("j", "a", label, v);
		}catch(Exception ex) {
			
		}
		
		when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
		try {
			lc.logIn("j", "a", label, v);
		}catch(Exception ex) {
			
		}
		
		when(response.readEntity(boolean.class)).thenReturn(true);
		assertTrue(lc.logIn("j", "a", label, v));
		
		
		when(response.readEntity(boolean.class)).thenReturn(false);
		assertFalse(lc.logIn("j", "a", label, v));
		
		
		/*boolean login = lc.logIn("j", "a", label, v);
		boolean login1 = lc.logIn("j", "j", label, v);
		boolean login2 = lc.logIn("c", "c", label, v);
		
		
		assertTrue(login);
		assertFalse(login1);
		assertFalse(login2);
		v.dispose();*/
	}
	
	@Test
	public void testGetUsuario() {
		when(webTarget.path("collector/getUsuario/j")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		when(inv.get()).thenReturn(response);
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		when(response.readEntity(Usuario.class)).thenReturn(us);
		try {
			assertEquals(lc.getUsuario("j"),us);
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			assertTrue(false);
		}
		when(usuarioDao.getUsuario("b")).thenThrow(JDOUserException.class);
		when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
		try {
			lc.getUsuario("j");
			assertTrue(true);
		}catch(Exception ex) {
			assertTrue(true);
		}
		
	}
}
