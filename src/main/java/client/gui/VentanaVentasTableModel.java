package client.gui;

import java.util.Arrays;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import serialization.Compra;

public class VentanaVentasTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final List<String> headers = Arrays.asList( "idcompra", "Opinion", "Precio");
	private List<Compra> ventas;

	public VentanaVentasTableModel(List<Compra> ventas) {
		this.ventas = ventas;
	}
	
	@Override
	public String getColumnName(int column) {
		return headers.get(column);
	}

	@Override
	public int getRowCount() {
		return ventas.size();
	}

	@Override
	public int getColumnCount() {
		return headers.size(); 
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Compra venta = ventas.get(rowIndex);
		switch (columnIndex) {
			case 0: return venta.getId();
			case 1: return venta.getOpinion();
			case 2: return venta.getPrecio();
			
			default: return null;
		}
	}

}

