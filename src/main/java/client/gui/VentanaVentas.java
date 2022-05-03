package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import client.controller.VentasController;
import serialization.Producto;
import java.awt.BorderLayout;

public class VentanaVentas extends JFrame {
	
	private JButton btnCerrar;
	private JPanel pCentro,pSur;
	private Client client;
	private String em1;
	private WebTarget webTarget;
	private VentanaVentas v1;
	private DefaultListModel lm1;
	private VentasController vc1;
	private JList jProductos;

	
	
	public VentanaVentas(Client c, WebTarget wt, String email) {
		
		this.client=c;
		this.em1=email;
		this.webTarget=wt;
		vcl=new VentasController(wt, email);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		
		lm1 = new DefaultListModel();
		List<Producto> productoV = vc1.getListaProductosVendidos(email) ;
		
		for(Producto m: productoV) {
			Date date = new Date(m.getFechaPubli());
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
			String p = df.format(date);
			lm1.addElement(m.getNombre()+" - " +p+" - " +m.getPrecioSalida());
		}
		
		jProductos = new JList(lm1);
		
		
		pCentro = new JPanel();
		pSur = new JPanel();
		btnCerrar = new JButton("VOLVER ATRAS");
		pSur.add(btnCerrar);
		
		pCentro.add(jProductos);
		
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				v1.dispose();
				
			}
		});
		
		v1.add(pCentro,BorderLayout.CENTER);
		v1.add(pSur,BorderLayout.SOUTH);
		
		
		
		setVisible(true);

		

		
		
	}
	
	
	

}
