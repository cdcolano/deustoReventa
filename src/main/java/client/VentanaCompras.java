package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import util.ReventaException;
import javax.swing.*;
import java.awt.*;


public class VentanaCompras extends JFrame{

	private Client client;
	private WebTarget webTarget;
	private String email;
	private Producto prod;
	private List<Producto> productos;
	
	public VentanaCompras(Client cliente, WebTarget webTarget, String email) {
		this.client=cliente;
		this.webTarget=webTarget;
		this.email=email;
		JPanel pCentro= new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		JComboBox<String>cbOrdenar= new JComboBox<>();
		cbOrdenar.addItem("Sin orden");
		cbOrdenar.addItem("Ordenar por Ventas del Vendedor");
		cbOrdenar.addItem("Ordenar por Fecha de Publicacion ascendente");
		cbOrdenar.addItem("Ordenar por Fecha de Publicacion descendente");
		cbOrdenar.addItem("Favoritos");
		
		cbOrdenar.addItemListener (new ItemListener () {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					String seleccion=(String)cbOrdenar.getSelectedItem();
					if (seleccion.compareTo("Ordenar por Ventas del Vendedor")==0) {
							ordenarPorVentas(productos, pCentro);
					}
					else if (seleccion.compareTo("Ordenar por Fecha de Publicacion ascendente")==0) {
						ordenarPorFechaAsc(productos, pCentro);
					}
					else if (seleccion.compareTo("Ordenar por Fecha de Publicacion descendente")==0) {
						ordenarPorFechaDesc(productos, pCentro);
					}
					else if (seleccion.compareTo("Favoritos")==0) {
						mostrarFavoritos(pCentro, email);
					}
				}
			}
		});
		
		getContentPane().add(cbOrdenar, BorderLayout.NORTH);
		
		try {
			 productos=getProductosEnVenta();
			
			for (Producto p:productos) {
				crearPanel(p, pCentro);
			}
			
		} catch (ReventaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getContentPane().add(pCentro,BorderLayout.CENTER);
		JPanel pVender= new JPanel();
		JButton bVender= new JButton("Vender");
		bVender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaProducto vR= new VentanaProducto(client, VentanaCompras.this.webTarget,VentanaCompras.this.email);
				VentanaCompras.this.dispose();
				
			}
			
		});
		
		pVender.add(bVender);
		getContentPane().add(pVender, BorderLayout.SOUTH);
		this.pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setVisible(true);
		
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
		WebTarget webTarget = this.webTarget.path("reventa/productosOrdenador/venta");
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		WebTarget webTarget2 = this.webTarget.path("reventa/productosVehiculo/venta");
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
		JPanel pProducto= new JPanel();
		pProducto.add(new JLabel (p.getNombre()));
		pProducto.add(new JLabel(""+ p.getPrecioSalida()+ "€"));
		JButton button= new JButton("Comprar");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					VentanaCompras.this.comprar(VentanaCompras.this.email,VentanaCompras.this.prod.getId(),VentanaCompras.this.prod.getPrecioSalida());
					pCentro.removeAll();
					VentanaCompras.this.productos=getProductos();
					pCentro.revalidate();
					
				} catch (ReventaException e1) {
					System.out.println(e1.getMessage());
				}
			}
			
		});
		pProducto.add(button);
		pCentro.add(pProducto);
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
	
	
	public void ordenarPorVentas(List<Producto>productos, JPanel pCentro) {
		productos.sort(new Comparator<Producto>(){
			   @Override
			   public int compare(Producto p1,Producto p2) {
				   try {
					   Usuario u1=getUsuario(p1.getEmailVendedor());
					   Usuario u2=getUsuario(p2.getEmailVendedor());
					   return u1.getProductosVendidos().size()-u2.getProductosVendidos().size();
				   }
					catch(Exception exc) {
						System.out.println("*ERROR * " + exc.getMessage());
						return 0;
					}
			     //TODO return 1 if rhs should be before lhs 
			     //     return -1 if lhs should be before rhs
			     //     return 0 otherwise (meaning the order stays the same)
			     }
			 });
		pCentro.removeAll();
		for (Producto p:productos) {
			crearPanel(p, pCentro);
		}
		pCentro.revalidate();
		//TODO si no pCentro repaint
		
	}
	
	
	public void ordenarPorFechaAsc(List<Producto>productos, JPanel pCentro) {
		productos.sort(new Comparator<Producto>(){
			   @Override
			   public int compare(Producto p1,Producto p2) {
				   try {
		               Date Pdate = new Date(p1.getFechaPubli());
		               Date Qdate= new Date(p2.getFechaPubli());
					   return Pdate.compareTo(Qdate);
				   }
					catch(Exception exc) {
						System.out.println("*ERROR * " + exc.getMessage());
						return 0;
					}
			     //TODO return 1 if rhs should be before lhs 
			     //     return -1 if lhs should be before rhs
			     //     return 0 otherwise (meaning the order stays the same)
			     }
			 });
		pCentro.removeAll();
		for (Producto p:productos) {
			crearPanel(p, pCentro);
		}
		pCentro.revalidate();
		//TODO si no pCentro repaint
	}
	
	public void ordenarPorFechaDesc(List<Producto>productos, JPanel pCentro) {
		productos.sort(new Comparator<Producto>(){
			   @Override
			   public int compare(Producto p1,Producto p2) {
				   try {
		               Date Pdate = new Date(p1.getFechaPubli());
		               Date Qdate= new Date(p2.getFechaPubli());
					   return Qdate.compareTo(Pdate);
				   }
					catch(Exception exc) {
						System.out.println("*ERROR * " + exc.getMessage());
						return 0;
					}
			     //TODO return 1 if rhs should be before lhs 
			     //     return -1 if lhs should be before rhs
			     //     return 0 otherwise (meaning the order stays the same)
			     }
			 });
		pCentro.removeAll();
		for (Producto p:productos) {
			crearPanel(p, pCentro);
		}
		pCentro.revalidate();
		//TODO si no pCentro repaint
	}
	
	
	public void mostrarFavoritos(JPanel pCentro, String email) {
		try {
			Usuario u=getUsuario(email);
			for (Producto p: u.getProductosFavoritos()) {
				crearPanel(p, pCentro);
			}
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
