package client.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import client.controller.ChatController;
import client.controller.ComprasController;
import client.controller.MisProductosController;
import client.controller.ProductoController;
import client.controller.VentasController;
import dao.UsuarioDAO;
import serialization.Producto;
import serialization.Usuario;
import service.VentasService;
import util.ReventaException;

/**Ventana donde se muestran los productos de un usuario
 * @author Ander
 *
 */
public class VentanaMisProductos extends JFrame {

	private Client client;
	private WebTarget webTarget;
	private String email;
	private List<Producto> productos;
	private MisProductosController controller;
	private static VentanaMisProductos v1;
	private JPanel pNorte;
	private JPanel pCentro;
	private JPanel pCategorias;
	private JPanel pOrdenar;

	
	/**Metodo que construye la ventana de las compras de un usuario
	 * @param cc controller para acceder a los metodos de la ventana
	 * @param cliente el cliente de la aplicacion
	 * @param webTarget para hacer las llamadas al server
	 * @param email email del usuario
	 */
	public VentanaMisProductos(MisProductosController cc, Client cliente, WebTarget webTarget, String email) {
		this.client=cliente;
		this.webTarget=webTarget;
		this.email=email;
		this.controller= cc;
		v1 = this;
		pCentro= new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));
		try {
		productos=controller.getProductosEnVentaConReservado();
		controller.setProductos(productos);
		System.out.println(productos);
		for (Producto p:productos) {
			if(p.getEmailVendedor().equals(email)) {
				System.out.println("Entro");
				controller.crearPanel(p, pCentro);
			}
		}
		} catch (ReventaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JScrollPane jsc= new JScrollPane(pCentro);
	
		v1.getContentPane().add(jsc,BorderLayout.CENTER);
		JPanel pVender= new JPanel();
		JButton bCerrar = new JButton("Volver atras");
		bCerrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				VentanaCompras v = new VentanaCompras(new ComprasController(webTarget, email),VentanaMisProductos.this.client,VentanaMisProductos.this.webTarget,email);
				v1.dispose();
			}
			
		});
		pVender.add(bCerrar);
		v1.getContentPane().add(pVender, BorderLayout.SOUTH);
		this.pack();
		v1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		//TODO desplegar todos los productos
		//TODO coger el producto seleccionado
	}
}
