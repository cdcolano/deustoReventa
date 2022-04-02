package service;

import dao.*;
import serialization.*;



public class VentasService {
	IProductoDAO productDao;
	IUsuarioDAO	usuarioDao;
	
	public VentasService() {
		productDao= new ProductoDAO();
		usuarioDao= new UsuarioDAO();
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
	
/*	public void comprarProducto(Usuario u, Producto p, double precio) {
		Compra c= new Compra();
		c.setComprador(u);
		c.setProducto(p);
		c.setPrecio(precio);
		usuarioDao.updateUsuario(u);
		p.setVendido(true);
		productDao.updateProducto(p);
	}*/
	
	
	
	
	

}
