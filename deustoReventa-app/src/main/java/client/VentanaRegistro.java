package serialization;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;




public class VentanaRegistro extends JFrame {
	
	private JLabel lblTitulo, lblNombre,lblEmail,lblContraseina,lblNumTarjeta,lblFechaCad,lblCvv;
	private JTextField txtNombre,txtNumTarjeta,txtEmail,txtFechaCad;
	private JPasswordField txtContraseina, txtCvv;
	private JButton btnRegistro,btnCancelar;
	private JPanel pCentro,pSur, pNorte;
	
	
	public VentanaRegistro() {
		super();
		JFrame ventana = this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("REGISTRO");
		
		lblTitulo = new JLabel("Registrate");
		
		
		lblNombre = new JLabel("Nombre de la Tarjeta:");
		txtNombre = new JTextField(20);
		
		lblEmail = new JLabel("Introduce tu e-mail:");
		txtEmail = new JTextField(20);
		
		lblContraseina= new JLabel("Introduce una contraseña:");
		txtContraseina = new JPasswordField(30);
		
		lblNumTarjeta = new JLabel("Introduce numero de Tarjeta:");
		txtNumTarjeta = new JTextField(30);
		
		lblFechaCad = new JLabel("Fecha caducidad:");
		txtFechaCad = new JTextField(30);
		
		lblCvv= new JLabel("CVV:");
		txtCvv = new JPasswordField(30);
		
		pNorte = new JPanel();
		pNorte.add(lblTitulo);
		
		pCentro = new JPanel(new GridLayout(6,2));
		
		pCentro.add(lblEmail);
		pCentro.add(txtEmail);
		
		pCentro.add(lblContraseina);
		pCentro.add(txtContraseina);
		
		pCentro.add(lblNombre);
		pCentro.add(txtNombre);
		
		pCentro.add(lblNumTarjeta);
		pCentro.add(txtNumTarjeta);
		
		pCentro.add(lblFechaCad);
		pCentro.add(txtFechaCad);
		
		pCentro.add(lblCvv);
		pCentro.add(txtCvv);
		
		getContentPane().add(pCentro,BorderLayout.CENTER);
	
		pSur = new JPanel();
		btnRegistro = new JButton("GUARDAR DATOS");
		btnCancelar = new JButton("CANCELAR");
		
		
		pSur.add(btnRegistro);
		pSur.add(btnCancelar);
		
		
		getContentPane().add(pSur, BorderLayout.SOUTH);
		btnRegistro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					
					String nombre = txtNombre.getText();
					String email = txtEmail.getText();
					String contraseina = txtContraseina.getText();
					String numTarjeta = txtNumTarjeta.getText();
					int numeroTarjeta = Integer.parseInt(numTarjeta);
					String fechaCad = txtFechaCad.getText();
					String cvv = txtCvv.getText();
					int numCvv = Integer.parseInt(cvv);
				    
				    //Usuario us = new Usuario(nombre, dni, email, contraseina, numeroEdad, carreraSelec,cursoSelec);
				    //INSERTAR EN BD
				    ventana.dispose();
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ventana.dispose();
			}
		});

		setVisible(true);
	}
	
	public static void main(String[]args) {
		
		VentanaRegistro v1 = new VentanaRegistro();
	}

	
}
