package client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.StyledDocument;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.controller.ChatController;
import serialization.Mensaje;
import serialization.Usuario;
import util.ReventaException;

public class VentanaChat extends JFrame {
	private Client client;
	private String em1,em2;
	private WebTarget webTarget;
	private JPanel panelCentro;
	private JPanel panelSur;
	private JTextField mensaje,nick,ip;
	private JTextPane campoChat;
	private JButton botonEnviar;
	private static VentanaChat v1;
	private ChatController cc;
	
	
	public VentanaChat(Client c, WebTarget wt, String email1, String email2) {
		client=c;
		em1=email1;
		em2=email2;
		this.webTarget=webTarget;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		v1=this;
		v1.setTitle("");
		panelCentro= new JPanel();
		
		panelSur = new JPanel();
		panelSur.setLayout(new GridLayout());
		campoChat= new JTextPane();
		campoChat.setEditable(false);
		StyledDocument doc = campoChat.getStyledDocument();
		botonEnviar = new JButton("ENVIAR");
		mensaje = new JTextField(20);
		
		
		panelSur.add(campoChat);
		panelSur.add(mensaje);
		panelSur.add(botonEnviar);
		
		v1.add(panelCentro);
		v1.add(panelSur);
		
		Thread hilo = new Thread();
		hilo.start();
		botonEnviar.addActionListener(new ActionListener()
				{
					public void actionPerformed (ActionEvent e) {
						String a = "\n" + mensaje.getText();
						campoChat.setText(a);
						v1.pack();
					}
				});
			
		setLocationRelativeTo(null);
		this.pack();
		setVisible(true);
	}


		
	
	
	
	
	
}
