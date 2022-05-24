package service;



import javax.jdo.JDOHelper;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.omg.CORBA.UserException;

import dao.*;
import serialization.*;
import java.util.*;


/**
 * clase que gestiona la logica de la aplicacion
 * 
 * @author usuario
 *
 */
public class VentasService {
	IProductoDAO productDao;
	IUsuarioDAO	usuarioDao;
	ICategoriaDAO categoriaDao;

	PersistenceManagerFactory pmf;
	
	public VentasService() {
		productDao= new ProductoDAO();
		usuarioDao= new UsuarioDAO();
		categoriaDao= new CategoriaDAO();
		pmf= JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}
	
	
	public IProductoDAO getProductDao() {
		return productDao;
	}


	public void setProductDao(IProductoDAO productDao) {
		this.productDao = productDao;
	}


	public IUsuarioDAO getUsuarioDao() {
		return usuarioDao;
	}


	public void setUsuarioDao(IUsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}


	public ICategoriaDAO getCategoriaDao() {
		return categoriaDao;
	}


	public void setCategoriaDao(ICategoriaDAO categoriaDao) {
		this.categoriaDao = categoriaDao;
	}


	public PersistenceManagerFactory getPmf() {
		return pmf;
	}


	public void setPmf(PersistenceManagerFactory pmf) {
		this.pmf = pmf;
	}


	public boolean logIn(String email, String contrasena) {	
		try {
			Usuario u= usuarioDao.getUsuario(email);
			if (u!=null) {
				if (u.getPassword().contentEquals(contrasena)) {
					return true;
				}
			}
			return false;
		}catch(JDOUserException exception) {
			return false;
		}
		
	}
	
	public List<Categoria> getCategorias(){
		return categoriaDao.getCategorias();
	}
	
	public boolean registro(Usuario u) {
		if (usuarioDao.getUsuario(u.getEmail())==null) {
			usuarioDao.storeUsuario(u);
			return true;
		}
		System.out.println("*No se ha realizado el registro*");
		return false;	
	}
	
