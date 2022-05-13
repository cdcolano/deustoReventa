package client.main;

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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.controller.ChatController;
import client.controller.ComprasController;
import client.controller.LoginController;
import client.controller.OfertaController;
import client.controller.ProductoController;
import client.controller.RegistroController;
import client.controller.VentasController;
import client.gui.VentanaLogin;
import client.gui.VentanaOferta;
import dao.UsuarioDAO;
import serialization.Categoria;
import serialization.Compra;
import serialization.Mensaje;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import util.ReventaException;

public class IntegrationTest {
	
	public static void main(String[]args) throws ReventaException {
		String hostname = args[0];
		String port = args[1];
		
		Client c= ClientBuilder.newClient();
		WebTarget wt=c.target(String.format("http://%s:%s/rest", hostname, port));
		String email="u@gmail.com";
		
		
		ChatController cc= new ChatController(wt, email, new UsuarioDAO());
		Mensaje m= new Mensaje();
		m.setContenido("hola buenas");
		m.setEnviado(email);
		m.setRecibido("a@gmail.com");
		m.setFecha(System.currentTimeMillis());
		m.setId(4);
		try {
			cc.enviar(email, "a@gmail.com", m);
		}catch(ReventaException ex) {
			ex.printStackTrace();
			throw ex;
		}
		cc.getMensajesEnviados("u@gmail.com");
		cc.getMensajesRecibidos("u@gmail.com");
		
		LoginController lc= new LoginController(c, wt);
		try {
			lc.logIn(email, "u", null, null);
		}catch(ReventaException r) {
			r.printStackTrace();
			throw r;
		}
		
		OfertaController of= new OfertaController(wt, email, 1);
		try {
			of.addOferta(email, 1, 100);
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		ProductoController pc= new ProductoController(wt, email);
		try {
			List<Categoria>cat=pc.getCategoria();
			ProductoOrdenador po= new ProductoOrdenador();
			po.setCategoria(cat.get(0));
			po.setCpu("i3");
			po.setEmailVendedor("u@gmail.com");
			po.setFechaPubli(System.currentTimeMillis());
			po.setGrafica("rtx");
			po.setMemoria(256);
			po.setNombre("asus pender");
			po.setPlacaBase("pb2");
			po.setRam(16);
			po.setPrecioSalida(400);
			pc.addProductoOrdenador(po);
			
			ProductoVehiculo pv= new ProductoVehiculo();
			pv.setAnyoFabri(2008);
			pv.setCaballos(255);
			pv.setCategoria(cat.get(1));
			pv.setKilometros(20000);
			po.setEmailVendedor("u@gmail.com");
			po.setFechaPubli(System.currentTimeMillis());
			pv.setMarca("Audi");
			pv.setModelo("tt");
			pv.setPrecioSalida(200000);
			pc.addProductoVehiculo(pv);
			
			ComprasController comprasController= new ComprasController(wt, email);
			comprasController.anadirFav(pv, email);
			comprasController.anadirUsuarioFav("a@gmail.com", email);
			comprasController.comprar(email, 1, 100);
			comprasController.getProductos();
			List<Producto>productos=comprasController.getProductosEnVenta();
			List<Producto>productosFavoritos=comprasController.getProductosFavoritos();
			int ventas=comprasController.getVentas(email);
			List<ProductoOrdenador>productosOrdenador= comprasController.getProductosOrdenador();
			List<ProductoVehiculo>productosvehiculo=comprasController.getProductosVehiculo();
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		RegistroController rc= new RegistroController(wt);
		Usuario u= new Usuario();
		u.setEmail("c@gmail.com");
		u.setPassword("c");
		try {
			rc.registrar(u);
		} catch (ReventaException e) {
			e.printStackTrace();
			throw e;
		}
		
		VentasController vc=new VentasController(wt, email);
		vc.getListaProductosVendidos(email);
		
	
		
	}
}
