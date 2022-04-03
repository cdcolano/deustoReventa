package clases;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VentanaProductos extends JFrame{
	private JFrame frame;
	private JPanel panelLogo;
	private JLabel labelLogo;
	private ImageIcon logo;
	private JScrollPane panelScroll;
	private JPanel panelSur;
	private int numProductos;
	
	public VentanaProductos() {
		this.frame = new JFrame("Productos en venta");
		this.frame.setSize(750,500);
		this.panelSur = new JPanel();
		this.logo = new ImageIcon("C:\\Users\\jegui\\eclipse-workspace\\clases\\src\\logo.png");
		this.labelLogo = new JLabel(logo);
		this.panelLogo = new JPanel();
		this.panelLogo.add(labelLogo);
		this.frame.getContentPane().add(panelLogo, "North");
		
		
		
		ArrayList lista = new ArrayList();
		for(int i = 0 ; i<35;i++) {
			lista.add(i);
			numProductos ++;
		}
		
		
		this.panelSur.setLayout(new GridLayout((numProductos/3)+1,2));
		
		for(int i = 0 ; i<lista.size();i++) {
			JLabel labelProducto = new JLabel("S" +i + "\n");
			JPanel panelProducto = new JPanel();
			panelProducto.setLayout(new GridLayout(2,1));
			panelProducto.add(labelProducto);
			JButton botonComprar = new JButton("COMPRAR");
			panelProducto.add(botonComprar);
			this.panelSur.add(panelProducto);
			
		}		
		this.panelScroll = new JScrollPane(panelSur);		
		this.frame.getContentPane().add(panelScroll, "Center");
		
		
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		
		
	}
	public static void main(String[]args) {
		VentanaProductos ventana = new VentanaProductos();
	}
	
}

