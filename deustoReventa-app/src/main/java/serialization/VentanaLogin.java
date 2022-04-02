package serialization;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaLogin extends JFrame {
	private JPanel contentPane, pSur, pNorte, pCentro;
	private JButton login,cerrar,registrarse;
	private JTextField usuario;
	private JPasswordField contraseina;
	private static VentanaLogin v1;
	
	public VentanaLogin(){
		super();
		JFrame ventana=this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		login = new JButton("Login");
		cerrar = new JButton("Cerrar");
		registrarse = new JButton("Registrate");
		
		pSur= new JPanel();
		pSur.setBackground(Color.WHITE);
		contentPane.add(pSur, BorderLayout.SOUTH);
		
		pNorte = new JPanel();
		pNorte.setBackground(Color.WHITE);
		contentPane.add(pNorte, BorderLayout.NORTH);
		
		pCentro = new JPanel();
		pCentro.setBackground(Color.WHITE);
		contentPane.add(pCentro, BorderLayout.CENTER);
		
		JLabel lblInicie = new JLabel("Inicie sesion:");
		pNorte.add(lblInicie);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		pCentro.add(lblUsuario);
		
		JLabel lblContraseina = new JLabel("Contraseña:");
		pCentro.add(lblContraseina);
		
		usuario = new JTextField();
		pCentro.add(usuario);
		usuario.setColumns(10);
		
		contraseina = new JPasswordField();
		pCentro.add(contraseina);
		contraseina.setColumns(10);
		
		pSur.add(login);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		pSur.add(cerrar);
		cerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ventana.dispose();
			}
		});
		pSur.add(registrarse);
		registrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//añadir ventana registrarse
			}
		});
		
		
		
		
		
		
		
		
	}
	public static void main(String[] args) {
		new VentanaLogin();
	}

}



