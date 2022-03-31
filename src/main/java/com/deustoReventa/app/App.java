package com.deustoReventa.app;

import java.util.Date;

import dao.ICategoriaDAO;
import dao.IProductoDAO;
import dao.IUsuarioDAO;
import dao.ProductoDAO;
import dao.UsuarioDAO;
import deustoReventa.serialization.Categoria;
import deustoReventa.serialization.Producto;
import deustoReventa.serialization.Usuario;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Usuario u = new Usuario();
        u.setEmail("jon.egiluz@mecomeElPepiono.es");
        u.setPassword("j");
        
        Categoria c= new Categoria();
        c.setId(1);
        c.setNombre("vehiculos");
        
        
        Producto p= new Producto();
        p.setId(1);
        p.setCategoria(c);
        p.setFechaPubli(new Date(System.currentTimeMillis()));
        p.setNombre("Golf");
        p.setPrecioSalida(3000);
        p.setReservado(false);
        p.setVendedor(u);
        
        IProductoDAO prod= new ProductoDAO();
        IUsuarioDAO iUsuari= new UsuarioDAO();
        ICategoriaDAO iCategoria= new CategoriaDAO();
        
        
        
    }
    public void MetodoPrueba() {
    	System.out.println("prueba metodo en branch");
    }
}
