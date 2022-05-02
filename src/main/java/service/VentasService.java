package service;



import javax.jdo.JDOHelper;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.omg.CORBA.UserException;

import dao.*;
import serialization.*;
import java.util.*;


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
	
	public void comprarProducto(String email, int idProducto, double precio) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		Producto p=null;
		Usuario uVendedor=null;
		try {	
			u=pm.getObjectById(Usuario.class, email);
			p=pm.getObjectById(Producto.class, idProducto);
			uVendedor=pm.getObjectById(Usuario.class,email);
			Compra c= new Compra();
			c.setProducto(p);
			c.setPrecio(precio);
			u.getCompras().add(c);
			//uVendedor.getProductosVendidos().add(p);
			p.setVendido(true);
		}catch(Exception e){
			if (u==null) {
				System.out.println("Error al realizar la compra no existe ese usuario");
			}else {
				System.out.println("Error al realizar la compra no existe producto");
			}
		}finally {
			pm.close();
		}
	}
	
<<<<<<< HEAD
	public void addProductoFav(int id, String email) {
		// TODO Auto-generated method stub
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		try {
			u=pm.getObjectById(Usuario.class, email);
			Producto p=pm.getObjectById(Producto.class,id);
=======
	public void addProductoFav(Producto p, String email) {
		// TODO Auto-generated method stub
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
	//	Producto p=null;
		try {
			u=pm.getObjectById(Usuario.class, email);
			//p=pm.getObjectById(Producto.class, id);
>>>>>>> e2ffe0e9a047b5059e1aaac2724afd99ea816553
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
			else {
				System.out.println("Error al enviar el mensaje");
			}
		}finally {
			pm.close();
		}
	}
	
	
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
	
	public Usuario getUsuario(String email) {
		return usuarioDao.getUsuario(email);
	}

<<<<<<< HEAD

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
=======
	
>>>>>>> e2ffe0e9a047b5059e1aaac2724afd99ea816553
	
	
	
	
	
	

}
