package deustoReventa.serialization;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable(detachable = "true")
public class ProductoVehiculo extends Producto{
	private int kilometros;
	private int caballos;
	private int anyoFabri;
	private String marca;
	private String modelo;
	
	
	public int getKilometros() {
		return kilometros;
	}
	public void setKilometros(int kilometros) {
		this.kilometros = kilometros;
	}
	public int getCaballos() {
		return caballos;
	}
	public void setCaballos(int caballos) {
		this.caballos = caballos;
	}
	public int getAnyoFabri() {
		return anyoFabri;
	}
	public void setAnyoFabri(int anyoFabri) {
		this.anyoFabri = anyoFabri;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
}
