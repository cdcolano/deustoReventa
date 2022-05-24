package client.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import client.controller.ComprasController;
import client.controller.VentasController;
import client.controller.VerComprasController;
import serialization.Compra;
import serialization.Producto;
import serialization.Reclamacion;
import util.ReventaException;

/** Ventana que muestra las compras realizadas por el usuario que ha iniciado sesion
 * @author Jon Eguiluz
 *
 */
public class VentanaVerCompras extends JFrame {
	
	private JButton btnCerrar;
	private JPanel pCentro,pSur;
	private Client client;
	private String em1;
	private WebTarget webTarget;
	private List<Compra> compras;

	private VerComprasController vc1;

	
	
	/** Constructor de la ventana
	 * @param vcc controller de esta ventana donde se almacenan los metodos que se utilizan en esta
	 * @param c el cliente de la aplicacion
	 * @param wt para hacer llamadas al servidor
	 * @param email email del usuario que ha iniciado sesion
	 */
	public VentanaVerCompras(VerComprasController vcc, Client c, WebTarget wt, String email) {
		
		this.client=c;
		this.em1=email;
		this.webTarget=wt;
		vc1=vcc;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		
		//List<Compra> compras=new ArrayList<Compra>();
		try {
			compras = vc1.getListaProductosComprados(email);
		} catch (ReventaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			pCentro = new JPanel();
			pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
			for(Compra compra: compras) {
				
				
				
				Producto m=vc1.getPoducto(compra);
				JPanel pDate= new JPanel();
				JPanel pNombre= new JPanel();
				JPanel pDinero= new JPanel();
				JPanel pProducto= new JPanel();
				JPanel pBotones = new JPanel();
				JPanel pDerecha = new JPanel();
				JPanel pAbajo = new JPanel();
				JPanel pBotones2 = new JPanel();
				pProducto.setLayout(new BoxLayout(pProducto, BoxLayout.Y_AXIS));
				pDate.setLayout(new FlowLayout( FlowLayout.CENTER));
				pDinero.setLayout(new FlowLayout( FlowLayout.CENTER));
				pNombre.setLayout(new FlowLayout( FlowLayout.CENTER));
				pBotones.setLayout(new FlowLayout( FlowLayout.CENTER));
				pDerecha.setLayout(new FlowLayout( FlowLayout.CENTER));
				JTextField razon = new JTextField(20);
				JTextField opinion = new JTextField(20);
				JButton reclamar = new JButton("Reclamar");
				if(compra.getReclamado()==true) {
					reclamar.setEnabled(false);
					razon.setText("El producto ha sido reclamado");
					razon.setEditable(false);
				}
				JButton btnDenuncia = new JButton("Denunciar");
				JButton btnOpinion = new JButton("Dar Opinion");
				Date date = new Date(m.getFechaPubli());
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
				String p = df.format(date);
				pDate.add(new JLabel(p));
				pNombre.add(new JLabel(m.getNombre()));
				pDinero.add(new JLabel(""+compra.getPrecio()));
				pBotones.add(reclamar);
				pBotones.add(btnDenuncia);
				pDerecha.add(new JLabel("Motivo de reclamcion:"));
				pDerecha.add(razon);
				pAbajo.add(new JLabel("Danos tu opinion:"));
				pAbajo.add(opinion);
				pBotones2.add(btnOpinion);
				pProducto.add(pNombre);
				pProducto.add(pDate);
				pProducto.add(pDinero);
				pProducto.add(pDerecha);
				pProducto.add(pBotones);
				pProducto.add(pAbajo);
				pProducto.add(pBotones2);
				pCentro.add(pProducto);
				
				
				
				
				btnDenuncia.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							vc1.denunciar(m);
						} catch (ReventaException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				reclamar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Reclamacion r = new Reclamacion(razon.getText(),compra.getPrecio());
						r.setEmailComprador(email);
						razon.setText("El producto ha sido reclamado");
						reclamar.setEnabled(false);
						razon.setEditable(false);
						compra.setReclamado(true);
						try {
							vc1.addReclamacion(r,compra.getId());
						} catch (ReventaException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						pProducto.revalidate();
					}
					
				});
				btnOpinion.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						compra.setOpinion(opinion.getText());
						opinion.setText("");
						System.out.println(compra.getOpinion());
						
						
					}
					
					
				});
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
			
		JScrollPane jsp= new JScrollPane(pCentro);
		
		
		
		pSur = new JPanel();
		btnCerrar = new JButton("VOLVER ATRAS");
		pSur.add(btnCerrar);
		
		
		btnCerrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VentanaCompras vc= new VentanaCompras(new ComprasController(webTarget,email),c, wt, email);
				VentanaVerCompras.this.dispose();
			}
		});
		
	
		
		getContentPane().add(jsp,BorderLayout.CENTER);
		getContentPane().add(pSur,BorderLayout.SOUTH);
		
		
		
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		
	}

}
