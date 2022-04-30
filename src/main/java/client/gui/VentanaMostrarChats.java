package client.gui;

import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import client.controller.ComprasController;
import client.controller.MostrarChatsController;
import serialization.Usuario;

public class VentanaMostrarChats extends JFrame{
	private Client client;
	private WebTarget webTarget;
	private String email;
	private HashSet<Usuario> setUsuarios;
	private MostrarChatsController controller;
	
	public VentanaMostrarChats(Client cliente, WebTarget webTarget, String email) {
		this.client = cliente;
		this.webTarget = webTarget;
		this.email = email;
		this.controller= new MostrarChatsController(webTarget, email);
		
		JPanel pCentro= new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		DefaultListModel<String> l1 = new DefaultListModel<>();  
		this.setUsuarios = new HashSet<Usuario>();
		for(Usuario u : setUsuarios) {
			l1.addElement(u.getEmail());
		}
		JList lista = new JList(l1);
		pCentro.add(lista);
		JPanel pSur= new JPanel();
		JButton btnAbrir = new JButton("ABRIR");
		pSur.add(btnAbrir);
	}
	public static void main(String[]args) {
		
	}
}
