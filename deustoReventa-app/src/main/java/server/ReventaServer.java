package server;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import javax.ws.rs.core.MediaType;

@Path("/reventa")
@Produces(MediaType.APPLICATION_JSON)
public class ReventaServer {
		
	private VentasService vs;
	
	
	public ReventaServer() {
		vs= new VentasService();
	}
	
	
	@POST
	@Path("/comprar")
	public Response addCompra(Compra c) {
		String email=c.getComprador().getEmail();
		int idProd=c.getProducto().getId();
		vs.comprarProducto(email, idProd,c.getPrecio());
		System.out.println("*Received purchase*");
		return Response.ok().build();
	}
	
	@POST
	@Path("/logIn")
	public Response logIn(Usuario u) {
		boolean logIn=vs.logIn(u.getEmail(), u.getPassword());
		System.out.println("*Realized logIn*");
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
	@Path("/sale")
	public Response addProducto(Producto p) {
		String email=p.getVendedor().getEmail();
		
		vs.ponerALaVenta(email, p.getNombre(), p.getPrecioSalida(), p.getCategoria());
		System.out.println("*Producto puesto a la venta*");
		return Response.ok().build();
	}
	
	@GET
	@Path("/getProducto/{x}")
	public Response getProducto(@QueryParam("x") int x) {
		Producto producto=vs.getProducto(x);
		return Response.ok(producto).build();
	}
	
	@GET
	@Path("/getUsuario/{x}")
	public Response getUsuario(@QueryParam("x") String x) {
		Usuario usuario=vs.getUsuario(x);
		return Response.ok(usuario).build();
	}
	
	@GET
	@Path("/getProductos")
	public Response getProductos() {
		List<Producto> p=vs.getProductos();
		return Response.ok(p).build();
	}
	
	
}
