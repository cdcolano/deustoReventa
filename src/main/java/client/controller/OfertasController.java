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
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.gui.VentanaOfertas;
import serialization.Compra;
import serialization.Oferta;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import util.ReventaException;

/**Clase que gestiona la logica de la ventana ofertas
 * @author Carlos
 *
 */
public class OfertasController {
	private WebTarget webTarget;
	private String email;
	private List<Producto>productos;
	private List<Oferta> ofertas;
	private Producto prod;
	
	
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}
	
	public OfertasController(WebTarget webTarget, String email) {
		super();
		this.webTarget = webTarget;
		this.email = email;
	}
	
	/**
	 * @return Devuelve productos en venta que no estan reservados
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
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
	
	/**
	 * @return Devuelve una lista con los productos ordenador que no estan reservados
	 */
	public List<ProductoOrdenador> getProductosOrdenadorConReservado(){
		WebTarget webTarget = this.webTarget.path("reventa/productosOrdenador/ventaReservado");
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		return lProductosOrdenador;
	}
	
	/** Realiza la compra de un producto
	 * @param email del usuario que realiza la compra
	 * @param idProducto id del producto que se compra
	 * @param precio por el que se cierra la compra
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
	public void comprar(String email, int idProducto, double precio) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/comprar/"+email +"/"+ idProducto);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Compra c= new Compra();
		c.setPrecio(precio);
		Response response = invocationBuilder.post(Entity.entity(c, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	/**Cra un panel para un producto
	 * @param p Producto a anadir al panel
	 * @param pCentro panel al que se anade el producto
	 * @param vo ventana a la que se anade el panel
	 */
	public void crearPanel(Producto p, JPanel pCentro, VentanaOfertas vo) {
		this.prod=p;
		JPanel pContenido= new JPanel();
		pContenido.setLayout(new BoxLayout(pContenido, BoxLayout.Y_AXIS));
		JPanel pProducto= new JPanel();
		pProducto.add(new JLabel (p.getNombre() ));
		pProducto.add(new JLabel("Precio salida: "+ p.getPrecioSalida()+ "€" + "\n"));
		
		for(Oferta o : p.getOfertasRecibidas()) {
			pProducto.add(new JLabel ("Oferta recibida:" + "\n"));
			pProducto.add(new JLabel ("Cantidad: " +o.getCantidad()));
			pProducto.add(new JLabel ("Fecha: " + new Date(o.getFecha())));
			JButton bAceptar = new JButton("Aceptar");
			bAceptar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						comprar(o.getEmailEmisor(), p.getId(), o.getCantidad());
						productos.remove(p);
						pCentro.removeAll();
						pCentro.revalidate();
						pCentro.repaint();
						JLabel lGracias = new JLabel("Has aceptado la compra!");
						pCentro.add(lGracias);
						//vo.dispose();
					} catch (ReventaException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			});
			pProducto.add(bAceptar);
		}		
		pContenido.add(pProducto);
		pCentro.add(pContenido);
		pCentro.revalidate();
		pCentro.repaint();

	}
	
	
	/**
	 * @return devuelve una lista con los productos en venta
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
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
	
	/**
	 * @return devuelve una lista con los productos ordenador en venta
	 */
	public List<ProductoOrdenador> getProductosOrdenador(){
		WebTarget webTarget = this.webTarget.path("reventa/productosOrdenador/venta");
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		return lProductosOrdenador;
	}
}
