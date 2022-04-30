package client;

import java.io.ObjectInputFilter.Status;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import serialization.Usuario;
import util.ReventaException;

public class RegistroController {
	private WebTarget webTarget;
	
	
	public void registrar(Usuario u) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/registro");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(u, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
}
