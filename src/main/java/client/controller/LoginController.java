package client.controller;



import javax.swing.JLabel;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.gui.VentanaLogin;
import serialization.Usuario;
import util.ReventaException;

public class LoginController {
	private WebTarget webTarget;
	
	public boolean logIn(String email, String password, JLabel lError, VentanaLogin vl) throws ReventaException {
		try {
			WebTarget webTarget=this.webTarget.path("/reventa/logIn");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Usuario u=new Usuario();
			u.setEmail(email);
			u.setPassword(password);
			System.out.println(webTarget.getUri());
			Response response = invocationBuilder.post(Entity.entity(u, MediaType.APPLICATION_JSON));

			if (response.getStatus() != Status.OK.getStatusCode()) {
				throw new ReventaException("" + response.getStatus());
			}
			boolean logIn=response.readEntity(boolean.class);
			if (!logIn) {
				lError.setText("Email o contraseña incorrectos");
				vl.setLocationRelativeTo(null);
				vl.pack();
				
			}
			return logIn;
		}catch(Exception e) {
			lError.setText("Algo ha fallado al realizar el LogIn");
			System.out.println("* Error " + e.getMessage() +"*");
			vl.pack();
			vl.setLocationRelativeTo(null);
			return false;
		}
	}
	public Usuario getUsuario(String email)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("collector/getUsuario/"+ email);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			Usuario u = response.readEntity(Usuario.class);
			return u;
		} else {
			throw new ReventaException("" + response.getStatus());
		}
	}
}
