package client.main;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import client.controller.ChatController;
import client.controller.ComprasController;
import client.controller.LoginController;
import client.controller.OfertaController;
import client.controller.ProductoController;
import client.gui.VentanaLogin;
import dao.UsuarioDAO;
import serialization.Categoria;
import serialization.Mensaje;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import util.ReventaException;

public class IntegrationTest {
	
	public static void main(String[]args) {
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
		}
		cc.getMensajesEnviados("u@gmail.com");
		cc.getMensajesRecibidos("u@gmail.com");
		
		LoginController lc= new LoginController(c, wt);
		try {
			lc.logIn(email, "u", null, null);
		}catch(ReventaException r) {
			r.printStackTrace();
		}
		
		OfertaController of= new OfertaController(wt, email, 1);
		try {
			of.addOferta(email, 1, 100);
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (ReventaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
