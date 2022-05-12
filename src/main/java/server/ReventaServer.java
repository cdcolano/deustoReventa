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
import serialization.Mensaje;
import serialization.Oferta;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import service.VentasService;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

@Path("/reventa")
@Produces(MediaType.APPLICATION_JSON)
public class ReventaServer {
		
	private VentasService vs;
	
	

	public void setVs(VentasService vs) {
		this.vs = vs;
	}


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
	@Path("/oferta/{x}/{y}")
	public Response addOferta(Oferta o, @PathParam("x") String x, @PathParam("y") int idProd) {
		vs.hacerOferta(x, idProd,o.getCantidad());
		System.out.println("*Received offer*");
		return Response.ok().build();
	}
	
	
	@POST
	@Path("/enviar/{x}/{y}")
	public Response addMensaje(Mensaje m,@PathParam("x") String x,@PathParam("y") String y){
		vs.enviarMensaje(x, y, m.getContenido(), m.getFecha());
		System.out.println("*Message sent*");
		return Response.ok().build();
	}
	
	@GET
	@Path("/mensajesRecibidos/{x}")
	public List<Mensaje> getMensajesRecibidos(@PathParam("x") String x){
		 List<Mensaje>mensajes=vs.getMensajesRecibidos(x);
		 return mensajes;
	}
	
	@GET
	@Path("/mensajesEnviados/{x}")
	public List<Mensaje> getMensajesEnviados(@PathParam("x") String x){
		 List<Mensaje>mensajes=vs.getMensajesEnviados(x);
		 return mensajes;
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
	@Path("/saleOrdenador")
	public Response addProductoOrdenador(ProductoOrdenador p) {
		vs.ponerALaVenta(p.getEmailVendedor(),p);
		System.out.println("*Producto puesto a la venta*");
		return Response.ok().build();
	}
	
	@POST
	@Path("/saleVehiculo")
	public Response addProductoVehiculo(ProductoVehiculo p) {
		vs.ponerALaVenta(p.getEmailVendedor(),p);
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
	@Path("/productosOrdenador")
	public List<ProductoOrdenador> getProductosOrdenador() {
		 List<ProductoOrdenador>prod=vs.getProductosOrdenador();
		 return prod;
	}
	
	@GET
	@Path("/productosVehiculo")
	public List<ProductoVehiculo> getProductosVehiculo() {
		 List<ProductoVehiculo>prod=vs.getProductosVehiculos();
		 return prod;
	}
	
	
	@GET
	@Path("/productosOrdenador/venta")
	public List<ProductoOrdenador> getProductosOrdenadorEnVenta() {
		 List<ProductoOrdenador>prod=vs.getProductosOrdenadorEnVenta();
		 return prod;
	}
	
	@GET
	@Path("/productosVehiculo/venta")
	public List<ProductoVehiculo> getProductosVehiculoEnVenta() {
		 List<ProductoVehiculo>prod=vs.getProductosVehiculosEnVenta();
		 return prod;
	}
	
	@GET
	@Path("/productosOrdenador/favoritos/{x}")
	public List<ProductoOrdenador> getProductosOrdenadorFavoritos(@PathParam("x") String x) {
		 List<ProductoOrdenador>prod=vs.getProductosOrdenadorFavoritos(x);
		 return prod;
	}
	
	@GET
	@Path("/productosVehiculo/favoritos/{x}")
	public List<ProductoVehiculo> getProductosVehiculoFavoritos(@PathParam("x") String x) {
		 List<ProductoVehiculo>prod=vs.getProductosVehiculoFavoritos(x);
		 return prod;
	}
	
	@GET
	@Path("/numVentas/{x}")
	public int getNumVentas(@PathParam("x") String x) {
		return vs.getNumVentas(x);
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
	
	@GET
	@Path("/ventas/productoOrdenador/{x}")
	public List<ProductoOrdenador> getVentasOrdenador(@PathParam("x") String x) {
		List<ProductoOrdenador>lista=vs.getProductosOrdenadorVendidos(x);
		return lista;
	}
	
	@GET
	@Path("/ventas/productoVehiculo/{x}")
	public List<ProductoVehiculo> getVentasVehiculo(@PathParam("x") String x) {
		List<ProductoVehiculo>lista=vs.getProductosVehiculoVendidos(x);
		return lista;
	}
	
	@POST
	@Path("/anadirFav/{x}")
	public Response addProductoFav(int id, @PathParam("x") String email ) {
		vs.addProductoFav(id, email);
		System.out.println("*Producto añadido*");
		return Response.ok().build();
	}
	
	@POST
	@Path("/anadirUsuarioFav/{x}")
	public Response addUsuarioFav(String email2, @PathParam("x") String email ) {
		vs.addUsuarioFav(email2, email);
		System.out.println("*Usuario añadido*");
		return Response.ok().build();
	}
	
	
	

	
}
