package client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import client.controller.ComprasController;
import client.controller.ProductoController;
import serialization.Categoria;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import util.ReventaException;
import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.*;

/** Ventana para la puesta a la venta de productos
 * @author Jon Eguiluz
 *
 */
public class VentanaProducto extends JFrame{
	private Client client;
	private WebTarget webTarget;
	private JPanel pCambiante;
	private JTextField tfNombre;
	private JTextField tfPrecio;
	private JTextField tfRam;
	private JTextField tfGrafica;
	private JTextField tfMemoria;
	private JComboBox<Categoria>cbCategoria;
	private JTextField tfCpu;
	private JTextField tfPlacaBase;
	private JTextField tfKilometros;
	private JTextField tfCaballos;
	private JTextField tfAnyo;
	private JLabel lError;
	private JPanel pCategoria;
	private JPanel pNombre;
	private JPanel pPrecio;
	private JPanel pRam;
	private JPanel pMemoria;
	private JPanel pCpu;
	private JPanel pPlacaBase;
	private JPanel pGrafica;
	private JPanel pKilometros;
	private JPanel pCaballos;
	private JPanel pAnyo;
	private JPanel pMarca;
	private JPanel pModelo;
	private String email,nombre;
	private JTextField tfMarca;
	private JTextField tfModelo;
	private ProductoController pc;
	
