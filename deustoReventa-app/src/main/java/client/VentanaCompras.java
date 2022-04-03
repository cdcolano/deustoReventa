package client;

import javax.swing.JFrame;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import serialization.Compra;
import serialization.Producto;
import serialization.Usuario;
import util.ReventaException;


public class VentanaCompras extends JFrame{

	private Client client;
	private WebTarget webTarget;
	
	public VentanaCompras(Client cliente, WebTarget webTarget, String email) {
		this.client=cliente;
		this.webTarget=webTarget;
		
		//TODO desplegar todos los productos
		//TODO coger el producto seleccionado
	}
	
	
	public void comprar(String email, int idProducto, double precio) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/comprar");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		
		
		Compra c= new Compra();
		c.setComprador(null);
		Producto p= getProducto(idProducto);
		c.setProducto(p);
		c.setPrecio(precio);
		Response response = invocationBuilder.post(Entity.entity(c, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	
	public Producto getProducto(int idProducto)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("collector/getProducto/"+ idProducto);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			Producto producto = response.readEntity(Producto.class);
			return producto;
		} else {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	
	public Usuario getUsuario(String email)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("collector/getUsuario/"+ email);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			Usuario u = response.readEntity(Usuario.class);
			return u;
		} else {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	
	
	
	
}
