package client.controller;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import serialization.Mensaje;
import util.ReventaException;

public class ChatController {
	private WebTarget webTarget;
	
	public Mensaje getMensajeEnviado(String email)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/mensajesEnviados/"+email);
		System.out.println(webTarget.getUri());
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if(response.getStatus() == Status.OK.getStatusCode()) {
			Mensaje m = response.readEntity(Mensaje.class);
			return m;
		}else {
			throw new ReventaException("" + response.getStatus());
		}
		
	}
	
	public Mensaje getMensajeRecibido(String email)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/mensajesRecibidos/"+email);
		System.out.println(webTarget.getUri());
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if(response.getStatus() == Status.OK.getStatusCode()) {
			Mensaje m = response.readEntity(Mensaje.class);
			return m;
		}else {
			throw new ReventaException("" + response.getStatus());
		}
		
	}
}
