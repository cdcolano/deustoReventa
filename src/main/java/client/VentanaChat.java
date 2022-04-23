package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class VentanaChat extends JFrame {
	private Client client;
	private WebTarget webTarget;
	private JPanel panelCentro;
	private JTextField mensaje,nick,ip;
	private JTextArea campoChat;
	private JButton botonEnviar;
	
	public VentanaChat() {
		//client=c;
		//this.webTarget=webTarget;
		panelCentro= new JPanel();
		panelCentro.setLayout(new BorderLayout());		
		campoChat= new JTextArea();
		botonEnviar = new JButton();
		mensaje = new JTextField(20);
		nick = new JTextField(5);
		ip = new JTextField(8);
		
		
		panelCentro.add(nick, BorderLayout.NORTH);
		panelCentro.add(ip, BorderLayout.NORTH);
		panelCentro.add(campoChat, BorderLayout.CENTER);
		panelCentro.add(mensaje, BorderLayout.CENTER);
		panelCentro.add(botonEnviar, BorderLayout.SOUTH);
		
		
		botonEnviar.addActionListener(new ActionListener()
				{
					public void actionPerformed (ActionEvent e) {
						
					}
				});
	}
	public static void main(String[]args) {
		System.out.println("hola");
	}
	
}
