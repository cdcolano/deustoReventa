package client.gui;

import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.controller.ComprasController;
import client.controller.LoginController;
import serialization.Compra;
import serialization.Producto;
import serialization.Usuario;
import util.ReventaException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaLogin extends JFrame {
	private JPanel contentPane, pSur, pNorte, pCentro;
	private JButton login,cerrar,registrarse;
	private JTextField usuario;
	private JPasswordField contraseina;
	private static VentanaLogin v1;
	private Client cliente;
	private WebTarget webTarget;
	private JLabel lError;
	private LoginController lc;
	
	public VentanaLogin(Client cliente, WebTarget webTarget){
		super();
		this.cliente=cliente;
		this.webTarget=webTarget;
		this.lc = new LoginController(cliente, webTarget);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		setLocationRelativeTo(null);
		v1=this;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		login = new JButton("Login");
		cerrar = new JButton("Cerrar");
		registrarse = new JButton("Registrate");
		
		pSur= new JPanel();
		pSur.setBackground(Color.WHITE);
		
		
		pNorte = new JPanel();
		pNorte.setBackground(Color.WHITE);
		
		
		pCentro = new JPanel();
		pCentro.setBackground(Color.WHITE);
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		
		
		lError= new JLabel();
		
		JLabel lblInicie = new JLabel("Inicio de sesion");
		pNorte.add(lblInicie);
		
		JPanel pEmail= new JPanel();
		JLabel lblUsuario = new JLabel("Email:");
		usuario = new JTextField();
		pEmail.add(lblUsuario);
		pEmail.add(usuario);
		usuario.setColumns(10);
		
		pEmail.setBackground(Color.WHITE);
		pEmail.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		pCentro.add(pEmail);
		
	
		
		JPanel pContrasena= new JPanel();
		JLabel lblContraseina = new JLabel("Contraseña:");
		pContrasena.add(lblContraseina);
		pContrasena.setBackground(Color.WHITE);
		pContrasena.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		
		contraseina = new JPasswordField();
		contraseina.setColumns(10);
		pContrasena.add(contraseina);
		
		pCentro.add(pContrasena);
		pCentro.add(lError);
		lError.setForeground(Color.RED);
		
		pCentro.setBackground(Color.WHITE);
		
		pSur.add(login);
		registrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRegistro v= new VentanaRegistro(v1.cliente, v1.webTarget);
				v1.dispose();
			}
		});
		pSur.add(registrarse);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String email=usuario.getText();
					String password=contraseina.getText();
					boolean valido=lc.logIn(email, password, lError,v1);
					if (valido) {
						VentanaCompras v= new VentanaCompras(new ComprasController(webTarget, email),VentanaLogin.this.cliente, VentanaLogin.this.webTarget, email);
						//VentanaChat v1 = new VentanaChat(VentanaLogin.this.cliente,VentanaLogin.this.webTarget,email);
						VentanaLogin.this.dispose();
					}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		contentPane.add(pNorte, BorderLayout.NORTH);
		contentPane.add(pCentro, BorderLayout.CENTER);
		
		contentPane.add(pSur);
		
		setVisible(true);
		this.pack();
		setLocationRelativeTo(null);
		
		
	}
}