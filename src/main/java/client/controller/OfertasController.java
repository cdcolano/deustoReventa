package client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import serialization.Oferta;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import util.ReventaException;

public class OfertasController {
	private WebTarget webTarget;
	private String email;
	private List<Producto>productos;
	private Producto prod;
	
	
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
	public OfertasController(WebTarget webTarget, String email) {
		super();
		this.webTarget = webTarget;
		this.email = email;
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
	
	public List<ProductoOrdenador> getProductosOrdenadorConReservado(){
		WebTarget webTarget = this.webTarget.path("reventa/productosOrdenador/ventaReservado");
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		return lProductosOrdenador;
	}
	
	public void crearPanel(Producto p, JPanel pCentro) {
		this.prod=p;
		JPanel pContenido= new JPanel();
		pContenido.setLayout(new BoxLayout(pContenido, BoxLayout.Y_AXIS));
		JPanel pProducto= new JPanel();
		pProducto.add(new JLabel (p.getNombre() ));
		pProducto.add(new JLabel("Precio salida: "+ p.getPrecioSalida()+ "€" + "\n"));
		
		for(Oferta o : p.getOfertasRecibidas()) {
			pProducto.add(new JLabel ("Oferta recibida " + "\n"));
			pProducto.add(new JLabel ("Cantidad: " +o.getCantidad()));
			pProducto.add(new JLabel ("Fecha: " + new Date(o.getFecha())));
			pProducto.add(new JButton("Aceptar"));
			pProducto.add(new JButton("Rechazar" + "\n"));
		}
		/*JButton bReservar = new JButton("Reservar");
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
			
		
		
		
		pContenido.add(pProducto);*/
		
		/*if(p.isReservado()) {
			pContenido.add(bDesReservar);
		}
		else {
			pContenido.add(bReservar);
		}*/
		pContenido.add(pProducto);
		pCentro.add(pContenido);
		pCentro.revalidate();
		pCentro.repaint();

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
	
	public List<ProductoOrdenador> getProductosOrdenador(){
		WebTarget webTarget = this.webTarget.path("reventa/productosOrdenador/venta");
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		return lProductosOrdenador;
	}
}
