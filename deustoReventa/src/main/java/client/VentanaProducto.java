package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

import serialization.Categoria;
import serialization.Producto;
import serialization.ProductoOrdenador;
import serialization.ProductoVehiculo;
import serialization.Usuario;
import util.ReventaException;
import javax.swing.*;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.*;

public class VentanaProducto extends JFrame{
	private Client client;
	private WebTarget webTarget;
	private JPanel pCambiante;
	
	public VentanaProducto(Client c, WebTarget webTarget, String email) {
		try {
			client=c;
			this.webTarget=webTarget;
			pCambiante= new JPanel();
			pCambiante.setLayout(new BoxLayout(pCambiante, BoxLayout.Y_AXIS));
			List<Categoria>categorias=getCategoria();
			
			DefaultComboBoxModel<Categoria> mCategorias= new DefaultComboBoxModel<>();
			mCategorias.addAll(categorias);
			
			JComboBox<Categoria> cbCategoria= new JComboBox<>(mCategorias);
			
			JLabel lCategoria= new JLabel("Seleccione una categoria ");
			JPanel pCategoria= new JPanel();
			pCategoria.setLayout(new FlowLayout(FlowLayout.LEFT));
			pCategoria.add(lCategoria);
			pCategoria.add(cbCategoria);
			cbCategoria.setSelectedIndex(0);
			
			JPanel pNombre= new JPanel();
			pNombre.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfNombre= new JTextField(20);
			JLabel lNombre= new JLabel("Nombre del producto: ");
			pNombre.add(lNombre);
			pNombre.add(tfNombre);
			
			JPanel pPrecio= new JPanel();
			pPrecio.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfPrecio= new JTextField(20);
			JLabel lPrecio= new JLabel("Precio: ");
			pPrecio.add(lPrecio);
			pPrecio.add(tfPrecio);
			
			
			JPanel pRam= new JPanel();
			pRam.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfRam= new JTextField(20);
			JLabel lRam= new JLabel("Memoria RAM: ");
			pRam.add(lRam);
			pRam.add(tfRam);
			
			
			JPanel pMemoria= new JPanel();
			pMemoria.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfMemoria= new JTextField(20);
			JLabel lMemoria= new JLabel("Memoria Secundaria: ");
			pMemoria.add(lMemoria);
			pMemoria.add(tfMemoria);
			
			
			JPanel pCpu= new JPanel();
			pCpu.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfCpu=new JTextField(20);
			JLabel lCpu= new JLabel("CPU: ");
			pCpu.add(lCpu);
			pCpu.add(tfCpu);
			
			JPanel pPlacaBase= new JPanel();
			pPlacaBase.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfPlacaBase=new JTextField(20);
			JLabel lPlacaBase= new JLabel("Placa Base: ");
			pPlacaBase.add(lPlacaBase);
			pPlacaBase.add(tfPlacaBase);
			
			
			JPanel pGrafica= new JPanel();
			pGrafica.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfGrafica= new JTextField(20);
			JLabel lGrafica= new JLabel("Tarjeta Gráfica: ");
			pGrafica.add(lGrafica);
			pGrafica.add(tfGrafica);
			
			JPanel pKilometros= new JPanel();
			pKilometros.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfKilometros= new JTextField(20);
			JLabel lKilometros= new JLabel("KM : ");
			pKilometros.add(lKilometros);
			pKilometros.add(tfKilometros);

			JPanel pCaballos= new JPanel();
			pCaballos.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfCaballos= new JTextField(20);
			JLabel lCaballos= new JLabel("CV : ");
			pCaballos.add(lCaballos);
			pCaballos.add(tfCaballos);
			
			JPanel pAnyo= new JPanel();
			pAnyo.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfAnyo= new JTextField(20);
			JLabel lAnyo= new JLabel("Año de fabricacion: ");
			pAnyo.add(lAnyo);
			pAnyo.add(tfAnyo);
			
			JPanel pMarca= new JPanel();
			pMarca.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfMarca= new JTextField(20);
			JLabel lMarca= new JLabel("Marca: ");
			pMarca.add(lMarca);
			pMarca.add(tfMarca);
			
			JPanel pModelo= new JPanel();
			pModelo.setLayout(new FlowLayout(FlowLayout.LEFT));
			JTextField tfModelo= new JTextField(20);
			JLabel lModelo= new JLabel("Tarjeta Gráfica: ");
			pModelo.add(lModelo);
			pModelo.add(tfModelo);
		
			JLabel lError= new JLabel();
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
			           addProducto(p, email, lError);
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
				           addProducto(p, email, lError);
		            }
		            
		         
		      }
		    });
			
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
			
		}catch(Exception e) {
			System.out.println("*Error al recopilar las categorias* " + e.getMessage());
		}
	}
	
	
	
	//necesito un getCategorias()
	public List<Categoria> getCategoria() throws ReventaException{
		WebTarget webTarget = this.webTarget.path("reventa/categorias");
		List<Categoria>lCategorias = webTarget.request( MediaType.APPLICATION_JSON ).get( new GenericType<List<Categoria>>() {
	     } );
		
		return lCategorias;
	}
	
	public void addProducto(Producto p, String email, JLabel lError) {
		try {
			WebTarget webTarget=this.webTarget.path("/reventa/sale/"+email);
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			System.out.println(webTarget.getUri());
			Response response = invocationBuilder.post(Entity.entity(p, MediaType.APPLICATION_JSON));
		}catch(Exception e) {
			lError.setText("Ha ocurrido un error al conectar con el servidor");
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	public Usuario getUsuario(String email)throws ReventaException {
		WebTarget webTarget = this.webTarget.path("reventa/getUsuario/"+ email);
		System.out.println(webTarget.getUri());
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		if (response.getStatus() == Status.OK.getStatusCode()) {
			Usuario u = response.readEntity(Usuario.class);
			return u;
		} else {
			throw new ReventaException("" + response.getStatus());
		}
	}
	
}
