package server;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import serialization.Categoria;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Tarjeta;
import serialization.Usuario;
import service.VentasService;

/**
 * 
 * clase que añade datos de prueba
 * @author usuario
 *
 */
public class MainServer{
	
	public static void main(String []args) {
		VentasService vs= new VentasService();
		Categoria c1= new Categoria();
		c1.setId(1);
		c1.setNombre("ordenadores");
		
		Categoria c2= new Categoria();
		c2.setId(2);
		c2.setNombre("vehiculos");
		
		vs.getCategoriaDao().storeCategoria(c1);
		vs.getCategoriaDao().storeCategoria(c2);
		
		
		Tarjeta t1= new Tarjeta();
		t1.setAnyoVencimiento(2023);
		t1.setMesVencimiento(5);
		t1.setCodigoSecreto(279);
		t1.setNombre("6739M");
		Usuario u1=new Usuario();
		u1.setEmail("u@gmail.com");
		u1.setPassword("u");
		u1.setTarjeta(t1);
		vs.registro(u1);
		
		
		Tarjeta t2= new Tarjeta();
		t2.setAnyoVencimiento(2024);
		t2.setMesVencimiento(5);
		t2.setCodigoSecreto(270);
		t2.setNombre("6839M");
		Usuario u2=new Usuario();
		u2.setEmail("a@gmail.com");
		u2.setPassword("a");
		u2.setTarjeta(t2);
		vs.registro(u2);
		
		List<Categoria>cat=vs.getCategorias();
		
		ProductoOrdenador p1= new ProductoOrdenador();
		p1.setPrecioSalida(400);
		p1.setNombre("acer");
		p1.setCategoria(cat.get(0));
		p1.setCpu("i5");
		p1.setPlacaBase("p1");
		p1.setGrafica("gtx");
		p1.setRam(8);
		p1.setMemoria(256);
		p1.setFechaPubli(3000);
		
		ProductoVehiculo p2=new ProductoVehiculo();
		p2.setPrecioSalida(10000);
		p2.setNombre("golf");
		p2.setCategoria(cat.get(1));
		p2.setFechaPubli(2000);
		
		ProductoOrdenador p3= new ProductoOrdenador();
		p3.setPrecioSalida(200);
		p3.setNombre("hp");
		p3.setCpu("i7");
		p3.setPlacaBase("p2");
		p3.setGrafica("gtx 630");
		p3.setRam(16);
		p3.setMemoria(512);
		p3.setCategoria(cat.get(0));
		p3.setFechaPubli(1000);
		
		ProductoVehiculo p4= new ProductoVehiculo();
		p4.setPrecioSalida(12000);
		p4.setNombre("ibiza");
		p4.setCategoria(cat.get(1));
		p4.setFechaPubli(500);
		
		
		p1.setEmailVendedor(u1.getEmail());
		p2.setEmailVendedor(u1.getEmail());
		p3.setEmailVendedor(u2.getEmail());
		p4.setEmailVendedor(u2.getEmail());
		
		vs.ponerALaVenta(u1.getEmail(), p1);
		vs.ponerALaVenta(u1.getEmail(), p2);
		vs.ponerALaVenta(u2.getEmail(), p3);
		vs.ponerALaVenta(u2.getEmail(), p4);
		
		vs.enviarMensaje(u1.getEmail(), u2.getEmail(), "hola", System.currentTimeMillis());
		vs.enviarMensaje(u2.getEmail(), u1.getEmail(), "hola", System.currentTimeMillis());
		vs.enviarMensaje(u1.getEmail(), u2.getEmail(), "que tal?", System.currentTimeMillis());
		
	}
}
