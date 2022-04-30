package client.gui;

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
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import serialization.Mensaje;
import serialization.Usuario;
import util.ReventaException;

public class VentanaChat extends JFrame {
	private Client client;
	private WebTarget webTarget;
	private JPanel panelCentro;
	private JPanel panelSur;
	private JTextField mensaje,nick,ip;
	private JTextArea campoChat;
	private JButton botonEnviar;
	private static VentanaChat v1;
	
	public VentanaChat(Client c, WebTarget wt, String email1, String email2) {
		client=c;
		this.webTarget=webTarget;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		v1=this;
		v1.setTitle("");
		panelCentro= new JPanel();
		
		panelSur = new JPanel();
		panelSur.setLayout(new GridLayout());
		campoChat= new JTextArea("DIABLO");
		campoChat.setEditable(false);
		botonEnviar = new JButton("ENVIAR");
		mensaje = new JTextField(20);
		
		
		panelSur.add(campoChat);
		panelSur.add(mensaje);
		panelSur.add(botonEnviar);
		
		v1.add(panelCentro);
		v1.add(panelSur);
		botonEnviar.addActionListener(new ActionListener()
				{
					public void actionPerformed (ActionEvent e) {
						String a = "\n" + mensaje.getText();
						campoChat.append(a);
						v1.pack();
					}
				});
		setLocationRelativeTo(null);
		this.pack();
		setVisible(true);
	}
	
	
	
	
}
