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
import javax.swing.JScrollPane;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.datanucleus.enhancer.methods.GetObjectId;

import client.gui.VentanaOferta;
import serialization.Compra;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Reclamacion;
import serialization.Usuario;
import util.ReventaException;

/**
 * @author Carlos
 *Clase que gestiona la logica de la ventana de compras
 */
public class ComprasController {
	private WebTarget webTarget;
	private String email,nombre;
	private Producto prod;
	private List<Producto>productos,productosFav;
	
	
	
	
	public ComprasController(WebTarget webTarget, String email) {
		super();
		this.webTarget = webTarget;
		this.email = email;
	}

	/**Realiza la compra de un producto
	 * @param email correo del usuario que realiza la compra
	 * @param idProducto id del producto a comprar
	 * @param precio por el cual se realiza la compra
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar
	 * con el servidor
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

	/**Añade un usuario a la lista de usuarios que te gustan
	 * @param u Usuario que añade a favoritos
	 * @param email del usuario añadido a me gusta
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
	public void anadirUsuarioFav(Usuario u, String email) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/anadirUsuarioFav/"+ email);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(u, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	/**
	 * @return devuelve una lista de productos registrados
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
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
	
	/**
	 * @return devuelve una lista de productos registrados
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
	
	/** devuelve un producto a partir de su id
	 * @param idProducto id del producto a devolver
	 * @return Producto con el id introducido
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
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
	
	/** 
	 * @return devuelve la lista de productos favoritos
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
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
	
	
	
	
	
	
	/**Crea el panel de la ventana para un producto
	 * @param p Producto para el que crear el panel
	 * @param pCentro JPanel al que añadir el producto
	 */
	public void crearPanel(Producto p, JPanel pCentro) {
		this.prod=p;
		JPanel pContenido= new JPanel();
		pContenido.setLayout(new BoxLayout(pContenido, BoxLayout.Y_AXIS));
		JPanel pProducto= new JPanel();
		pProducto.add(new JLabel (p.getNombre()));
		pProducto.add(new JLabel(""+ p.getPrecioSalida()+ "â‚¬"));
		JButton button= new JButton("Comprar");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(p.getId());
					comprar(ComprasController.this.email,p.getId(),p.getPrecioSalida());
					pCentro.removeAll();
					productos.remove(p);
					pCentro.removeAll();
					for (Producto p1: productos) {
						crearPanel(p1, pCentro);
					}
					pCentro.revalidate();
					
				} catch (ReventaException e1) {
					System.out.println(e1.getMessage());
				}
			}
			
		});

		JButton bMeGusta = new JButton("Anadir a favoritos");
		JButton bUsuarioFav = new JButton("Me gusta");
		
		bMeGusta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					anadirFav(p,email)	;				//ComprasController.this.();
										
				} catch (ReventaException e1) {
					System.out.println(e1.getMessage());
				}
			}
			
		});
		bUsuarioFav.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					anadirUsuarioFav(p.getEmailVendedor(),email);				//ComprasController.this.();
										
				} catch (ReventaException e1) {
					System.out.println(e1.getMessage());
				}
			}
			
		});
		
		JButton bOferta= new JButton("Oferta");
		bOferta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaOferta v= new VentanaOferta(new OfertaController(webTarget, email, p.getId()), webTarget, email, p.getId());
			}
			
		});
		
		
		pContenido.add(pProducto);
		
		pContenido.add(button);
		pContenido.add(bMeGusta);
		pContenido.add(bUsuarioFav);
		pContenido.add(bOferta);
		pCentro.add(pContenido);
		pCentro.revalidate();
		pCentro.repaint();

	}
	
	/** Añade un producto a favoritos
	 * @param p Producto a añadir a productos favoritos
	 * @param email del usuario que lo añade a favoritos
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
	public void anadirFav(Producto p, String email) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/anadirFav/"+ email);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(p.getId(), MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	
	/**Añade un usuario a la lista de usuarios que te gustan
	 * @param email2 email del usuario a añadir  a la lista
	 * @param email email del usuario que añade a la lista
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
	public void anadirUsuarioFav(String email2, String email) throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/anadirUsuarioFav/"+ email);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(email2, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
	/** Devuelve el numero de las ventas de un usuario
	 * @param email del usuario que ha realizado las ventas
	 * @return numero de ventas
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
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
	
	/** Ordena un panel por numero de ventas
	 * @param pCentro panel a ordenar
	 */
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
	
	
	/**Ordena un panel por fecha ascendente
	 * @param pCentro panel a ordenar
	 */
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
	
	/**Ordena un panel por precio ascendente
	 * @param pCentro panel a ordenar
	 */
	public void ordenarPorPrecioAsc( JPanel pCentro) {
		productos.sort(new Comparator<Producto>(){
			   @Override
			   public int compare(Producto p1,Producto p2) {
				   
		               Double precio = p1.getPrecioSalida();
		               Double precio2= p2.getPrecioSalida();
					   return precio.compareTo(precio2);
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
	
	/**Ordena un panel por precio descendente
	 * @param pCentro panel a ordenar
	 */
	public void ordenarPorPrecioDesc( JPanel pCentro) {
		productos.sort(new Comparator<Producto>(){
			   @Override
			   public int compare(Producto p1,Producto p2) {
				   
		               Double precio = p1.getPrecioSalida();
		               Double precio2= p2.getPrecioSalida();
					   return precio2.compareTo(precio);
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
	
	
	/**Ordena un panel por fecha descendente
	 * @param pCentro panel a ordenar
	 */
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
	
	
	/**Muestra los favoritos de un usuario en un panel
	 * @param pCentro panel en el que se muestra
	 * @param email del usuario que tiene los productos en favoritos
	 */
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
	
	/**
	 * @return lista con los productos ordenador en venta
	 */
	public List<ProductoOrdenador> getProductosOrdenador(){
		WebTarget webTarget = this.webTarget.path("reventa/productosOrdenador/venta");
		List<ProductoOrdenador>lProductosOrdenador = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoOrdenador>>() {
	     } );
		return lProductosOrdenador;
	}

	/**filtra unicamente los productos ordenador
	 * @param pa Panel en el que se muestran
	 */
	public void seleccionarOrdenador(JPanel pa) {
		pa.removeAll();
		for (Producto p: productos) {
			if (p instanceof ProductoOrdenador) {
				crearPanel(p, pa);
			}
		}
		pa.revalidate();
	}

	/**filtra unicamente los productos vehiculo
	 * @param pa Panel en el que se muestran
	 */
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

	/**
	 * @return devuelve una lista con los producto vehiculo en venta
	 */
	public List<ProductoVehiculo> getProductosVehiculo() {
		WebTarget webTarget2 = this.webTarget.path("reventa/productosVehiculo/venta");
		List<ProductoVehiculo>lProductosVehiculo = webTarget2.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<ProductoVehiculo>>() {
	    } );
		return lProductosVehiculo;
	}
	
	/** Filtra en base a unas caracteristicas
	 * @param cpu deseada
	 * @param placaBase deseada
	 * @param grafica deseada
	 * @param ramMinima deseada, debe ser numerica
	 * @param ramMaxima deseada, debe ser numerica
	 * @param memMinima deseada, debe ser numerica
	 * @param memMaxima deseada, debe ser numerica
	 * @param pa panel en el que deben desplegarse los productos
	 */
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
	
	
	/** Filtra los productos vehiculos  
	 * @param modelo deseado
	 * @param marca deseada
	 * @param cvMin deseados, debe ser numerico
	 * @param cvMax deseados, debe ser numerico
	 * @param kmMin deseados, debe ser numerico
	 * @param kmMax deseados, debe ser numerico
	 * @param anyoMin deseado, debe ser numerico
	 * @param anyoMax deseado, debe ser numerico
	 * @param pa panel al que añadir los vehiculos
	 */
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
