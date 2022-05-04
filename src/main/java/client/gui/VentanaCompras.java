package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.controller.ComprasController;
import client.controller.VentasController;
import serialization.Categoria;
import serialization.Compra;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import util.ReventaException;
import javax.swing.*;
import java.awt.*;


public class VentanaCompras extends JFrame{

	private Client client;
	private WebTarget webTarget;
	private String email;
	private List<Producto> productos;
	private ComprasController controller;
	
	public VentanaCompras(Client cliente, WebTarget webTarget, String email) {
		this.client=cliente;
		this.webTarget=webTarget;
		this.email=email;
		this.controller= new ComprasController(webTarget, email);
		JPanel pCentro= new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		JComboBox<String>cbOrdenar= new JComboBox<>();
		cbOrdenar.addItem("Sin orden");
		cbOrdenar.addItem("Ordenar por Ventas del Vendedor");
		cbOrdenar.addItem("Ordenar por Fecha de Publicacion ascendente");
		cbOrdenar.addItem("Ordenar por Fecha de Publicacion descendente");
		cbOrdenar.addItem("Favoritos");
		try {
			 productos=controller.getProductosEnVenta();
			 controller.setProductos(productos);
			
			for (Producto p:productos) {
				controller.crearPanel(p, pCentro);
			}
			
		} catch (ReventaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		cbOrdenar.addItemListener (new ItemListener () {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					String seleccion=(String)cbOrdenar.getSelectedItem();
					if (seleccion.compareTo("Ordenar por Ventas del Vendedor")==0) {
						controller.ordenarPorVentas(pCentro);
					}
					else if (seleccion.compareTo("Ordenar por Fecha de Publicacion ascendente")==0) {
						controller.ordenarPorFechaAsc(pCentro);
					}
					else if (seleccion.compareTo("Ordenar por Fecha de Publicacion descendente")==0) {
						controller.ordenarPorFechaDesc(pCentro);
					}
					else if (seleccion.compareTo("Favoritos")==0) {
						controller.mostrarFavoritos(pCentro, email);
						revalidate();
					}
				}
			}
		});
		
		getContentPane().add(cbOrdenar, BorderLayout.NORTH);
		
	
		getContentPane().add(pCentro,BorderLayout.CENTER);
		JPanel pVender= new JPanel();
		JButton bVender= new JButton("Vender");
		bVender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaProducto vR= new VentanaProducto(client, VentanaCompras.this.webTarget,VentanaCompras.this.email);
				VentanaCompras.this.dispose();
				
			}
			
		});
		pVender.add(bVender);
		JButton bVentas= new JButton("Ventas");
		bVentas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaVentas v=new VentanaVentas(new VentasController(webTarget, email), cliente, webTarget, email);
				VentanaCompras.this.dispose();	
			}
			
		});
		pVender.add(bVentas);
		
		JButton bMensajes= new JButton("Mensajes");
		bMensajes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaChat v= new VentanaChat(cliente, webTarget, email);
				VentanaCompras.this.dispose();	
			}
			
		});
		pVender.add(bMensajes);
		getContentPane().add(pVender, BorderLayout.SOUTH);
		this.pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setVisible(true);
		
		//TODO desplegar todos los productos
		//TODO coger el producto seleccionado
	}
	
	




	

	

	
	

	

	
	
	
	

	
	
	
	
	

	

	

}
