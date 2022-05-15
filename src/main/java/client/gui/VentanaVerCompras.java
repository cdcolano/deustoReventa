package client.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import client.controller.ComprasController;
import client.controller.VentasController;
import client.controller.VerComprasController;
import serialization.Compra;
import serialization.Producto;
import util.ReventaException;

public class VentanaVerCompras extends JFrame {
	
	private JButton btnCerrar;
	private JPanel pCentro,pSur;
	private Client client;
	private String em1;
	private WebTarget webTarget;

	private VerComprasController vc1;

	
	
	public VentanaVerCompras(VerComprasController vcc, Client c, WebTarget wt, String email) {
		
		this.client=c;
		this.em1=email;
		this.webTarget=wt;
		vc1=vcc;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		
		List<Compra> compras=new ArrayList<Compra>();
		try {
			compras = vc1.getListaProductosComprados(email);
		} catch (ReventaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			pCentro = new JPanel();
			pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
			for(Compra compra: compras) {
				Producto m=vc1.getPoducto(compra);
				JPanel pDate= new JPanel();
				JPanel pNombre= new JPanel();
				JPanel pDinero= new JPanel();
				JPanel pProducto= new JPanel();
				pProducto.setLayout(new BoxLayout(pProducto, BoxLayout.Y_AXIS));
				pDate.setLayout(new FlowLayout( FlowLayout.CENTER));
				pDinero.setLayout(new FlowLayout( FlowLayout.CENTER));
				pNombre.setLayout(new FlowLayout( FlowLayout.CENTER));
				Date date = new Date(m.getFechaPubli());
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
				String p = df.format(date);
				pDate.add(new JLabel(p));
				pNombre.add(new JLabel(m.getNombre()));
				pDinero.add(new JLabel(""+compra.getPrecio()));
				pProducto.add(pNombre);
				pProducto.add(pDate);
				pProducto.add(pDinero);
				pCentro.add(pProducto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			
		JScrollPane jsp= new JScrollPane(pCentro);
		
		
		
		pSur = new JPanel();
		btnCerrar = new JButton("VOLVER ATRAS");
		pSur.add(btnCerrar);
		
		
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaCompras vc= new VentanaCompras(new ComprasController(webTarget,email),c, wt, email);
				VentanaVerCompras.this.dispose();
			}
		});
		
		getContentPane().add(jsp,BorderLayout.CENTER);
		getContentPane().add(pSur,BorderLayout.SOUTH);
		
		
		
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		
	}

}
