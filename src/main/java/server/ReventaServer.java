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
import serialization.Reclamacion;
import serialization.Usuario;
import service.VentasService;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 * Gestiona la parte servidora
 * @author usuario
 *
 */
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
	
	
	/** 
	 * Anade una compra
	 * @param c
	 * @param x
	 * @param idProd
	 * @return
	 */
	@POST
	@Path("/comprar/{x}/{y}")
	public Response addCompra(Compra c, @PathParam("x") String x, @PathParam("y") int idProd) {
		vs.comprarProducto(x, idProd,c.getPrecio());
		System.out.println("*Received purchase*");
		return Response.ok().build();
	}
	
	/**Anade una oferta 
	 * @param o
	 * @param x
	 * @param idProd
	 * @return
	 */
	@POST
	@Path("/oferta/{x}/{y}")
	public Response addOferta(Oferta o, @PathParam("x") String x, @PathParam("y") int idProd) {
		vs.hacerOferta(x, idProd,o.getCantidad());
		System.out.println("*Received offer*");
		return Response.ok().build();
	}
	
	
	/**
	 * Anade un mensaje 
	 * @param m
	 * @param x
	 * @param y
	 * @return
	 */
	@POST
	@Path("/enviar/{x}/{y}")
	public Response addMensaje(Mensaje m,@PathParam("x") String x,@PathParam("y") String y){
		vs.enviarMensaje(x, y, m.getContenido(), m.getFecha());
		System.out.println("*Message sent*");
		return Response.ok().build();
	}
	
	/**
	 * coje los mensajes recibidos
	 * @param x
	 * @return
	 */
	@GET
	@Path("/mensajesRecibidos/{x}")
	public List<Mensaje> getMensajesRecibidos(@PathParam("x") String x){
		 List<Mensaje>mensajes=vs.getMensajesRecibidos(x);
		 return mensajes;
	}
	
	/**
	 * coje los mensajes enviados
	 * @param x
	 * @return
	 */
	@GET
	@Path("/mensajesEnviados/{x}")
	public List<Mensaje> getMensajesEnviados(@PathParam("x") String x){
		 List<Mensaje>mensajes=vs.getMensajesEnviados(x);
		 return mensajes;
	}
	
	
	/** 
	 * Devuelve el login del usuario
	 * @param u
	 * @return
	 */
	@POST
	@Path("/logIn")
	public Response logIn(Usuario u) {
		boolean logIn=vs.logIn(u.getEmail(), u.getPassword());
		System.out.println("*Realized logIn*");
		Response r=Response.ok(logIn).build();
		System.out.println(r.getEntity());
		return Response.ok(logIn).build();
	}
	
	/**
	 * Devuelve el registro realizado
	 * @param u
	 * @return
	 */
	@POST
	@Path("/registro")
	public Response registro(Usuario u) {
		boolean registro=vs.registro(u);
		System.out.println("*Realizando registro*");
		return Response.ok(registro).build();
	}
	
	/**
	 * Anadir producto ordenador a la venta
	 * @param p
	 * @return
	 */
	@POST
	@Path("/saleOrdenador")
	public Response addProductoOrdenador(ProductoOrdenador p) {
		vs.ponerALaVenta(p.getEmailVendedor(),p);
		System.out.println("*Producto puesto a la venta*");
		return Response.ok().build();
	}
	
	/**
	 * Anadir producto vehiculo a la venta
	 * @param p
	 * @return
	 */
	@POST
	@Path("/saleVehiculo")
	public Response addProductoVehiculo(ProductoVehiculo p) {
		vs.ponerALaVenta(p.getEmailVendedor(),p);
		System.out.println("*Producto puesto a la venta*");
		return Response.ok().build();
	}


	/**
	 * Obtener un producto
	 * @param x
	 * @return
	 */
	@GET
	@Path("/getProducto/{x}")
	public Response getProducto(@PathParam("x") int x) {
		Producto producto=vs.getProducto(x);
		return Response.ok(producto).build();
	}
	
	/**
	 * @return devuelve un producto ordenador
	 */
	@GET
	@Path("/productosOrdenador")
	public List<ProductoOrdenador> getProductosOrdenador() {
		 List<ProductoOrdenador>prod=vs.getProductosOrdenador();
		 return prod;
	}
	
	/**
	 * @return devuelve un producto vehiculo
	 */
	@GET
	@Path("/productosVehiculo")
	public List<ProductoVehiculo> getProductosVehiculo() {
		 List<ProductoVehiculo>prod=vs.getProductosVehiculos();
		 return prod;
	}
	
	
	/**
	 * @return un producto ordenador a la venta
	 */
	@GET
	@Path("/productosOrdenador/venta")
	public List<ProductoOrdenador> getProductosOrdenadorEnVenta() {
		 List<ProductoOrdenador>prod=vs.getProductosOrdenadorEnVenta();
		 return prod;
	}
	
	/**
	 * @return producto ordenador reservado
	 */
	@GET
	@Path("/productosOrdenador/ventaReservado")
	public List<ProductoOrdenador> getProductosOrdenadorEnVentaConReservado() {
		 List<ProductoOrdenador>prod=vs.getProductosOrdenadorEnVentaConReservado();
		 return prod;
	}
	
	/**
	 * @return producto vehiculo a la venta
	 */
	@GET
	@Path("/productosVehiculo/venta")
	public List<ProductoVehiculo> getProductosVehiculoEnVenta() {
		 List<ProductoVehiculo>prod=vs.getProductosVehiculosEnVenta();
		 return prod;
	}
	
	/**
	 * @return producto vehiculo reservado
	 */
	@GET
	@Path("/productosVehiculo/ventaReservado")
	public List<ProductoVehiculo> getProductosVehiculoEnVentaConReservado() {
		 List<ProductoVehiculo>prod=vs.getProductosVehiculosEnVentaConReservado();
		 return prod;
	}
	
	
	/**
	 * @param x
	 * @return producto ordenador en favoritos
	 */
	@GET
	@Path("/productosOrdenador/favoritos/{x}")
	public List<ProductoOrdenador> getProductosOrdenadorFavoritos(@PathParam("x") String x) {
		 List<ProductoOrdenador>prod=vs.getProductosOrdenadorFavoritos(x);
		 return prod;
	}
	
	/**
	 * @param x
	 * @return producto vehiculos en favoritos
	 */
	@GET
	@Path("/productosVehiculo/favoritos/{x}")
	public List<ProductoVehiculo> getProductosVehiculoFavoritos(@PathParam("x") String x) {
		 List<ProductoVehiculo>prod=vs.getProductosVehiculoFavoritos(x);
		 return prod;
	}
	
	/**
	 * @param x
	 * @return numero de ventas
	 */
	@GET
	@Path("/numVentas/{x}")
	public int getNumVentas(@PathParam("x") String x) {
		return vs.getNumVentas(x);
	}
	
	/**
	 * @return las categorias
	 */
	@GET
	@Path("/categorias")
	public List<Categoria> getCategorias() {
		 List<Categoria>categorias=vs.getCategorias();
		 return categorias;
	}
	

	/**
	 * @param x
	 * @return productos favoritos
	 */
	@GET
	@Path("/numFavoritos/{x}")
	public int getNumFavoritos(@PathParam("x") int x) {
		return vs.getProductosFavoritos(x);
	}
	
	/**
	 * @param x
	 * @return denuncias realizadas
	 */
	@GET
	@Path("/denunciar/{x}")
	public Response denunciar(@PathParam("x") String x) {
		vs.denunciar(x);
		return Response.ok().build();
	}
	
	
	/**
	 * @param x
	 * @return los usuarios
	 */
	@GET
	@Path("/getUsuario/{x}")
	public Response getUsuario(@PathParam("x") String x) {
		System.out.println(x);
		Usuario usuario=vs.getUsuario(x);
		return Response.ok(usuario).build();
	}
	
	/**
	 * @param x
	 * @return lista productos ordenador vendidos
	 */
	@GET
	@Path("/ventas/productoOrdenador/{x}")
	public List<ProductoOrdenador> getVentasOrdenador(@PathParam("x") String x) {
		List<ProductoOrdenador>lista=vs.getProductosOrdenadorVendidos(x);
		return lista;
	}
	
	/**
	 * @param x
	 * @return lista productos vehiculo vendidos
	 */
	@GET
	@Path("/ventas/productoVehiculo/{x}")
	public List<ProductoVehiculo> getVentasVehiculo(@PathParam("x") String x) {
		List<ProductoVehiculo>lista=vs.getProductosVehiculoVendidos(x);
		return lista;
	}
	
	/**
	 * @param id
	 * @param email
	 * @return producto aniadido a favoritso
	 */
	@POST
	@Path("/anadirFav/{x}")
	public Response addProductoFav(int id, @PathParam("x") String email ) {
		vs.addProductoFav(id, email);
		System.out.println("*Producto añadido*");
		return Response.ok().build();
	}
	
	/**
	 * @param email2
	 * @param email
	 * @return usuario aniadido a favoritos
	 */
	@POST
	@Path("/anadirUsuarioFav/{x}")
	public Response addUsuarioFav(String email2, @PathParam("x") String email ) {
		vs.addUsuarioFav(email2, email);
		System.out.println("*Usuario anradido*");
		return Response.ok().build();
	}
	
	/**
	 * @param r
	 * @return anadir una reclamacion
	 */
	@POST
	@Path("/addReclamacion/")
	public Response addReclamacion(Reclamacion r) {
		vs.addReclamacion(r);
		System.out.println("*Reclamacion añadida*");
		return Response.ok().build();
	}
	

	/**
	 * @param idProducto
	 * @return reservar un producto
	 */
	@POST
	@Path("/reservar/{x}")
	public Response reservarProducto(@PathParam("x") int idProducto) {
		vs.reservar(idProducto);
		System.out.println("Producto reservado");
		return Response.ok().build();
	}
	
	/**
	 * @param idProducto
	 * @return quitar una serverva
	 */
	@POST
	@Path("/desReservar/{x}")
	public Response desReservarProducto(@PathParam("x") int idProducto) {
		vs.desReservar(idProducto);
		System.out.println("Producto desreservado");
		return Response.ok().build();
	}
	
	
	/**
	 * @param email
	 * @return compras realizadas pasandole el email
	 */
	@GET
	@Path("/compras/{x}")
	public List<Compra> getCompras(@PathParam("x") String email) {
		return vs.getCompras(email);
	}
	
	
	

	
}
