package service;



import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import dao.*;
import serialization.*;



public class VentasService {
	IProductoDAO productDao;
	IUsuarioDAO	usuarioDao;
	
	PersistenceManagerFactory pmf;
	
	public VentasService() {
		productDao= new ProductoDAO();
		usuarioDao= new UsuarioDAO();
		pmf= JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
	}
	
	
	public boolean logIn(String email, String contrasena) {	
		Usuario u= usuarioDao.getUsuario(email);
		if (u.getPassword().contentEquals(contrasena)) {
			return true;
		}
		return false;
	}
	
	public void registro(Usuario u) {
		if (usuarioDao.getUsuario(u.getEmail())==null) {
			usuarioDao.storeUsuario(u);
		}
	}
	
	public void comprarProducto(String email, int idProducto, double precio) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		Producto p=null;
		try {	
			u=pm.getObjectById(Usuario.class, email);
			p=pm.getObjectById(Producto.class, idProducto);
			Compra c= new Compra();
			c.setComprador(u);
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
	
	
	public void ponerALaVenta(String email, String nombre, double precio, Categoria c) {
		PersistenceManager pm=pmf.getPersistenceManager();
		Usuario u= null;
		try {	
			u=pm.getObjectById(Usuario.class, email);
			Producto p=new Producto(u,precio,nombre, c);
			u.getProductos().add(p);
		}catch(Exception e){
			if (u==null) {
				System.out.println("Error al realizar la compra no existe ese usuario");
			}
		}finally {
			pm.close();
		}
	}
	
	
	
	
	
	

}
