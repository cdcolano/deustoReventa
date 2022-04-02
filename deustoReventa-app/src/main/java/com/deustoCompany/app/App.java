package com.deustoCompany.app;

import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.datanucleus.store.fieldmanager.MakeTransientFieldManager;

import dao.CategoriaDAO;
import dao.ICategoriaDAO;
import dao.IProductoDAO;
import dao.IUsuarioDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import serialization.Categoria;
import serialization.Compra;
import serialization.Producto;
import serialization.Usuario;
import service.VentasService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	/*
	    VentasService v= new VentasService();
        IProductoDAO prod= new ProductoDAO();
        IUsuarioDAO iUsuari= new UsuarioDAO();
        ICategoriaDAO iCategoria= new CategoriaDAO();
        
       /*System.out.println("prueba metodo en branch");
        Usuario u = new Usuario();
        u.setEmail("jon.egiluz@mecomeElPepiono.es");
        u.setPassword("j");
        
        iUsuari.storeUsuario(u);
        */Categoria c= new Categoria();
        c.setId(1);
        c.setNombre("vehiculos");
       // Usuario u= iUsuari.getUsuario("jon.egiluz@mecomeElPepiono.es");
        PersistenceManagerFactory pmf=JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        PersistenceManager pm = pmf.getPersistenceManager();
        Usuario u=pm.getObjectById(Usuario.class,"jon.egiluz@mecomeElPepiono.es");
       // pm.makeTransient(u);
       // System.out.println(u.getEmail());
        
       /* Producto p= new Producto();
        p.setId(1);
        p.setCategoria(c);
        p.setFechaPubli(new Date(System.currentTimeMillis()));
        p.setNombre("Golf");
        p.setPrecioSalida(3000);
        p.setReservado(false);
        p.setVendedor(u);
        
        
        Compra com= new Compra();
        com.setComprador(u);
        com.setId(0);
        com.setPrecio(2);
        com.setProducto(p);
        
        //u.getProductos().add(p);
        u.getCompras().add(com);
        */
        Usuario u2=pm.getObjectById(Usuario.class,"jon.egiluz@mecomeElPepiono.es");
        u2.setDenuncias(10);
        
        //Usuario temporal=u.clone();
        
       // iUsuari.updateUsuario(u, com);
        
        
  //      iUsuari.storeUsuario(temporal);
    //    PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
       // PersistenceManager pm = pmf.getPersistenceManager();
       // pm.makeTransient(u);
        
        //iUsuari.updateVentasUsuario(u.getEmail(), p);
        
        
        
       // u.addProductoEnVenta(p);
     
        
      //  iCategoria.deleteAllCategorias();
      //  prod.deleteAllProductos();
      // ;
      //  iUsuari.deleteAllUsuarios();
     //   iUsuari.storeUsuario(u);
     //   */
        /*MetodoPrueba(v, iUsuari, c);*/
        
      /*  Usuario u=iUsuari.getUsuario("jon.egiluz@mecomeElPepiono.es");
        for(Producto p:u.getProductosEnVenta()) {
        	System.out.println(p.getId());
        }
        */
        
        
    }
    public static void MetodoPrueba(VentasService v, IUsuarioDAO iUsuari, Categoria c) {
    	Usuario u;
        
      
        u=iUsuari.getUsuario("jon.egiluz@mecomeElPepiono.es");
        
        Producto p2= new Producto();
        p2.setId(2);
        p2.setFechaPubli(new Date(System.currentTimeMillis()));
        p2.setNombre("Golf");
        p2.setPrecioSalida(3000);
        p2.setReservado(false);
        p2.setVendedor(u);
       
       // Usuario u2=iUsuari.getUsuario("jon.egiluz@mecomeElPepiono.es");
       // prod.storeProducto(p2);
       
      //  v.comprarProducto(u, p2, 100);
      //  iCategoria.storeCategoria(c);
        
        
    }
}
