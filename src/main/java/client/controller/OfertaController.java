package client.controller;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import serialization.Compra;
import serialization.Oferta;
import util.ReventaException;

public class OfertaController {
	private WebTarget webTarget;
	private String email;
	private int id;
	
	public OfertaController(WebTarget wt, String email, int id) {
		webTarget=wt;
		this.email=email;
		this.id=id;
	}

	public void addOferta(String email, int idProd, double oferta) throws ReventaException{
		WebTarget webTarget = this.webTarget.path("reventa/oferta/"+email +"/"+ idProd);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Oferta o= new Oferta();
		o.setCantidad(oferta);
		o.setEmailEmisor(email);
		Response response = invocationBuilder.post(Entity.entity(o, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	
}
