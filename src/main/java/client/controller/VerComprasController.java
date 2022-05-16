package client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.UsuarioDAO;
import serialization.Compra;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Reclamacion;
import util.ReventaException;

public class VerComprasController {
	
	private WebTarget webTarget;
	private String email;
	UsuarioDAO uDao;

	
	public VerComprasController(WebTarget webTarget, String email) {
		super();
		this.webTarget = webTarget;
		this.email = email;
	}
	
	public List<Compra>  getListaProductosComprados(String email)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/compras/"+email);
		List<Compra>lCompra = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<Compra>>() {
	     } );
		return lCompra;
	}
	
	public Producto getPoducto(Compra c) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/getProducto/"+ c.getProducto());
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
		if (c.getIdCat()==1) {
			return response.readEntity(ProductoOrdenador.class);
		}else {
			System.out.println("Este es el id " +c.getIdCat());
			return response.readEntity(ProductoVehiculo.class);
		}
	}
	public void addReclamacion(Reclamacion r, double importeReclamado) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/addReclamacion"+ importeReclamado);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(r, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}

	public void denunciar(Producto m) throws ReventaException {
		// TODO Auto-generated method stub
		WebTarget webTarget = this.webTarget.path("reventa/denunciar/"+ m.getEmailVendedor());
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}

}
