package client.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.StyledDocument;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.controller.ChatController;
import client.controller.ComprasController;
import dao.UsuarioDAO;
import serialization.Mensaje;
import serialization.Usuario;
import util.ReventaException;

/**Ventana que se encarga de mostrar el chat del usuario
 * @author Ander
 *
 */
public class VentanaChat extends JFrame {
	private Client client;
	private String em1;
	private WebTarget webTarget;
	private JPanel panelCentro,panelSur,panelEnviados,panelRecibidos;
	private JTextField mensaje;
	private JTextArea enviados, recibidos;
	private JTextPane campoChat,campoChat1;
	private JButton botonEnviar;
	private JComboBox<String> comboUsuarios;
	private JScrollPane scroll,scroll1;
	private static VentanaChat v1;
	private ChatController cc;
	private UsuarioDAO udao;
	
	
	
	/**Metodo que construye la ventana
	 * @param cc controller de la ventana donde se almacenan los metodos necesarios
	 * @param c el cliente de la aplicacion
	 * @param wt WebTarget para hacer las llamadas al server
	 * @param email1 email del usuario
	 */
	public VentanaChat(ChatController cc,Client c, WebTarget wt, String email1) {
		this.client=c;
		this.em1=email1;
		udao = new UsuarioDAO();
		webTarget= wt;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		this.cc = cc;
		v1=this;
		
		
		comboUsuarios = new JComboBox<String>();
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
			enviados.append(m.getContenido() + "-" + m.getEnviado()+ "-"+ p + "\n");
		}
		for(Mensaje m1: recibidosM) {
			Date date = new Date(m1.getFecha());
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
			String p = df.format(date);
			recibidos.append(m1.getContenido() + "-" + m1.getRecibido()+ "-"+ p + "\n");
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
				enviarMensaje(email1,enviados,mensaje,v1,comboUsuarios,cc);
			}
		});
		
		JPanel pContent= new JPanel();
		pContent.setLayout(new BorderLayout());
		
		panelCentro.add(panelEnviados);
		panelCentro.add(panelRecibidos);
		
		panelSur.add(mensaje);
		panelSur.add(comboUsuarios);
		panelSur.add(botonEnviar);
		pContent.add(panelCentro, BorderLayout.CENTER);
		pContent.add(panelSur, BorderLayout.SOUTH);
		getContentPane().add(pContent, BorderLayout.CENTER);
		
		JPanel pVolver= new JPanel();
		JButton bVolver = new JButton("Volver atras");
		bVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaCompras v= new VentanaCompras(new ComprasController(wt, email1), c, wt, email1);
				VentanaChat.this.dispose();
			}
			
		});
		pVolver.add(bVolver);
		
		getContentPane().add(pVolver, BorderLayout.SOUTH);
		this.pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**Metodo que envia el mensaje escrito al usuario indicado
	 * @param email1 email del usuario
	 * @param ta panel donde se muestran los mensajes
	 * @param tf field donde se escribe el mensaje
	 * @param vt la ventana en la que se crea
	 * @param jc combobox para elegir el usuario al que se quiere enviar el mensaje
	 * @param cac controller para acceder a los metodos de la ventana
	 */
	public void enviarMensaje(String email1, JTextArea ta, JTextField tf, VentanaChat vt, JComboBox jc, ChatController cac) {
		Mensaje m = new Mensaje();
		m.setContenido(tf.getText());
		Date fecha = new Date();
		long milliseconds = fecha.getTime();
		m.setFecha(milliseconds);
		try {
			cac.enviar(email1, jc.getSelectedItem().toString(), m);
		} catch (ReventaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
		String p = df.format(fecha);
		ta.setText(tf.getText()+" - "+ p +"\n" + ta.getText());
		vt.pack();
	}

}
