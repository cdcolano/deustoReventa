package client.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import client.controller.ComprasController;
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
	private List<Oferta> ofertas;
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
			 ofertas = new ArrayList<Oferta>();
			 for(Producto p : productos) {
				 for(Oferta o : p.getOfertasRecibidas()) {
					 ofertas.add(o);
				 }
			 }
			 controller.setProductos(productos);
			 controller.setOfertas(ofertas);
			
			for (Producto p:productos) {
				System.out.println(p.getOfertasRecibidas());
				if(p.getEmailVendedor().equals(email)) {
					controller.crearPanel(p, pCentro,this);
				}
			}
			
		} catch (ReventaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JScrollPane jsc= new JScrollPane(pCentro);
		JButton bCerrar = new JButton("Volver atras");
		bCerrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				v1.dispose();
				VentanaCompras v = new VentanaCompras(new ComprasController(webTarget, email),VentanaOfertas.this.client,VentanaOfertas.this.webTarget,email);
			}
		});
		JPanel pSur = new JPanel();
		pSur.add(bCerrar);
		v1.getContentPane().add(jsc,BorderLayout.CENTER);
		v1.getContentPane().add(pSur, BorderLayout.SOUTH);
		v1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		v1.pack();
		v1.setVisible(true);
	}	
}
