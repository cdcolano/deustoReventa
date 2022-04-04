package server;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import serialization.Categoria;
import serialization.Compra;
import serialization.Producto;
import serialization.Usuario;
import service.VentasService;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

@Path("/reventa")
@Produces(MediaType.APPLICATION_JSON)
public class ReventaServer {
		
	private VentasService vs;
	
	
	public ReventaServer() {
		vs= new VentasService();
	}
	
	
	@POST
	@Path("/comprar/{x}/{y}")
	public Response addCompra(Compra c, @PathParam("x") String x, @PathParam("y") int idProd) {
		vs.comprarProducto(x, idProd,c.getPrecio());
		System.out.println("*Received purchase*");
		return Response.ok().build();
	}
	
	@POST
	@Path("/logIn")
	public Response logIn(Usuario u) {
		boolean logIn=vs.logIn(u.getEmail(), u.getPassword());
		System.out.println("*Realized logIn*");
		Response r=Response.ok(logIn).build();
		System.out.println(r.getEntity());
		return Response.ok(logIn).build();
	}
	
	@POST
	@Path("/registro")
	public Response registro(Usuario u) {
		boolean registro=vs.registro(u);
		System.out.println("*Realizando registro*");
		return Response.ok(registro).build();
	}
	
	@POST
	@Path("/sale/{x}")
	public Response addProducto(Producto p, @PathParam("x") String email) {
		vs.ponerALaVenta(email,p);
		System.out.println("*Producto puesto a la venta*");
		return Response.ok().build();
	}
	
	@GET
	@Path("/getProducto/{x}")
	public Response getProducto(@PathParam("x") int x) {
		Producto producto=vs.getProducto(x);
		return Response.ok(producto).build();
	}
	
	@GET
	@Path("/productos")
	public List<Producto> getProductos() {
		 List<Producto>prod=vs.getProductos();
		 return prod;
	}
	
	@GET
	@Path("/categorias")
	public List<Categoria> getCategorias() {
		 List<Categoria>categorias=vs.getCategorias();
		 return categorias;
	}
	
	@GET
	@Path("/getUsuario/{x}")
	public Response getUsuario(@PathParam("x") String x) {
		System.out.println(x);
		Usuario usuario=vs.getUsuario(x);
		return Response.ok(usuario).build();
	}
	

	
}