	/** Constructor de la ventana
	 * @param pc controller de esta ventana donde se almacenan los metodos que se utilizan en esta
	 * @param c el cliente de la aplicacion
	 * @param webTarget para hacer llamadas al servidor
	 * @param email email del usuario que ha iniciado sesion
	 */
	public VentanaProducto(ProductoController pc,Client c, WebTarget webTarget, String email) {
		try {
			client=c;
			this.webTarget=webTarget;
			pCambiante= new JPanel();
			this.pc=pc;
			pCambiante.setLayout(new BoxLayout(pCambiante, BoxLayout.Y_AXIS));
			List<Categoria>categorias=pc.getCategoria();
			this.email=email;
			DefaultComboBoxModel<Categoria> mCategorias= new DefaultComboBoxModel<>();
			mCategorias.addAll(categorias);
			
			cbCategoria= new JComboBox<>(mCategorias);
			
			JLabel lCategoria= new JLabel("Seleccione una categoria ");
			pCategoria= new JPanel();
			pCategoria.setLayout(new FlowLayout(FlowLayout.LEFT));
			pCategoria.add(lCategoria);
			pCategoria.add(cbCategoria);
			cbCategoria.setSelectedIndex(0);
			
			pNombre= new JPanel();
			pNombre.setLayout(new FlowLayout(FlowLayout.LEFT));
			tfNombre= new JTextField(20);
			JLabel lNombre= new JLabel("Nombre del producto: ");
			pNombre.add(lNombre);
			pNombre.add(tfNombre);
			
			pPrecio= new JPanel();
			pPrecio.setLayout(new FlowLayout(FlowLayout.LEFT));
			tfPrecio= new JTextField(20);
			JLabel lPrecio= new JLabel("Precio: ");
			pPrecio.add(lPrecio);
			pPrecio.add(tfPrecio);
			
			
			pRam= new JPanel();
			pRam.setLayout(new FlowLayout(FlowLayout.LEFT));
			tfRam= new JTextField(20);
			JLabel lRam= new JLabel("Memoria RAM: ");
			pRam.add(lRam);
			pRam.add(tfRam);
			
			
			pMemoria= new JPanel();
			pMemoria.setLayout(new FlowLayout(FlowLayout.LEFT));
			tfMemoria= new JTextField(20);
			JLabel lMemoria= new JLabel("Memoria Secundaria: ");
			pMemoria.add(lMemoria);
			pMemoria.add(tfMemoria);
			
			
			pCpu= new JPanel();
			pCpu.setLayout(new FlowLayout(FlowLayout.LEFT));
			tfCpu=new JTextField(20);
			JLabel lCpu= new JLabel("CPU: ");
			pCpu.add(lCpu);
			pCpu.add(tfCpu);
			
			pPlacaBase= new JPanel();
			pPlacaBase.setLayout(new FlowLayout(FlowLayout.LEFT));
			tfPlacaBase=new JTextField(20);
			JLabel lPlacaBase= new JLabel("Placa Base: ");
			pPlacaBase.add(lPlacaBase);
			pPlacaBase.add(tfPlacaBase);
			
			
			pGrafica= new JPanel();
			pGrafica.setLayout(new FlowLayout(FlowLayout.LEFT));
			tfGrafica= new JTextField(20);
			JLabel lGrafica= new JLabel("Tarjeta Gráfica: ");
			pGrafica.add(lGrafica);
			pGrafica.add(tfGrafica);
			
			pKilometros= new JPanel();
			pKilometros.setLayout(new FlowLayout(FlowLayout.LEFT));
			tfKilometros= new JTextField(20);
			JLabel lKilometros= new JLabel("KM : ");
			pKilometros.add(lKilometros);
			pKilometros.add(tfKilometros);

			pCaballos= new JPanel();
			pCaballos.setLayout(new FlowLayout(FlowLayout.LEFT));
			tfCaballos= new JTextField(20);
			JLabel lCaballos= new JLabel("CV : ");
			pCaballos.add(lCaballos);
			pCaballos.add(tfCaballos);
			
			pAnyo= new JPanel();
			pAnyo.setLayout(new FlowLayout(FlowLayout.LEFT));
			tfAnyo= new JTextField(20);
			JLabel lAnyo= new JLabel("Año de fabricacion: ");
			pAnyo.add(lAnyo);
			pAnyo.add(tfAnyo);
			
			pMarca= new JPanel();
			pMarca.setLayout(new FlowLayout(FlowLayout.LEFT));
			 tfMarca= new JTextField(20);
			JLabel lMarca= new JLabel("Marca: ");
			pMarca.add(lMarca);
			pMarca.add(tfMarca);
			
			pModelo= new JPanel();
			pModelo.setLayout(new FlowLayout(FlowLayout.LEFT));
			 tfModelo= new JTextField(20);
			JLabel lModelo= new JLabel("Tarjeta Gráfica: ");
			pModelo.add(lModelo);
			pModelo.add(tfModelo);
		
			lError= new JLabel();
			lError.setForeground(Color.RED);
			
			pCambiante.add(pCategoria);
			pCambiante.add(pNombre);
			pCambiante.add(pPrecio);
	
			pCambiante.add(pRam);
			pCambiante.add(pGrafica);
			pCambiante.add(pPlacaBase);
			pCambiante.add(pCpu);
			pCambiante.add(pMemoria);
			
			JButton bCrear= new JButton("Crear");
			JPanel pCrear=new JPanel();
			pCrear.add(bCrear);
			bCrear.addActionListener(new ActionListener()
		     {
		         public void actionPerformed (ActionEvent e)
		         {
		            String nombre=tfNombre.getText();
		            Categoria c=(Categoria)cbCategoria.getSelectedItem();
		            if(c.getId()==1) {
		            	ProductoOrdenador p=new ProductoOrdenador();
		            	p.setNombre(nombre);
		            	p.setCategoria(c);
		            	try {
			            	double precio=Double.parseDouble(tfPrecio.getText());
			            	p.setPrecioSalida(precio);
			            }catch(NumberFormatException ex) {
			            	lError.setText("El formato del precio no es adecuado");
			            	return;
			            }
		            	p.setCpu(tfCpu.getText());
		            	p.setGrafica(tfGrafica.getText());
		            	try {
			            	int memoria=Integer.parseInt(tfMemoria.getText());
			            	p.setMemoria(memoria);
			            }catch(NumberFormatException ex) {
			            	lError.setText("El formato de la memoria no es adecuado");
			            	return;
			            }
		            	p.setPlacaBase(tfPlacaBase.getText());
		            	try {
			            	int ram=Integer.parseInt(tfRam.getText());
			            	p.setRam(ram);
			            }catch(NumberFormatException ex) {
			            	lError.setText("El formato de la ram no es adecuado");
			            	return;
			            }
			           pc.addProductoOrdenador(p);
		            }else if(c.getId()==2) {
			            	ProductoVehiculo p=new ProductoVehiculo();
			            	p.setNombre(nombre);
			            	p.setCategoria(c);
			            	try {
				            	double precio=Double.parseDouble(tfPrecio.getText());
				            	p.setPrecioSalida(precio);
				            }catch(NumberFormatException ex) {
				            	lError.setText("El formato del precio no es adecuado");
				            	return;
				            }
			            	p.setMarca(tfMarca.getText());
			            	p.setModelo(tfModelo.getText());
			            	try {
				            	int km=Integer.parseInt(tfKilometros.getText());
				            	p.setKilometros(km);
				            }catch(NumberFormatException ex) {
				            	lError.setText("El formato de los km no es adecuado");
				            	return;
				            }
			            	try {
				            	int cv=Integer.parseInt(tfCaballos.getText());
				            	p.setCaballos(cv);
				            }catch(NumberFormatException ex) {
				            	lError.setText("El formato de los cv no es adecuado");
				            	return;
				            }
			            	try {
				            	int anyo=Integer.parseInt(tfAnyo.getText());
				            	p.setAnyoFabri(anyo);
				            }catch(NumberFormatException ex) {
				            	lError.setText("El formato del año de fabricacion no es adecuado");
				            	return;
				            }
				           pc.addProductoVehiculo(p);       
		            }
		            dispose();
		            VentanaCompras vc= new VentanaCompras(new ComprasController(webTarget,email),VentanaProducto.this.client, webTarget, email);
		            
		         
		      }
		    });
			
			JButton bVolver= new JButton("Volver atras");
			bVolver.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					VentanaCompras vc= new VentanaCompras(new ComprasController(webTarget, email), c, webTarget, email);
					VentanaProducto.this.dispose();
				}
				
				
			});
			pCrear.add(bVolver);
			getContentPane().add(lError,BorderLayout.NORTH);
			getContentPane().add(pCambiante,BorderLayout.CENTER);
			getContentPane().add(pCrear,BorderLayout.SOUTH);
			
			setLocationRelativeTo(null);
			this.pack();
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setVisible(true);
			
			
			
			cbCategoria.addItemListener (new ItemListener () {

				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange()==ItemEvent.SELECTED) {
						pCambiante.removeAll();
						pCambiante.add(pCategoria);
						pCambiante.add(pNombre);
						pCambiante.add(pPrecio);
						if (cbCategoria.getSelectedIndex()==0) {
							pCambiante.add(pRam);
							pCambiante.add(pGrafica);
							pCambiante.add(pPlacaBase);
							pCambiante.add(pCpu);
							pCambiante.add(pMemoria);
							
						}else {
							pCambiante.add(pModelo);
							pCambiante.add(pModelo);
							pCambiante.add(pKilometros);
							pCambiante.add(pAnyo);
							pCambiante.add(pCaballos);
						}
						pCambiante.revalidate();
						pCambiante.repaint();
					}
					
				}
			});
			
		}catch(ReventaException e) {
			System.out.println("*Error al recopilar las categorias* " + e.getMessage());
		}
	}
	
	

	
	
	
	

	
}
