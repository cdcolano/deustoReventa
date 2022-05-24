package client.controller;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import serialization.Categoria;
import serialization.Producto;
import serialization.Usuario;
import util.ReventaException;

/**Clase que gestiona la logica de la venta producto
 * @author Carlos
 *
 */
public class ProductoController {
	private String email;
	private WebTarget webTarget;
	
	public ProductoController(WebTarget wt, String email) {
		this.webTarget=wt;
		this.email=email;
	}
	
	
	//necesito un getCategorias()
	/**
	 * @return lista con las categorias registradas en la bd
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
	public List<Categoria> getCategoria() throws ReventaException{
		WebTarget webTarget = this.webTarget.path("reventa/categorias");
		List<Categoria>lCategorias = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<Categoria>>() {
	     } );
		
		return lCategorias;
	}
	
	/** Anade un producto ordenador
	 * @param p Producto a anadir
	 */
	public void addProductoOrdenador(Producto p) {
		try {
			WebTarget webTarget=this.webTarget.path("reventa/saleOrdenador");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			System.out.println(webTarget.getUri());
			p.setEmailVendedor(email);
			Response response = invocationBuilder.post(Entity.entity(p, MediaType.APPLICATION_JSON));
			System.out.println(response.getStatus());
		}catch(Exception e) {
			//lError.setText("Ha ocurrido un error al conectar con el servidor");
			System.out.println(e.getMessage());
		}
	}
	
	/**Anade un producto vehiculo
	 * @param p Producto vehiculo a anadir
	 */
	public void addProductoVehiculo(Producto p) {
		try {
			WebTarget webTarget=this.webTarget.path("reventa/saleVehiculo");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			System.out.println(webTarget.getUri());
			p.setEmailVendedor(email);
			Response response = invocationBuilder.post(Entity.entity(p, MediaType.APPLICATION_JSON));
			System.out.println(response.getStatus());
		}catch(Exception e) {
			//lError.setText("Ha ocurrido un error al conectar con el servidor");
			System.out.println(e.getMessage());
		}
	}

	
	/** Registra un usuario 
	 * @param u usuario a registrar
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
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
