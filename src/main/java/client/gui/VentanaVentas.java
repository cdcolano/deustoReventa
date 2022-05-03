package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import java.awt.FlowLayout;
import client.controller.VentasController;
import serialization.Producto;
import java.awt.BorderLayout;

public class VentanaVentas extends JFrame {
	
	private JButton btnCerrar;
	private JPanel pCentro,pSur;
	private Client client;
	private String em1;
	private WebTarget webTarget;

	private VentasController vc1;

	
	
	public VentanaVentas(Client c, WebTarget wt, String email) {
		
		this.client=c;
		this.em1=email;
		this.webTarget=wt;
		vc1=new VentasController(wt, email);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		
		List<Producto> productoV= new ArrayList<Producto>();
		try {
			productoV = vc1.getListaProductosVendidos(email);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		pCentro = new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		
		for(Producto m: productoV) {
			JPanel pProducto= new JPanel();
			pProducto.setLayout(new BoxLayout(pProducto, BoxLayout.Y_AXIS));
			
			
			JPanel pDate= new JPanel();
			pDate.setLayout(new FlowLayout(FlowLayout.CENTER));
			Date date = new Date(m.getFechaPubli());
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
			String p = df.format(date);
			pDate.add(new JLabel(p));
			
			
			JPanel pNombre= new JPanel();
			pNombre.setLayout(new FlowLayout(FlowLayout.CENTER));
			pNombre.add(new JLabel(m.getNombre()));
			
			JPanel pDinero= new JPanel();
			pDinero.setLayout(new FlowLayout(FlowLayout.CENTER));
			pDinero.add(new JLabel (""+m.getPrecioSalida()+ "$"));
			pProducto.add(pNombre);
			pProducto.add(pDate);
			pProducto.add(pDinero);
			pCentro.add(pProducto);
			pCentro.add(new JSeparator());
			
		}
		
		
		
		JScrollPane sjp=new JScrollPane(pCentro);
		
		
		pSur = new JPanel();
		btnCerrar = new JButton("VOLVER ATRAS");
		pSur.add(btnCerrar);
		
		
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaCompras v= new VentanaCompras(c, wt, email);
				VentanaVentas.this.dispose();				
			}
		});
		
		getContentPane().add(sjp,BorderLayout.CENTER);
		getContentPane().add(pSur,BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
		
		setVisible(true);

		

		
		
	}
	
	
	

}
