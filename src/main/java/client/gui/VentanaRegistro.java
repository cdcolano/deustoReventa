package client.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.controller.RegistroController;
import serialization.Compra;
import serialization.Tarjeta;
import serialization.Usuario;
import util.ReventaException;
import java.awt.*;

public class VentanaRegistro extends JFrame{
	private JTextField tfEmail,tfNombreTar, tfNumTar,tfAnyoVen,tfMesVen;
	private JPasswordField tfPassword;
	private JLabel lError;
	private WebTarget webTarget;
	private Client client;
	private RegistroController rc;
	public VentanaRegistro(Client cliente, WebTarget wt) {
		super();
		JPanel pCentral=new JPanel();
		pCentral.setLayout(new BoxLayout(pCentral, BoxLayout.Y_AXIS));
		this.client=cliente;
		this.webTarget=wt;
		this.rc= new RegistroController(webTarget);
		JPanel pContrasena= new JPanel();
		JLabel lblContraseina = new JLabel("Contraseña: ");
		pContrasena.add(lblContraseina);
		pContrasena.setBackground(Color.WHITE);
		pContrasena.setLayout(new FlowLayout(FlowLayout.LEFT));
		tfPassword= new JPasswordField(20);
		pContrasena.add(tfPassword);
		lError= new JLabel();
		lError.setForeground(Color.RED);
		JPanel pEmail= new JPanel();
		JLabel lEmail = new JLabel("Email: ");
		pEmail.add(lEmail);
		pEmail.setBackground(Color.WHITE);
		pEmail.setLayout(new FlowLayout(FlowLayout.LEFT));
		tfEmail= new JTextField(20);
		pEmail.add(tfEmail);
		
		
		JPanel pNombreTar= new JPanel();
		JLabel lNombreTar = new JLabel("Numero de tarjeta: ");
		pNombreTar.add(lNombreTar);
		pNombreTar.setBackground(Color.WHITE);
		pNombreTar.setLayout(new FlowLayout(FlowLayout.LEFT));
		tfNombreTar= new JTextField(20);
		pNombreTar.add(tfNombreTar);
		
		
		JPanel pNumTar= new JPanel();
		JLabel lNumTar = new JLabel("Codigo de tarjeta: ");
		pNumTar.add(lNumTar);
		pNumTar.setBackground(Color.WHITE);
		pNumTar.setLayout(new FlowLayout(FlowLayout.LEFT));
		tfNumTar= new JTextField(20);
		pNumTar.add(tfNumTar);
		
		JPanel pAnyoVen= new JPanel();
		JLabel lAnyoVen = new JLabel("Año de vencimiento de tarjeta: ");
		pAnyoVen.add(lAnyoVen);
		pAnyoVen.setBackground(Color.WHITE);
		pAnyoVen.setLayout(new FlowLayout(FlowLayout.LEFT));
		tfAnyoVen= new JTextField(20);
		pAnyoVen.add(tfAnyoVen);
		

		JPanel pMesVen= new JPanel();
		JLabel lMesVen = new JLabel("Mes de vencimiento de tarjeta: ");
		pMesVen.add(lMesVen);
		pMesVen.setBackground(Color.WHITE);
		pMesVen.setLayout(new FlowLayout(FlowLayout.LEFT));
		tfMesVen= new JTextField(20);
		pMesVen.add(tfMesVen);
		
		
		pEmail.setLayout(new FlowLayout(FlowLayout.LEFT));
		pContrasena.setLayout(new FlowLayout(FlowLayout.LEFT));
		pNombreTar.setLayout(new FlowLayout(FlowLayout.LEFT));
		pNumTar.setLayout(new FlowLayout(FlowLayout.LEFT));
		pAnyoVen.setLayout(new FlowLayout(FlowLayout.LEFT));
		pMesVen.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		pCentral.add(pEmail);
		pCentral.add(pContrasena);
		pCentral.add(pNombreTar);
		pCentral.add(pNumTar);
		pCentral.add(pAnyoVen);
		pCentral.add(pMesVen);
		
		
		JPanel pAceptar= new JPanel();
		JButton bAceptar=new JButton("Aceptar");
		bAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Usuario u= new Usuario();
				u.setEmail(tfEmail.getText());
				u.setPassword(tfPassword.getText());
				Tarjeta t= new Tarjeta();
				t.setNombre(tfNombreTar.getText());
				try {
					t.setAnyoVencimiento(Integer.parseInt(tfAnyoVen.getText()));
					t.setCodigoSecreto(Integer.parseInt(tfNumTar.getText()));
					t.setMesVencimiento(Integer.parseInt(tfMesVen.getText()));
					u.setTarjeta(t);
					rc.registrar(u);
					VentanaRegistro.this.dispose();
				}catch(NumberFormatException ex) {
					lError.setText("Los campos codigo y año/mes de vencimiento de la tarjeta deben ser numericos");
				}catch(Exception exc) {
					lError.setText("Fallo en el registro");
					System.out.println("*ERROR* " + exc.getMessage());
				}
			}
			
		});
		pAceptar.add(bAceptar);
		pAceptar.setBackground(Color.WHITE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().add(lError,BorderLayout.NORTH);
		getContentPane().add(pCentral,BorderLayout.CENTER);
		getContentPane().add(pAceptar, BorderLayout.SOUTH);
		this.pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
}
