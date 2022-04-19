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
			}else if(p==null) {
				System.out.println("Error al realizar la compra no existe producto");
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
			if (u==null) {
				System.out.println("Error al realizar la compra no existe ese usuario");
			}
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
			System.out.println("* Error el producto no existe *S");
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
