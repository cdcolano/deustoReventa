package client.gui;

import javax.swing.JTable;

public class VentanaVentas {
	
	private JTable ventasTable;
	
	// crear la ventana
	
	ventasTable = new JTable();
	add(new JScrollPane(launchesJTable), BorderLayout.CENTER);

		
		ventasTable.getColumnModel().getColumn(0).setMinWidth(60);
		ventasTable.getColumnModel().getColumn(1).setMinWidth(110);
		ventasTable.getColumnModel().getColumn(2).setMinWidth(320);
		ventasTable.getColumnModel().getColumn(3).setMinWidth(20);
		ventasTable.getColumnModel().getColumn(4).setMinWidth(20);
		
	}
	
	
	

}
