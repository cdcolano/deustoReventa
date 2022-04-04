package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;


public class MainCliente {
	
	public static void main(String[]args) {

		String hostname = args[0];
		String port = args[1];
		
		Client c= ClientBuilder.newClient();
		WebTarget wt=c.target(String.format("http://%s:%s/rest", hostname, port));
		VentanaLogin vli= new VentanaLogin(c, wt);
		//VentanaProducto vp= new VentanaProducto(c, wt, "jon.egiluz@mecomeElPepiono.es");
	}
	
}