	/**
	 * compra un producto, si este existe
	 * @param email
	 * @param idProducto
	 * @param precio
	 */
	public void comprarProducto(String email, int idProducto, double precio) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		Producto p=null;
		try {	
			u=pm.getObjectById(Usuario.class, email);
			p=pm.getObjectById(Producto.class, idProducto);
			Compra c= new Compra();
			c.setProducto(p.getId());
			c.setPrecio(precio);
			if (p instanceof ProductoOrdenador) {
				c.setIdCat(1);
			}else {
				c.setId(2);
			}
			u.getCompras().add(c);
			//uVendedor.getProductosVendidos().add(p);
			p.setVendido(true);
		}catch(Exception e){
			if (u==null) {
				System.out.println("Error al realizar la compra no existe ese usuario");
			}else {
				System.out.println("Error al realizar la compra no existe producto");
				System.out.println(e.getMessage());
			}
		}finally {
			pm.close();
		}
	}
	

	/**
	 * Anade productos a favoritos
	 * @param id
	 * @param email
	 * @exception si no se puede anadir a favoritos
	 */
	public void addProductoFav(int id, String email) {
		// TODO Auto-generated method stub
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		try {
			u=pm.getObjectById(Usuario.class, email);
			Producto p=pm.getObjectById(Producto.class,id);
			u.getProductosFavoritos().add(p);
			//tengo que meter en una lista todos 
		}catch(Exception e){
			if (u==null) {
				System.out.println("Error al seleccionar el producto para guardarlo en FAV");
			}else {
				System.out.println("Error al guardar el producto como FAV");
			}
		}finally {
			pm.close();
		}
		
	}
	

	
	/**
	 * Anade el ususario a favorito
	 * @param email2
	 * @param email
	 * @exception error si no te deja añadir el usuario a favoritos
	 */
	public void addUsuarioFav(String email2 , String email) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u1 = null;
		Usuario u2 = null;
		try {
			u1 = pm.getObjectById(Usuario.class, email);
			u2 = pm.getObjectById(Usuario.class, email2);
			u1.getVendedoresLike().add(u2);
		}catch(Exception e) {
			if(u1==null) {
				System.out.println("Error al seleccionar el usuario para guardarlo en los usuarios que te gustan");
			}else {
				System.out.println("Error al guardar el usuario como me gusta");
			}
		}
	}
	
	/**
	 * envia un mensaje
	 * @param email1
	 * @param email2
	 * @param contenido
	 * @param fecha
	 * @exception si el mensaje no se envia o el mensaje no se recibe
	 */
	public void enviarMensaje(String email1, String email2, String contenido, long fecha) {
		PersistenceManager pm = pmf.getPersistenceManager();
		System.out.println(email1);
		System.out.println(email2);
		Usuario u1 = null;
		Usuario u2 = null;
		try {
			u1=pm.getObjectById(Usuario.class, email1);
			u2=pm.getObjectById(Usuario.class, email2);
			System.out.println("ENTRO");
			Mensaje m = new Mensaje();
			m.setContenido(contenido);
			m.setEnviado(email1);
			m.setRecibido(email2);
			m.setFecha(fecha);
			u1.getMensajesEnviados().add(m);
			u2.getMensajesRecibidos().add(m);
		}catch(Exception e) {
			if(u1== null) {
				System.out.println("Error al enviar mensaje, el usuario emisor no existe");
			}
			else if(u2==null) {
				System.out.println("Error al enviar mensaje, el usuario receptor no existe");
			}
		}finally {
			pm.close();
		}
	}
	
	/**
	 * para reservar un producto
	 * @param idProducto
	 * @exception si el producto no existe
	 */
	public void reservar(int idProducto) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Producto p = null;
		try {
			p= pm.getObjectById(Producto.class,idProducto);
			p.setReservado(true);
		}catch(Exception e) {
			System.out.println("El producto no existe");
		}
	}
	/**
	 * quitar las reservas realizadas
	 * @param idProducto
	 * @exception si el producto no existe
	 */
	public void desReservar(int idProducto) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Producto p = null;
		try {
			p= pm.getObjectById(Producto.class,idProducto);
			p.setReservado(false);
		}catch(Exception e) {
			System.out.println("El producto no existe");
		}
	}
	
	/**
	 * poner a la venta un producto
	 * @param email
	 * @param p
	 * @exception si al realizar la compra no existe el usuario
	 */
	public void ponerALaVenta(String email, Producto p) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, email);
			u.getProductos().add(p);
		}catch(Exception e){
				System.out.println("Error al realizar la compra no existe ese usuario");
		}finally {
			pm.close();
		}
	}


	/**
	 * coger un producto
	 * @param x
	 * @return
	 */
	public Producto getProducto(int x) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Producto p=null;
		try {
			p=pm.getObjectById(Producto.class,x);
		}catch(Exception e) {
			System.out.println("* Error el producto no existe *");
		}finally {
			pm.close();
		}
		return p;
		
	}
	

	/**
	 * anade productos a la lista
	 * @return
	 */
	public List<Producto> getProductos() {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<Producto> p=new ArrayList<Producto>();
		try {
			p=productDao.getProductos();
		}catch(Exception e) {
			System.out.println("* Error el producto no existe *");
		}finally {
			pm.close();
		}
		return p;
		
	}
	
	/**
	 * anade a la lista los productos ordenador
	 * @exception si el producto no existe
	 * @return
	 */
	public List<ProductoOrdenador> getProductosOrdenador() {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoOrdenador> p=new ArrayList<ProductoOrdenador>();
		try {
			p=productDao.getProductosOrdenador();
		}catch(Exception e) {
			System.out.println("* Error el producto no existe *S");
		}finally {
			pm.close();
		}
		return p;
		
	}
	
	
	/**
	 * anade a la lista los productos ordenador en venta
	 * 
	 * @exception si el producto no existe
	 * @return
	 */
	public List<ProductoOrdenador> getProductosOrdenadorEnVenta() {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoOrdenador> p=new ArrayList<ProductoOrdenador>();
		try {
			p=productDao.getProductosOrdenadorEnVenta();
			for (ProductoOrdenador prod:p) {
				System.out.println(prod.getNombre());
			}
		}catch(Exception e) {
			System.out.println("* Error el producto no existe *S");
		}finally {
			pm.close();
		}
		return p;
		
	}
	
	/**
	 * coje los productos ordenador que estan en venta y reservados
	 * 
	 * @exception si el producto no existe
	 * @return
	 */
	public List<ProductoOrdenador> getProductosOrdenadorEnVentaConReservado() {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoOrdenador> p=new ArrayList<ProductoOrdenador>();
		try {
			p=productDao.getProductosOrdenadorEnVentaConReservado();
			for (ProductoOrdenador prod:p) {
				System.out.println(prod.getNombre());
			}
		}catch(Exception e) {
			System.out.println("* Error el producto no existe *S");
		}finally {
			pm.close();
		}
		return p;
		
	}
	
	
	/**
	 * anade los productos tipo vehiculo
	 * @exception si el producto no existe
	 * @return
	 */
	public List<ProductoVehiculo> getProductosVehiculos() {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoVehiculo> p=new ArrayList<ProductoVehiculo>();
		try {
			p=productDao.getProductosVehiculos();
		}catch(Exception e) {
			System.out.println("* Error el producto no existe *S");
		}finally {
			pm.close();
		}
		return p;
		
	}
	
	/**
	 * coje los productos vehiculo en venta
	 * @exception si el producto no existe
	 * @return
	 */
	public List<ProductoVehiculo> getProductosVehiculosEnVenta() {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoVehiculo> p=new ArrayList<ProductoVehiculo>();
		try {
			p=productDao.getProductosVehiculosEnVenta();
		}catch(Exception e) {
			System.out.println("* Error el producto no existe *S");
		}finally {
			pm.close();
		}
		return p;
		
	}
	
	/**
	 * coje los productos en vehiculo en venta y reservados
	 * @exception si el producto no existe
	 * @return
	 */
	public List<ProductoVehiculo> getProductosVehiculosEnVentaConReservado() {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoVehiculo> p=new ArrayList<ProductoVehiculo>();
		try {
			p=productDao.getProductosVehiculosEnVentaConReservado();
		}catch(Exception e) {
			System.out.println("* Error el producto no existe *S");
		}finally {
			pm.close();
		}
		return p;
		
	}
	
	/** coje los usuarios con el email
	 * @param email
	 * @return
	 */
	public Usuario getUsuario(String email) {
		return usuarioDao.getUsuario(email);
	}

	


	/**
	 * coje los productos ordenador que tienes en favoritos
	 * @param x
	 * @return
	 * @exception si el usuario no existe al realizar la compra
	 */
	public List<ProductoOrdenador> getProductosOrdenadorFavoritos(String x) {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoOrdenador>po= new ArrayList<ProductoOrdenador>();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			for(Producto p:u.getProductosFavoritos()) {
				if (p instanceof ProductoOrdenador) {
					ProductoOrdenador prod=(ProductoOrdenador)p;
					po.add(prod);
				}
			}
		}catch(Exception e){
				System.out.println("Error al realizar la compra no existe ese usuario");
		}finally {
			pm.close();
		}
		return po;
	}
	
	
	/**
	 * coje los productos tipo vehiculo anadidos a favoritos
	 * @param x
	 * @return
	 * @exception si al realizar la compra el usuario no existe
	 */
	public List<ProductoVehiculo> getProductosVehiculoFavoritos(String x) {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoVehiculo>po= new ArrayList<ProductoVehiculo>();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			for(Producto p:u.getProductosFavoritos()) {
				if (p instanceof ProductoVehiculo) {
					ProductoVehiculo prod=(ProductoVehiculo)p;
					po.add(prod);
				}
			}
		}catch(Exception e){
				System.out.println("Error al realizar la compra no existe ese usuario");
		}finally {
			pm.close();
		}
		return po;
	}


	/**
	 * cojer el numero de ventas relizadas
	 * @param x
	 * @return
	 */
	public int getNumVentas(String x) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		int contador=0;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			for(Producto p:u.getProductos()) {
				System.out.println(p + "Es producto");
				if (p.isVendido()) {
					contador++;
				}
			}
		}catch(Exception e){
				System.out.println("Error al realizar la compra no existe ese usuario");
		}finally {
			pm.close();
		}
		return contador;
	}


	/**
	 * cojer los mensajes enviados
	 * @param x
	 * @return
	 */
	public List<Mensaje> getMensajesEnviados(String x) {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<Mensaje>men= new ArrayList<Mensaje>();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			men= u.getMensajesEnviados();
		}catch(Exception e){
				System.out.println("Error al realizar la compra no existe ese usuario");
		}finally {
			pm.close();
		}
		return men;
	}
	
	/**
	 * cojer los mensajes que se han recibido
	 * @param x
	 * @return
	 */
	public List<Mensaje> getMensajesRecibidos(String x) {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<Mensaje>men= new ArrayList<Mensaje>();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			men= u.getMensajesRecibidos();
		}catch(Exception e){
				System.out.println("Error al realizar la compra no existe ese usuario");
		}finally {
			pm.close();
		}
		return men;
	}

	
	/**
	 * coje los productos ordenador vendidos
	 * @param x
	 * @return
	 */
	public List<ProductoOrdenador> getProductosOrdenadorVendidos(String x) {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoOrdenador>po= new ArrayList<ProductoOrdenador>();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			for(Producto p:u.getProductos()) {
				if (p instanceof ProductoOrdenador) {
					if(p.isVendido()) {
						ProductoOrdenador prod=(ProductoOrdenador)p;
						po.add(prod);
					}
				}
			}
		}catch(Exception e){
				System.out.println("Error al realizar la compra no existe ese usuario");
		}finally {
			pm.close();
		}
		return po;
	}
	
	/*public List<Producto> getProductosFavoritos(String x) {
	PersistenceManager pm=pmf.getPersistenceManager();
	List<Producto>p= new ArrayList<Producto>();
	Usuario u= null;
	try {	
		u=pm.getObjectById(Usuario.class, x);
		for(Producto pr:u.getProductosFavoritos()) {
			Producto prod=(Producto)p;
			p.add(prod);
		}
	}catch(Exception e){
			System.out.println("Error! ese usuario no tiene favoritos");
	}finally {
		pm.close();
	}
	return p;
}*/
	/** 
	 * @param x
	 * @return  cuenta los productos que hay en favoritos
	 */
	public int getProductosFavoritos(int x){
		PersistenceManager pm=pmf.getPersistenceManager();
		int contador = 0;
		Producto p= null;
		try {
			p=pm.getObjectById(Producto.class, x);
			List<Usuario> usuarios = usuarioDao.getUsuarios();
			for(Usuario u: usuarios) {
				for(Producto p2: u.getProductosFavoritos()) {
					if(p2.getId()==p.getId()) {
						contador++;
					}
				}
			}
		}catch(Exception e){
			System.out.println("Error! ese usuario no tiene favoritos");
		}finally {
			pm.close();
		}
		return contador;

	}

	/**
	 * @param x
	 * @return anade los productos vehiculos vendidos 
	 */
	public List<ProductoVehiculo> getProductosVehiculoVendidos(String x) {
		PersistenceManager pm=pmf.getPersistenceManager();
		List<ProductoVehiculo>po= new ArrayList<ProductoVehiculo>();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			for(Producto p:u.getProductos()) {
				if (p instanceof ProductoVehiculo) {
					if(p.isVendido()) {
						ProductoVehiculo prod=(ProductoVehiculo)p;
						po.add(prod);
					}
				}
			}
		}catch(Exception e){
				System.out.println("Error al realizar la compra no existe ese usuario");
		}finally {
			pm.close();
		}
		return po;
	}


	/**
	 * hace una oferta por un producto
	 * @param x
	 * @param idProd
	 * @param cantidad
	 */
	public void hacerOferta(String x, int idProd, double cantidad) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		Producto p=null;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			p=pm.getObjectById(Producto.class, idProd);
			Oferta o= new Oferta();
			o.setCantidad(cantidad);
			o.setEmailEmisor(x);
			o.setFecha(System.currentTimeMillis());
			u.getOfertasEnviadas().add(o);
			p.getOfertasRecibidas().add(o);
			//uVendedor.getProductosVendidos().add(p);
		}catch(Exception e){
			if (u==null) {
				System.out.println("Error al realizar la oferta no existe ese usuario");
			}else {
				System.out.println("Error al realizar la oferta no existe producto");
				System.out.println(e.getMessage());
			}
		}finally {
			pm.close();
		}
		
	}
	/** crear una reclamacion
	 * @param r
	 */
	public void addReclamacion(Reclamacion r, int idCompra) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		Compra c= null;
		try {	
			c=pm.getObjectById(Compra.class,idCompra);
			c.setReclamado(true);
			u=pm.getObjectById(Usuario.class, r.getEmailComprador());
			u.getReclamacionesHechas().add(r);
		}catch(Exception e){
				e.printStackTrace();
				System.out.println("Error al realizar la Reclamacion no existe ese usuario");
		}finally {
			pm.close();
		}
	}


	/**
	 * @param email
	 * @return las compras realizadas por cada usuario
	 */
	public List<Compra> getCompras(String email) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		List<Compra>compras= new ArrayList<Compra>();
		try {	
			u=pm.getObjectById(Usuario.class, email);
			return u.getCompras();
		}catch(Exception e){
				System.out.println("Error al realizar la Reclamacion no existe ese usuario");
		}finally {
			pm.close();
		}
		return compras;
	}


	/**
	 * Denunciar a un usuario
	 * @param x
	 */
	public void denunciar(String x) {
		// TODO Auto-generated method stub
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, x);
			u.setDenuncias(u.getDenuncias()+1);
		}catch(Exception e){
				System.out.println("Error al denunciar al usuario");
		}finally {
			pm.close();
		}
	}
}
