package client.controller;

import java.util.ArrayList;

import javax.ws.rs.client.WebTarget;

import serialization.Mensaje;
import serialization.Usuario;

public class MostrarChatsController {
	private WebTarget webTarget;
	private String email;
	
	public MostrarChatsController(WebTarget webTarget, String email) {
		super();
		this.webTarget = webTarget;
		this.email = email;
	}
}
