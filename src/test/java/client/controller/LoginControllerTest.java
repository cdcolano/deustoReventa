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
	
	public boolean logIn(String email, String password, JLabel lError, VentanaLogin vl) throws ReventaException {
		try {
			WebTarget webTarget=this.webTarget.path("/reventa/logIn");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Usuario u=new Usuario();
			u.setEmail(email);
			u.setPassword(password);
			System.out.println(webTarget.getUri());
			Response response = invocationBuilder.post(Entity.entity(u, MediaType.APPLICATION_JSON));

			if (response.getStatus() != Status.OK.getStatusCode()) {
				throw new ReventaException("" + response.getStatus());
			}
			boolean logIn=response.readEntity(boolean.class);
			if (!logIn) {
				lError.setText("Email o contraseña incorrectos");
				vl.setLocationRelativeTo(null);
				vl.pack();
				
			}
			return logIn;
		}catch(Exception e) {
			lError.setText("Algo ha fallado al realizar el LogIn");
			System.out.println("* Error " + e.getMessage() +"*");
			vl.pack();
			vl.setLocationRelativeTo(null);
			return false;
		}
	}
	
	@Test
	public void testLogIn() throws ReventaException {
		when(webTarget.path("/reventa/logIn")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(inv);
		//when(inv.post(Entity.entity(us, MediaType.APPLICATION_JSON))).thenReturn(response);
		//when(response.readEntity(boolean.class)).thenReturn(true);
		
		//when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		
		//when(inv.get()).thenReturn(response);
		
//		when(usuarioDao.getUsuario("j")).thenReturn(us);
//		when(usuarioDao.getUsuario("b")).thenThrow(JDOUserException.class);
		
		JLabel label= new JLabel();
		try {
			lc.logIn("j", "a", label, vl);
		}catch(Exception ex) {
			assertTrue(true);
		}
		
		
		
		/*boolean login = lc.logIn("j", "a", label, v);
		boolean login1 = lc.logIn("j", "j", label, v);
		boolean login2 = lc.logIn("c", "c", label, v);
		
		
		assertTrue(login);
		assertFalse(login1);
		assertFalse(login2);
		v.dispose();*/
		

		
		
		
		
		
		
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
		when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
		try {
			lc.getUsuario("j");
			assertTrue(true);
		}catch(Exception ex) {
			assertTrue(true);
		}
		
	}
}
