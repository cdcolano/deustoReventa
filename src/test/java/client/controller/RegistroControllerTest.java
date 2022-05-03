package client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

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

import serialization.Usuario;
import util.ReventaException;

@RunWith(MockitoJUnitRunner.class)
public class RegistroControllerTest {
	Usuario u1;
	@Mock 
	private WebTarget webTarget;
	@Mock
	private WebTarget webTarget2;
	@Mock
	private Invocation.Builder builder;
	@Mock
	private Response response;
	
	RegistroController rc;
	
	@Before
	public void setUp() {
		rc = new RegistroController(webTarget);
		u1 = new Usuario();
		u1.setEmail("a");
		u1.setPassword("a");
		
	}
	
	public void registrar(Usuario u) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/registro");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(u, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	@Test
	public void testRegistrar() {
		when(webTarget.path("reventa/registro")).thenReturn(webTarget2);
		when(webTarget2.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
		when(builder.post(Entity.entity(u1, MediaType.APPLICATION_JSON))).thenReturn(response);
		when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
		try {
			rc.registrar(u1);
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
		try {
			rc.registrar(u1);
			assertTrue(true);
		}catch(Exception ex) {
			assertTrue(false);
		}*/
	}
}
