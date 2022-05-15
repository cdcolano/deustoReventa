package client.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import client.controller.MisProductosController;
import client.controller.OfertasController;
import serialization.Oferta;
import serialization.Producto;
import serialization.Usuario;
import util.ReventaException;

public class VentanaOfertas extends JFrame {
	private Client client;
	private WebTarget webTarget;
	private String email;
	private List<Producto> productos;
	private List<Usuario> usuarios;
	
	private OfertasController controller;
	private static VentanaOfertas v1;
	private JPanel pNorte;
	private JPanel pCentro;
	
	public VentanaOfertas(OfertasController oc, Client cliente, WebTarget webTarget, String email) {
		this.controller= oc;
		this.webTarget = webTarget;
		this.email = email;
		
		v1 = this;
		pCentro= new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		
		try {
			 productos=controller.getProductosEnVentaConReservado();
			 controller.setProductos(productos);
			
			for (Producto p:productos) {
				System.out.println(p.getOfertasRecibidas());
				if(p.getEmailVendedor().equals(email)) {
					controller.crearPanel(p, pCentro);
				}
			}
			
		} catch (ReventaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JScrollPane jsc= new JScrollPane(pCentro);
		
		v1.getContentPane().add(jsc,BorderLayout.CENTER);
		v1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		v1.pack();
		v1.setVisible(true);
	}	
}
