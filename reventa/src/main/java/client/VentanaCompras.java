package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import serialization.Categoria;
import serialization.Compra;
import serialization.Producto;
import serialization.Usuario;
import util.ReventaException;
import javax.swing.*;
import java.awt.*;


public class VentanaCompras extends JFrame{

	private Client client;
	private WebTarget webTarget;
	private String email;
	private Producto prod;
	
	public VentanaCompras(Client cliente, WebTarget webTarget, String email) {
		this.client=cliente;
		this.webTarget=webTarget;
		this.email=email;
		JPanel pCentro= new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		
		try {
			List<Producto> productos=getProductos();
			
			for (Producto p:productos) {
				this.prod=p;
				JPanel pProducto= new JPanel();
				pProducto.add(new JLabel (p.getNombre()));
				pProducto.add(new JLabel(""+ p.getPrecioSalida()+ "€"));
				JButton button= new JButton("Comprar");
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							VentanaCompras.this.comprar(VentanaCompras.this.email,VentanaCompras.this.prod.getId(),VentanaCompras.this.prod.getPrecioSalida());
						} catch (ReventaException e1) {
							System.out.println(e1.getMessage());
						}
					}
					
				});
				pProducto.add(button);
				pCentro.add(pProducto);
				
			}
			
		} catch (ReventaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getContentPane().add(pCentro,BorderLayout.CENTER);
		
		
		
		//TODO desplegar todos los productos
		//TODO coger el producto seleccionado
	}
	
	



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
	
	public List<Producto> getProductos() throws ReventaException{
		WebTarget webTarget = this.webTarget.path("reventa/productos");
		List<Producto>lProductos = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<Producto>>() {
	     } );
		
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
