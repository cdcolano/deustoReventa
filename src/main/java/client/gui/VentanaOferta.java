package client.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import javax.ws.rs.client.WebTarget;

import client.controller.ComprasController;
import client.controller.OfertaController;
import serialization.Producto;
import util.ReventaException;

public class VentanaOferta extends JFrame{
	private double oferta;
	private JTextField tPrecio;
	
	public VentanaOferta(OfertaController oc,WebTarget wt, String email, int idProd) {
		tPrecio= new JTextField(10);
		JLabel lPrecio= new JLabel("Oferta: ");
		JPanel pPrecio= new JPanel();
		
		pPrecio.setLayout(new FlowLayout(FlowLayout.LEFT));
		pPrecio.add(lPrecio);
		pPrecio.add(tPrecio);
		
		getContentPane().add(pPrecio,BorderLayout.CENTER);
		JButton bAceptar= new JButton("Aceptar");
		JButton bCancelar = new JButton("Cancelar");
		
		bAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				aceptar();
				try {
					oc.addOferta(email, idProd, oferta);
				} catch (ReventaException e1) {
					e1.printStackTrace();
				}
				new VentanaCompras(new ComprasController(wt, email), null, wt, email);
			}
			
		});
		
		bCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaCompras(new ComprasController(wt, email),null, wt, email);
			}
			
		});
		JPanel botonera= new JPanel();
		botonera.add(bAceptar);
		botonera.add(bCancelar);
		getContentPane().add(botonera,BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
	
	public void aceptar() {
		try {
			oferta=Double.parseDouble(tPrecio.getText());
		}catch(NumberFormatException ex) {
			JLabel lError= new JLabel("Error en el formato de la oferta introducida");
			lError.setForeground(Color.RED);
			getContentPane().add(lError,BorderLayout.SOUTH);
		}
	}
}
