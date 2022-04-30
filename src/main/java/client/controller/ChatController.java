package client.controller;

import java.util.List;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import serialization.Categoria;
import serialization.Mensaje;
import util.ReventaException;

public class ChatController {
	private WebTarget webTarget;
	
	
	public List<Mensaje> getMensajesEnviados(String email) throws ReventaException{
		WebTarget webTarget = this.webTarget.path("reventa/mensajesEnviados/"+email);
		List<Mensaje>lMensajesEnviados = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<Mensaje>>() {
	     } );
		
		return lMensajesEnviados;
	}
	public List<Mensaje> getMensajesRecibidos(String email) throws ReventaException{
		WebTarget webTarget = this.webTarget.path("reventa/mensajesRecibidos/"+email);
		List<Mensaje>lMensajesRecibidos = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<Mensaje>>() {
	     } );
		
		return lMensajesRecibidos;
	}
	
	
}
