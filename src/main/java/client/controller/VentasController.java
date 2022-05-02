package client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.UsuarioDAO;
import serialization.Usuario;
import util.ReventaException;

import serialization.Producto;
public class VentasController {
	
	private WebTarget webTarget;
	private String email;
	UsuarioDAO uDao;
	
	public VentasController(WebTarget webTarget, String email,UsuarioDAO uDao) {
		super();
		this.webTarget = webTarget;
		this.email = email;
		this.uDao = uDao;
		
		
		
		
	}
	
		
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
	
	public List<Producto> getListaProductosVendidos(String email){
		List<Producto> l1 = new ArrayList<>();
		try {
			Usuario u = getUsuario(email);
			l1 = u.getProductosVendidos();
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l1;
		
	}
	
	

}
