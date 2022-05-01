package client.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.lang.*;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.gui.VentanaProducto;
import serialization.Categoria;
import serialization.Mensaje;
import serialization.Usuario;
import util.ReventaException;

public class ChatController {
	private WebTarget webTarget;
	
	public Usuario getUsuario(String email)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/getUsuario/"+ email);
		System.out.println(webTarget.getUri());
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			Usuario u = response.readEntity(Usuario.class);
			return u;
		} else {
			throw new ReventaException("" + response.getStatus());
		}	
	}
	
	/*public List<Mensaje> getMensajesEnviados(String email) throws ReventaException{
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
	}*/
	
	public List<Mensaje> getConversacion(String emailEmisor, String emailReceptor){
		List<Mensaje> lConversacionRecibido= new ArrayList<Mensaje>();
		try {
			Usuario emisor = getUsuario(emailEmisor);
			Usuario receptor = getUsuario(emailReceptor);
			List<Mensaje> lMensajesEmisor = emisor.getMensajesEnviados();
			List<Mensaje> lMensajesReceptor = receptor.getMensajesRecibidos();
	
			for(int i =0; i<lMensajesEmisor.size();i++) {
				for(int t =0; t<lMensajesReceptor.size();i++) {
					if(lMensajesEmisor.get(i).getId()==lMensajesReceptor.get(t).getId()) {
						lConversacionRecibido.add(lMensajesEmisor.get(i));
					}
				}
			}
			
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lConversacionRecibido;
	}
	public List<Mensaje> getListaEntera(String email1, String email2){
		List<Mensaje> lm1 = new ArrayList<>();
		List<Mensaje> lm2 = new ArrayList<>();
		lm1= getConversacion(email1, email2);
		lm2= getConversacion(email2, email1);
		lm1.addAll(lm2);
		
		lm1.sort(Comparator.comparing(Mensaje::getFecha));
		
		return lm1;
		
	}
	
	
	
}
