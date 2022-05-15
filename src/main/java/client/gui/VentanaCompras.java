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

import client.controller.ChatController;
import client.controller.ComprasController;
import client.controller.MisProductosController;
import client.controller.OfertasController;
import client.controller.ProductoController;
import client.controller.VentasController;
import client.controller.VerComprasController;
import dao.UsuarioDAO;
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
	private static VentanaCompras v1;
	private JPanel pNorte;
	private JPanel pCentro;
	private JPanel pCategorias;
	private JPanel pOrdenar;
	
	public VentanaCompras(ComprasController cc, Client cliente, WebTarget webTarget, String email) {
		this.client=cliente;
		this.webTarget=webTarget;
		this.email=email;
		this.controller= cc;
		v1 = this;
		pCentro= new JPanel();
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
					/*String seleccion=(String)cbOrdenar.getSelectedItem();
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
					}*/
					itemStateChangedVentana(cbOrdenar,pCentro,controller,email,v1);
				}
			}
		});
		
		
		JComboBox<String>cbCategorias= new JComboBox<String>();
		cbCategorias.addItem("Todas");
		cbCategorias.addItem("Ordenador");
		cbCategorias.addItem("Vehiculo");
		JLabel lCategorias= new JLabel("Categoria: ");
		pCategorias= new JPanel();
		pCategorias.setLayout(new FlowLayout(FlowLayout.LEFT));
		pCategorias.add(lCategorias);
		pCategorias.add(cbCategorias);
		pNorte= new JPanel();
		pNorte.setLayout(new BorderLayout());
		
		JLabel lOrdenar= new JLabel("Ordenar: ");
		pOrdenar= new JPanel();
		pOrdenar.setLayout(new FlowLayout(FlowLayout.LEFT));
		pOrdenar.add(lOrdenar);
		pOrdenar.add(cbOrdenar);
	
		pNorte.add(pCategorias, BorderLayout.NORTH);
		pNorte.add(pOrdenar, BorderLayout.CENTER);
		
		v1.getContentPane().add(pNorte, BorderLayout.NORTH);
		
		cbCategorias.addItemListener (new ItemListener () {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					/*String seleccion=(String)cbOrdenar.getSelectedItem();
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
					}*/
					itemStateChangedCategoria(cbCategorias,pCentro,controller,email,v1);
				}
			}
		});
		JScrollPane jsc= new JScrollPane(pCentro);
	
		v1.getContentPane().add(jsc,BorderLayout.CENTER);
		JPanel pVender= new JPanel();
		JButton bVender= new JButton("Vender");
		bVender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaProducto vR= new VentanaProducto(new ProductoController(webTarget, email),client, VentanaCompras.this.webTarget,VentanaCompras.this.email);
				v1.dispose();
				
			}
			
		});
		pVender.add(bVender);
		JButton bVentas= new JButton("Ventas");
		bVentas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaVentas v=new VentanaVentas(new VentasController(webTarget, email), cliente, webTarget, email);
				v1.dispose();	
			}
			
		});
		pVender.add(bVentas);
		
		JButton bMensajes= new JButton("Mensajes");
		bMensajes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaChat v= new VentanaChat(new ChatController(webTarget, email, new UsuarioDAO()),cliente, webTarget, email);
				v1.dispose();	
			}
			
		});
		pVender.add(bMensajes);
		
		JButton bMisProductos = new JButton("Mis productos en venta");
		bMisProductos.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				VentanaMisProductos v = new VentanaMisProductos(new MisProductosController(webTarget, email),cliente, webTarget, email);
				v1.dispose();
			}	
		
			
		});
		
		
		JButton bVerCompras= new JButton("Ver compras");
		bVerCompras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaVerCompras v=new VentanaVerCompras(new VerComprasController(webTarget, email), cliente, webTarget, email);
				v1.dispose();	
			}
			
		});
		pVender.add(bVerCompras);
		
		JButton bVerOfertas = new JButton("Ver ofertas");
		bVerOfertas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				VentanaOfertas vo = new VentanaOfertas(new OfertasController(webTarget,email),cliente,webTarget,email);
				v1.dispose();
			}
			
		});
		pVender.add(bVerOfertas);
		
		
		
		pVender.add(bMisProductos);
		v1.getContentPane().add(pVender, BorderLayout.SOUTH);
		this.pack();
		v1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		//TODO desplegar todos los productos
		//TODO coger el producto seleccionado
	}
	
	public void itemStateChangedVentana(JComboBox<String> jc, JPanel pa, ComprasController cco, String mail, VentanaCompras vc) {
		String seleccion=(String)jc.getSelectedItem();
		if (seleccion.compareTo("Ordenar por Ventas del Vendedor")==0) {
			cco.ordenarPorVentas(pa);
		}
		else if (seleccion.compareTo("Ordenar por Fecha de Publicacion ascendente")==0) {
			cco.ordenarPorFechaAsc(pa);
		}
		else if (seleccion.compareTo("Ordenar por Fecha de Publicacion descendente")==0) {
			cco.ordenarPorFechaDesc(pa);
		}
		else if (seleccion.compareTo("Favoritos")==0) {
			cco.mostrarFavoritos(pa, mail);
			vc.revalidate();
			}
	}
	
	public void itemStateChangedCategoria(JComboBox<String> jc, JPanel pa, ComprasController cco, String mail, VentanaCompras vc) {
		String seleccion=(String)jc.getSelectedItem();
		if (seleccion.compareTo("Todas")==0) {
			pNorte.removeAll();
			crearPNorte(pCategorias, pOrdenar);
			cco.ordenarPorFechaDesc(pa);
			revalidate();
		}
		else if (seleccion.compareTo("Ordenador")==0) {
			cco.seleccionarOrdenador(pa);
			crearPNorte(pCategorias, pOrdenar);
			generaFiltrosOrdenador();
			revalidate();
		}
		else if (seleccion.compareTo("Vehiculo")==0) {
			cco.seleccionarVehiculo(pa);
			crearPNorte(pCategorias, pOrdenar);
			generaFiltrosVehiculo();
			revalidate();
		}
	}
	
	public void crearPNorte(JPanel pCategorias, JPanel pOrdenar) {
		pNorte.add(pCategorias, BorderLayout.NORTH);
		pNorte.add(pOrdenar, BorderLayout.CENTER);
	}
	
	public void generaFiltrosOrdenador() {
		JLabel lCpu= new JLabel("CPU: ");
		JTextField tCpu= new JTextField(16);
		JPanel pCpu= new JPanel();
		pCpu.setLayout(new FlowLayout(FlowLayout.LEFT));
		pCpu.add(lCpu);
		pCpu.add(tCpu);
		
		
		JLabel lPlacaBase= new JLabel("Placa Base: ");
		JTextField tPlacaBase= new JTextField(16);
		JPanel pPlacaBase= new JPanel();
		pPlacaBase.setLayout(new FlowLayout(FlowLayout.LEFT));
		pPlacaBase.add(lPlacaBase);
		pPlacaBase.add(tPlacaBase);
		
		JLabel lGrafica= new JLabel("Grafica: ");
		JTextField tGrafica= new JTextField(16);
		JPanel pGrafica= new JPanel();
		pGrafica.setLayout(new FlowLayout(FlowLayout.LEFT));
		pGrafica.add(lGrafica);
		pGrafica.add(tGrafica);
		
		JLabel lRamMinima= new JLabel("Ram minima: ");
		JTextField tRamMinima= new JTextField(16);
		JPanel pRamMinima= new JPanel();
		pRamMinima.setLayout(new FlowLayout(FlowLayout.LEFT));
		pRamMinima.add(lRamMinima);
		pRamMinima.add(tRamMinima);
		
		JLabel lRamMaxima= new JLabel("Ram maxima: ");
		JTextField tRamMaxima= new JTextField(16);
		JPanel pRamMaxima= new JPanel();
		pRamMaxima.setLayout(new FlowLayout(FlowLayout.LEFT));
		pRamMaxima.add(lRamMaxima);
		pRamMaxima.add(tRamMaxima);
		
		JLabel lMemoriaMinima= new JLabel("Memoria minima: ");
		JTextField tMemoriaMinima= new JTextField(16);
		JPanel pMemoriaMinima= new JPanel();
		pMemoriaMinima.setLayout(new FlowLayout(FlowLayout.LEFT));
		pMemoriaMinima.add(lMemoriaMinima);
		pMemoriaMinima.add(tMemoriaMinima);
		
		JLabel lMemoriaMaxima= new JLabel("Memoria maxima: ");
		JTextField tMemoriaMaxima= new JTextField(16);
		JPanel pMemoriaMaxima= new JPanel();
		pMemoriaMaxima.setLayout(new FlowLayout(FlowLayout.LEFT));
		pMemoriaMaxima.add(lMemoriaMaxima);
		pMemoriaMaxima.add(tMemoriaMaxima);
		
		
		JPanel pFiltros= new JPanel();
		pFiltros.setLayout(new BorderLayout());
		JLabel lFiltros= new JLabel("Filtros");
		Font fTitulo = new Font("TimesRoman", Font.PLAIN,20);
		JPanel pTit= new JPanel();
		pTit.setLayout(new FlowLayout(FlowLayout.CENTER));
		pTit.add(lFiltros);
		
		lFiltros.setFont(fTitulo);
		pFiltros.add(new JSeparator(), BorderLayout.NORTH);
		pFiltros.add(pTit, BorderLayout.CENTER);
		
		JPanel pFiltrosContenido= new JPanel();
		pFiltrosContenido.setLayout(new BoxLayout(pFiltrosContenido, BoxLayout.Y_AXIS));
		
		pFiltrosContenido.add(pCpu);
		pFiltrosContenido.add(pGrafica);
		pFiltrosContenido.add(pPlacaBase);
		pFiltrosContenido.add(pRamMinima);
		pFiltrosContenido.add(pRamMaxima);
		pFiltrosContenido.add(pMemoriaMinima);
		pFiltrosContenido.add(pMemoriaMaxima);
		
		JButton bFiltrar= new JButton("Filtrar");
		bFiltrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.filtrarOrdenador(tCpu.getText(),tPlacaBase.getText(), tGrafica.getText(),
						tRamMinima.getText(), tRamMaxima.getText(), tMemoriaMinima.getText(), tMemoriaMaxima.getText(),
						VentanaCompras.this.pCentro);
			}
			
		});
		JPanel pBoton= new JPanel();
		pBoton.setLayout(new FlowLayout(FlowLayout.CENTER));
		pBoton.add(bFiltrar);
		pFiltrosContenido.add(pBoton);
		
		pFiltros.add(pFiltrosContenido, BorderLayout.SOUTH);
		pNorte.add(pFiltros, BorderLayout.SOUTH);
		revalidate();
	}
	
	public void generaFiltrosVehiculo() {
		JLabel lMarca = new JLabel("Marca: ");
		JTextField tMarca= new JTextField(16);
		JPanel pMarca= new JPanel();
		pMarca.setLayout(new FlowLayout(FlowLayout.LEFT));
		pMarca.add(lMarca);
		pMarca.add(tMarca);
		
		
		JLabel lModelo= new JLabel("Modelo: ");
		JTextField tModelo= new JTextField(16);
		JPanel pModelo= new JPanel();
		pModelo.setLayout(new FlowLayout(FlowLayout.LEFT));
		pModelo.add(lModelo);
		pModelo.add(tModelo);
		
		
		JLabel lCVMin= new JLabel("CV minimos: ");
		JTextField tCVMin= new JTextField(16);
		JPanel pCVMin= new JPanel();
		pCVMin.setLayout(new FlowLayout(FlowLayout.LEFT));
		pCVMin.add(lCVMin);
		pCVMin.add(tCVMin);
		
		JLabel lCVMax= new JLabel("CV maximos: ");
		JTextField tCVMax= new JTextField(16);
		JPanel pCVMax= new JPanel();
		pCVMax.setLayout(new FlowLayout(FlowLayout.LEFT));
		pCVMax.add(lCVMax);
		pCVMax.add(tCVMax);
		
		JLabel lKMMin= new JLabel("KM minimos: ");
		JTextField tKMMin= new JTextField(16);
		JPanel pKMMin= new JPanel();
		pKMMin.setLayout(new FlowLayout(FlowLayout.LEFT));
		pKMMin.add(lKMMin);
		pKMMin.add(tKMMin);
		
		JLabel lKMMax= new JLabel("KM maximos: ");
		JTextField tKMMax= new JTextField(16);
		JPanel pKMMax= new JPanel();
		pKMMax.setLayout(new FlowLayout(FlowLayout.LEFT));
		pKMMax.add(lKMMax);
		pKMMax.add(tKMMax);
		
		JLabel lAnyoFabriMin= new JLabel("Ano de fabricacion minimo: ");
		JTextField tAnyoFabriMin= new JTextField(16);
		JPanel pAnyoFabriMin= new JPanel();
		pAnyoFabriMin.setLayout(new FlowLayout(FlowLayout.LEFT));
		pAnyoFabriMin.add(lAnyoFabriMin);
		pAnyoFabriMin.add(tAnyoFabriMin);
		
		JLabel lAnyoFabriMax= new JLabel("Ano de fabricacion maximo: ");
		JTextField tAnyoFabriMax= new JTextField(16);
		JPanel pAnyoFabriMax= new JPanel();
		pAnyoFabriMax.setLayout(new FlowLayout(FlowLayout.LEFT));
		pAnyoFabriMax.add(lAnyoFabriMax);
		pAnyoFabriMax.add(tAnyoFabriMax);
		
		
		
		
		JPanel pFiltros= new JPanel();
		pFiltros.setLayout(new BorderLayout());
		JLabel lFiltros= new JLabel("Filtros");
		Font fTitulo = new Font("TimesRoman", Font.PLAIN,20);
		JPanel pTit= new JPanel();
		pTit.setLayout(new FlowLayout(FlowLayout.CENTER));
		pTit.add(lFiltros);
		
		lFiltros.setFont(fTitulo);
		pFiltros.add(new JSeparator(), BorderLayout.NORTH);
		pFiltros.add(pTit, BorderLayout.CENTER);
		
		JPanel pFiltrosContenido= new JPanel();
		pFiltrosContenido.setLayout(new BoxLayout(pFiltrosContenido, BoxLayout.Y_AXIS));
		
		pFiltrosContenido.add(pMarca);
		pFiltrosContenido.add(pModelo);
		pFiltrosContenido.add(pCVMin);
		pFiltrosContenido.add(pCVMax);
		pFiltrosContenido.add(pKMMin);
		pFiltrosContenido.add(pKMMax);
		pFiltrosContenido.add(pAnyoFabriMin);
		pFiltrosContenido.add(pAnyoFabriMax);
		
		JButton bFiltrar= new JButton("Filtrar");
		bFiltrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.filtrarOrdenador(email, email, email, email, email, email, email, pAnyoFabriMax);
			}
			
		});
		JPanel pBoton= new JPanel();
		pBoton.setLayout(new FlowLayout(FlowLayout.CENTER));
		pBoton.add(bFiltrar);
		pFiltrosContenido.add(pBoton);
		
		pFiltros.add(pFiltrosContenido, BorderLayout.SOUTH);
		pNorte.add(pFiltros, BorderLayout.SOUTH);
		revalidate();
	}
	

	




	

	

	
	

	

	
	
	
	

	
	
	
	
	

	

	

}
