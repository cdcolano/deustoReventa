package client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dao.UsuarioDAO;
import serialization.Usuario;
import util.ReventaException;

import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
public class VentasController {
	
	private WebTarget webTarget;
	private String email;
	
	
	public VentasController(WebTarget webTarget, String email) {
		super();
		this.webTarget = webTarget;
		this.email = email;
		
	}
	
		
	public List<Producto>  getListaProductosVendidos(String email) {
		WebTarget webTarget = this.webTarget.path("reventa/ventas/productoOrdenador/"+ email);
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		WebTarget webTarget2 = this.webTarget.path("reventa/ventas/productoVehiculo/"+ email);
		List<ProductoVehiculo>lProductosVehiculo = webTarget2.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoVehiculo>>() {
	    } );
		List<Producto>lProductos= new ArrayList<>();
		lProductos.addAll(lProductosOrdenador);
		lProductos.addAll(lProductosVehiculo);
		return lProductos;
	}
	
	
	

}
