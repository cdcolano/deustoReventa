package client.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import dao.UsuarioDAO;
import serialization.Mensaje;
import serialization.Usuario;
import util.ReventaException;

public class VentanaChat extends JFrame {
	private Client client;
	private String em1;
	private WebTarget webTarget;
	private JPanel panelCentro,panelSur,panelEnviados,panelRecibidos;
	private JTextField mensaje;
	private JTextArea enviados, recibidos;
	private JTextPane campoChat,campoChat1;
	private JButton botonEnviar;
	private JComboBox comboUsuarios;
	private JScrollPane scroll,scroll1;
	private static VentanaChat v1;
	private ChatController cc;
	private UsuarioDAO udao;
	
	
	
	public VentanaChat(Client c, WebTarget wt, String email1) {
		this.client=c;
		this.em1=email1;
		udao = new UsuarioDAO();
		webTarget= wt;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		cc = new ChatController(wt,email1,udao);
		v1=this;
		v1.setTitle("");
		v1.setLayout( new GridLayout(2,0));
		
		
		comboUsuarios = new JComboBox();
		enviados = new JTextArea();
		recibidos = new JTextArea();
		enviados.setEditable(false);
		recibidos.setEditable(false);
		
		scroll = new JScrollPane(enviados);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Dimension dm = new Dimension(150,150);
		scroll.setPreferredSize(dm);
		
		scroll1 = new JScrollPane(recibidos);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll1.setPreferredSize(dm);
		
		List<String> usuariosMails = cc.getEmailUsuarios();
		List<Mensaje> enviadosM = cc.getMensajesEnviados(email1);
		List<Mensaje> recibidosM = cc.getMensajesRecibidos(email1);
		
		for(String a : usuariosMails) {
			comboUsuarios.addItem(a);
		}
		for(Mensaje m: enviadosM) {
			Date date = new Date(m.getFecha());
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
			String p = df.format(date);
			enviados.append(m.getContenido() +" - "+ p + "\n");
		}
		for(Mensaje m1: recibidosM) {
			Date date = new Date(m1.getFecha());
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
			String p = df.format(date);
			recibidos.append(m1.getContenido() +" - "+ p + "\n" );
		}
		
		
		
		
		panelCentro= new JPanel();
		panelCentro.setLayout(new GridLayout(0,2));
		panelSur = new JPanel();
		panelSur.setLayout(new GridBagLayout());
		
		panelEnviados = new JPanel();
		//panelEnviados.setLayout(new GridLayout());
		panelEnviados.add(scroll);
		//panelEnviados.add(enviados);
		
		panelRecibidos = new JPanel();
		//panelRecibidos.setLayout(new GridLayout());
		panelRecibidos.add(scroll1);
		//panelRecibidos.add(recibidos);
	
		
		mensaje = new JTextField(20);
		botonEnviar = new JButton("ENVIAR");
		botonEnviar.addActionListener(new ActionListener()
		{
			public void actionPerformed (ActionEvent e) {
				Mensaje m = new Mensaje();
				m.setContenido(mensaje.getText());
				Date fecha = new Date();
				long milliseconds = fecha.getTime();
				m.setFecha(milliseconds);
				try {
					cc.enviar(email1, comboUsuarios.getSelectedItem().toString(), m);
				} catch (ReventaException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
				String p = df.format(fecha);
				enviados.append(mensaje.getText()+" - "+ p +"\n");
				v1.pack();
			}
		});
	
		
		panelCentro.add(panelEnviados);
		panelCentro.add(panelRecibidos);
		
		panelSur.add(mensaje);
		panelSur.add(comboUsuarios);
		panelSur.add(botonEnviar);
		v1.add(panelCentro);
		v1.add(panelSur);
		setLocationRelativeTo(null);
		this.pack();
		setVisible(true);
	}

}
