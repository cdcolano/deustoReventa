package client.controller;



import javax.swing.JLabel;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.gui.VentanaLogin;
import serialization.Usuario;
import util.ReventaException;

/**Clase que gestiona la logica de la ventana de login
 * @author Carlos
 *
 */
public class LoginController {
	private WebTarget webTarget;
	private Client cliente;
	
	public LoginController(Client cliente, WebTarget webTarget) {
		super();
		this.cliente = cliente;
		this.webTarget = webTarget;
	}
	
	/**Realiza el login de un usuario
	 * @param email del usuario que realiza el login
	 * @param password contrasena introducida por el usuario
	 * @param lError label en el que se mostrara el mensaje de error
	 * @param vl ventana de login
	 * @return true si el login es correcto, false en caso contrario
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
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
				lError.setText("Email o contrasena incorrectos");
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
	
	/**Devuelve un usuario dado su email
	 * @param email del usuario a devolver
	 * @return Usuario con el email introducido
	 * @throws ReventaException excepcion lanzada cuando no se puede conectar con el servidor
	 */
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
