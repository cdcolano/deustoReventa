package client.main;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import client.controller.ChatController;
import client.gui.VentanaLogin;
import dao.UsuarioDAO;
import serialization.Mensaje;
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
	}
}
