package client.controller;


import javax.ws.rs.core.Response.Status;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import serialization.Usuario;
import util.ReventaException;

/**
 * lase que implementa la logica de la ventana de registro
 * @author usuario
 *
 */
public class RegistroController {
	private WebTarget webTarget;
	
	public RegistroController(WebTarget webTarget) {
		this.webTarget=webTarget;
	}
	
	/**
	 * registrar a un usuario
	 * @param u
	 * @throws ReventaException
	 */
	public void registrar(Usuario u) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/registro");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(u, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
}
