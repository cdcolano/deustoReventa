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

	public void anadirUsuarioFav(Usuario u, String email) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/anadirUsuarioFav/"+ email);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(u, MediaType.APPLICATION_JSON));
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
	
	public List<Producto> getProductosFavoritos() throws ReventaException{
		WebTarget webTarget = this.webTarget.path("reventa/productosOrdenador/favoritos/"+ email);
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		WebTarget webTarget2 = this.webTarget.path("reventa/productosVehiculo/favoritos/"+ email);
		List<ProductoVehiculo>lProductosVehiculo = webTarget2.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoVehiculo>>() {
	    } );
		List<Producto>lProductos= new ArrayList<>();
		lProductos.addAll(lProductosOrdenador);
		lProductos.addAll(lProductosVehiculo);
		return lProductos;
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
		
		/*Producto sacarFavs = p.getEmailVendedor()
		pProducto.add(new JLabel("Numero de veces guardado en favoritos: "));
		pProducto.add(new JLabel(""+p.getEmailVendedor()));*/
		
		
		try {
			int numFavoritos= getNumFavoritos(p.getId());
			
			JLabel lblFavoritos = new JLabel("Num favoritos: " + numFavoritos);
			pProducto.add(lblFavoritos);
		} catch (ReventaException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
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
		
		/*pContenido.add(button);
		pContenido.add(bMeGusta);
		pContenido.add(bUsuarioFav);
		pContenido.add(bOferta);*/
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
	
	public void anadirFav(Producto p, String email) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/anadirFav/"+ email);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(p.getId(), MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	public void anadirUsuarioFav(String email2, String email) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/anadirUsuarioFav/"+ email);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(email2, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	public int getVentas(String email)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/numVentas/"+ email);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			int u = response.readEntity(Integer.class);
			return u;
			
		} else {
			throw new ReventaException("" + response.getStatus());
		}
	}
	public int getNumFavoritos(int id)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/numFavoritos/"+ id);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			int u = response.readEntity(Integer.class);
			return u;
			
		} else {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	public void ordenarPorVentas(JPanel pCentro) {
		productos.sort(new Comparator<Producto>(){
			   @Override
			   public int compare(Producto p1,Producto p2) {
				   try {
					   Integer u1=getVentas(p1.getEmailVendedor());
					   Integer u2=getVentas(p2.getEmailVendedor());
					   System.out.println(u1 + " " +p1.getEmailVendedor());
					   System.out.println(u2+ " " + p2.getEmailVendedor());
					   return u2.compareTo(u1);
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
	
	
	public void ordenarPorFechaAsc( JPanel pCentro) {
		productos.sort(new Comparator<Producto>(){
			   @Override
			   public int compare(Producto p1,Producto p2) {
				   
		               Date Pdate = new Date(p1.getFechaPubli());
		               Date Qdate= new Date(p2.getFechaPubli());
					   return Pdate.compareTo(Qdate);
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
	
	
	public void ordenarPorFechaDesc(JPanel pCentro) {
		productos.sort(new Comparator<Producto>(){
			   @Override
			   public int compare(Producto p1,Producto p2) {
		               Date Pdate = new Date(p1.getFechaPubli());
		               Date Qdate= new Date(p2.getFechaPubli());
					   return Qdate.compareTo(Pdate);
				   
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
			List<Producto>productos= getProductosFavoritos();
			System.out.println("Se estan haciendo los favoritos");
			pCentro.removeAll();
			for (Producto p: productos) {
				System.out.println("fav");
				crearPanel(p, pCentro);
			}
			pCentro.revalidate();
			pCentro.repaint();
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void seleccionarOrdenador(JPanel pa) {
		pa.removeAll();
		for (Producto p: productos) {
			if (p instanceof ProductoOrdenador) {
				crearPanel(p, pa);
			}
		}
		pa.revalidate();
	}

	public void seleccionarVehiculo(JPanel pa) {
		//List<ProductoVehiculo> productos= getProductosVehiculo();
		pa.removeAll();
		for (Producto p: productos) {
			if (p instanceof ProductoVehiculo) {
				crearPanel(p, pa);
			}
		}
		pa.revalidate();
	}

	public List<ProductoVehiculo> getProductosVehiculo() {
		WebTarget webTarget2 = this.webTarget.path("reventa/productosVehiculo/venta");
		List<ProductoVehiculo>lProductosVehiculo = webTarget2.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoVehiculo>>() {
	    } );
		return lProductosVehiculo;
	}
	
	public void filtrarOrdenador(String cpu, String placaBase, String grafica, String ramMinima,
			String ramMaxima, String memMinima, String memMaxima, JPanel pa) {
			pa.removeAll();
			ArrayList<Producto>filtrado= new ArrayList<>();
			filtrado.addAll(productos);
			for (int i=productos.size()-1;i>=0;i--) {
				if(filtrado.get(i) instanceof ProductoOrdenador) {
					ProductoOrdenador p=(ProductoOrdenador)filtrado.get(i);
					if (!cpu.contentEquals("")) {
						if (!cpu.contentEquals(p.getCpu())) {
							filtrado.remove(i);
						}
					}
					if (!placaBase.contentEquals("")) {
						if (!placaBase.contentEquals(p.getPlacaBase())) {
							filtrado.remove(i);
						}
					}
					if (!grafica.contentEquals("")) {
						if (!grafica.contentEquals(p.getGrafica())) {
							filtrado.remove(i);
						}
					}
					if (!ramMinima.contentEquals("")) {
						int num=Integer.parseInt(ramMinima);
						if (num<p.getRam()) {
							filtrado.remove(i);
						}
					}
					if (!ramMaxima.contentEquals("")) {
						int num=Integer.parseInt(ramMaxima);
						if (num>p.getRam()) {
							filtrado.remove(i);
						}
					}
					if (!memMinima.contentEquals("")) {
						int num=Integer.parseInt(memMinima);
						if (num<p.getMemoria()) {
							filtrado.remove(i);
						}
					}
					if (!memMaxima.contentEquals("")) {
						int num=Integer.parseInt(memMaxima);
						if (num>p.getMemoria()) {
							filtrado.remove(i);
						}
					}
				}else {
					filtrado.remove(i);
				}
			}
			for (Producto po:filtrado) {
				crearPanel(po, pa);
			}
			pa.revalidate();
	}
	
	
	public void filtrarVehiculo(String modelo, String marca, String cvMin, String cvMax, String kmMin,
			String kmMax, String anyoMin, String anyoMax,JPanel pa) {
			pa.removeAll();
			ArrayList<Producto>filtrado= new ArrayList<>();
			filtrado.addAll(productos);
			for (int i=productos.size()-1;i>=0;i--) {
				if(filtrado.get(i) instanceof ProductoVehiculo) {
					ProductoVehiculo p=(ProductoVehiculo)filtrado.get(i);
					if (!modelo.contentEquals("")) {
						if (!modelo.contentEquals(p.getModelo())) {
							filtrado.remove(i);
						}
					}
					if (!marca.contentEquals("")) {
						if (!marca.contentEquals(p.getMarca())) {
							filtrado.remove(i);
						}
					}
					if (!cvMin.contentEquals("")) {
						int num=Integer.parseInt(cvMin);
						if (num<p.getCaballos()) {
							filtrado.remove(i);
						}
					}
					if (!cvMax.contentEquals("")) {
						int num=Integer.parseInt(cvMax);
						if (num>p.getCaballos()) {
							filtrado.remove(i);
						}
					}
					if (!kmMin.contentEquals("")) {
						int num=Integer.parseInt(kmMin);
						if (num<p.getKilometros()) {
							filtrado.remove(i);
						}
					}
					if (!kmMax.contentEquals("")) {
						int num=Integer.parseInt(kmMax);
						if (num>p.getKilometros()) {
							filtrado.remove(i);
						}
					}
					if (!anyoMin.contentEquals("")) {
						int num=Integer.parseInt(anyoMin);
						if (num<p.getAnyoFabri()) {
							filtrado.remove(i);
						}
					}
					if (!anyoMax.contentEquals("")) {
						int num=Integer.parseInt(anyoMax);
						if (num>p.getAnyoFabri()) {
							filtrado.remove(i);
						}
					}
				}else {
					filtrado.remove(i);
				}
			}
			for (Producto po:filtrado) {
				crearPanel(po, pa);
			}
			pa.revalidate();
	}

}