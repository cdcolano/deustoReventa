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
	
	public VentanaLogin(Client cliente, WebTarget webTarget){
		super();
		this.cliente=cliente;
		this.webTarget=webTarget;
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
					boolean valido=logIn(email, password, lError);
					if (valido) {
						VentanaCompras v= new VentanaCompras(VentanaLogin.this.cliente, VentanaLogin.this.webTarget, email);
					//	VentanaChat v1 = new VentanaChat(VentanaLogin.this.cliente,VentanaLogin.this.webTarget,email);
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
	
	
	public boolean logIn(String email, String password, JLabel lError) throws ReventaException {
		try {
			WebTarget webTarget=this.webTarget.path("/reventa/logIn");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Usuario u=new Usuario();
			u.setEmail(email);
			u.setPassword(password);
			System.out.println(webTarget.getUri());
			Response response = invocationBuilder.post(Entity.entity(u, MediaType.APPLICATION_JSON));
			
			if (response.getStatus() != Status.OK.getStatusCode()) {
				throw new ReventaException("" + response.getStatus());
			}
			boolean logIn=response.readEntity(boolean.class);
			if (!logIn) {
				lError.setText("Email o contraseña incorrectos");
				this.setLocationRelativeTo(null);
				this.pack();
				
			}
			return logIn;
		}catch(Exception e) {
			lError.setText("Algo ha fallado al realizar el LogIn");
			System.out.println("* Error " + e.getMessage() +"*");
			this.pack();
			this.setLocationRelativeTo(null);
			return false;
		}
	}
	
	
	public Usuario getUsuario(String email)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("collector/getUsuario/"+ email);
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			Usuario u = response.readEntity(Usuario.class);
			return u;
		} else {
			throw new ReventaException("" + response.getStatus());
		}
	}
		

}