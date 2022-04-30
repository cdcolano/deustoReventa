package service;



import java.util.ArrayList;
import java.util.Date;

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
		try {	
			u=pm.getObjectById(Usuario.class, email);
			p=pm.getObjectById(Producto.class, idProducto);
			Compra c= new Compra();
			c.setProducto(p);
			c.setPrecio(precio);
			u.getCompras().add(c);
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
	
	public void enviarMensaje(String email1, String email2, String contenido, long fecha) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Usuario u1 = null;
		Usuario u2 = null;
		try {
			u1=pm.getObjectById(Usuario.class, email1);
			u2=pm.getObjectById(Usuario.class, email2);
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
	
	
	
	
	
	

}
