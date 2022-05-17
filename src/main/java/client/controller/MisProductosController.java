package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.gui.VentanaMisProductos;
import client.gui.VentanaOferta;
import serialization.Compra;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import util.ReventaException;

public class MisProductosController {

	private WebTarget webTarget;
	private String email,nombre;
	private Producto prod;
	private List<Producto>productos,productosFav;
	
	
	
	
	public MisProductosController(WebTarget webTarget, String email) {
		super();
		this.webTarget = webTarget;
		this.email = email;
	}

	public List<Producto> getProductos() throws ReventaException{
		WebTarget webTarget = this.webTarget.path("reventa/productosOrdenador");
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		WebTarget webTarget2 = this.webTarget.path("reventa/productosVehiculo");
		List<ProductoVehiculo>lProductosVehiculo = webTarget2.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoVehiculo>>() {
	     } );
		List<Producto>lProductos= new ArrayList<>();
		lProductos.addAll(lProductosOrdenador);
		lProductos.addAll(lProductosVehiculo);
		return lProductos;
	}
	
	public List<Producto> getProductosEnVenta() throws ReventaException{
		List<ProductoOrdenador>lProductosOrdenador = getProductosOrdenador();
		WebTarget webTarget2 = this.webTarget.path("reventa/productosVehiculo/venta");
		List<ProductoVehiculo>lProductosVehiculo = webTarget2.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoVehiculo>>() {
	    } );
		List<Producto>lProductos= new ArrayList<>();
		lProductos.addAll(lProductosOrdenador);
		lProductos.addAll(lProductosVehiculo);
		return lProductos;
	}
	
	public List<Producto> getProductosEnVentaConReservado() throws ReventaException{
		List<ProductoOrdenador>lProductosOrdenador = getProductosOrdenadorConReservado();
		WebTarget webTarget2 = this.webTarget.path("reventa/productosVehiculo/ventaReservado");
		List<ProductoVehiculo>lProductosVehiculo = webTarget2.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoVehiculo>>() {
	    } );
		List<Producto>lProductos= new ArrayList<>();
		lProductos.addAll(lProductosOrdenador);
		lProductos.addAll(lProductosVehiculo);
		return lProductos;
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
	
	
	
	public void crearPanel(Producto p, JPanel pCentro) {
		this.prod=p;
		JPanel pContenido= new JPanel();
		pContenido.setLayout(new BoxLayout(pContenido, BoxLayout.Y_AXIS));
		JPanel pProducto= new JPanel();
		pProducto.add(new JLabel (p.getNombre()));
		pProducto.add(new JLabel(""+ p.getPrecioSalida()+ "€"));
		JButton bReservar = new JButton("Reservar");
		bReservar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					reservar(p.getId());
					//pCentro.removeAll();
					pCentro.revalidate();
					pCentro.repaint();
				} catch (ReventaException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});		
		JButton bDesReservar = new JButton("Quitar reserva");
		bDesReservar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					desReservar(p.getId());
				} catch (ReventaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pCentro.revalidate();
			}
			
		});
			
		pContenido.add(pProducto);
		if(p.isReservado()) {
			pContenido.add(bDesReservar);
		}
		else {
			pContenido.add(bReservar);
		}
		
		pCentro.add(pContenido);
		pCentro.revalidate();
		pCentro.repaint();

	}
	
	public void reservar(int idProducto) throws ReventaException{
		WebTarget webTarget = this.webTarget.path("reventa/reservar/"+idProducto);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(idProducto, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	public void desReservar(int idProducto) throws ReventaException{
		WebTarget webTarget = this.webTarget.path("reventa/desReservar/"+idProducto);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(idProducto, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
	public List<ProductoOrdenador> getProductosOrdenador(){
		WebTarget webTarget = this.webTarget.path("reventa/productosOrdenador/venta");
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		return lProductosOrdenador;
	}
	
	public List<ProductoOrdenador> getProductosOrdenadorConReservado(){
		WebTarget webTarget = this.webTarget.path("reventa/productosOrdenador/ventaReservado");
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		return lProductosOrdenador;
	}

	public List<ProductoVehiculo> getProductosVehiculo() {
		WebTarget webTarget2 = this.webTarget.path("reventa/productosVehiculo/venta");
		List<ProductoVehiculo>lProductosVehiculo = webTarget2.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoVehiculo>>() {
	    } );
		return lProductosVehiculo;
	}

}
