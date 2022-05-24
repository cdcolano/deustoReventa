package client.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.IntegrationTest;
import dao.UsuarioDAO;
import serialization.Categoria;
import serialization.Mensaje;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Reclamacion;
import serialization.Usuario;
import service.VentasService;
import util.ReventaException;

@Category(IntegrationTest.class)
public class IntegrationTestController {


	

	    
	    @Test
	    public void testIntegration() throws Exception {
	    	String hostname="localhost";
	    	String port="8080";
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
			assertEquals(cc.getMensajesEnviados("u@gmail.com").size(), 3);
			assertEquals(cc.getMensajesRecibidos("u@gmail.com").size(), 1);
			
			LoginController lc= new LoginController(c, wt);
			try {
				assertTrue(lc.logIn(email, "u", null, null));
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
				pv.setEmailVendedor("u@gmail.com");
				pv.setFechaPubli(System.currentTimeMillis());
				pv.setMarca("Audi");
				pv.setModelo("tt");
				pv.setNombre("Audi tt");
				pv.setPrecioSalida(200000);
				pc.addProductoVehiculo(pv);
				pv.setId(21);
				
				ComprasController comprasController= new ComprasController(wt, email);
				comprasController.anadirFav(pv, email);
				comprasController.anadirUsuarioFav("a@gmail.com", email);
				comprasController.comprar(email, 1, 100);
				comprasController.getProductos();
				List<Producto>productos=comprasController.getProductosEnVenta();
				assertEquals(productos.size(),5);
				List<Producto>productosFavoritos=comprasController.getProductosFavoritos();
				assertEquals(productosFavoritos.size(), 1);
				int ventas=comprasController.getVentas(email);
				assertEquals(ventas, 1);
				List<ProductoOrdenador>productosOrdenador= comprasController.getProductosOrdenador();
				assertEquals(productosOrdenador.size(), 2);
				List<ProductoVehiculo>productosVehiculo=comprasController.getProductosVehiculo();
				assertEquals(productosVehiculo.size(), 3);
				
				MisProductosController mps= new MisProductosController(wt, email);
				List<Producto>prods= mps.getProductosEnVentaConReservado();
				assertEquals(prods.size(), 5);
				VentasService vs= new VentasService();
				mps.reservar(21);
				assertTrue(vs.getProducto(21).isReservado());
				mps.desReservar(21);
				OfertaController ofc= new OfertaController(wt, email, 21);
				ofc.addOferta(email, 21, 100);
				assertEquals(vs.getUsuario(email).getOfertasEnviadas().size(),2);
				VerComprasController vcc= new VerComprasController(wt, email);
				vcc.addReclamacion(new Reclamacion("No me ha llegado", 100),1);
				vcc.denunciar(pv);
				
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
			List<Producto>prodVen=vc.getListaProductosVendidos(email);
		//	assertEquals(prodVen.size(), 1);
			
	    }

	    
	    
}
