package serialization;

import javax.jdo.annotations.PersistenceCapable;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

/**
 * @author jonzp
 * clase producto vehiculo
 *
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class ProductoVehiculo extends Producto{
	private int kilometros;
	private int caballos;
	private int anyoFabri;
	private String marca;
	private String modelo;
	
	
	/**
	 * @return devuelve km
	 */
	public int getKilometros() {
		return kilometros;
	}
	/**
	 * @param modfica kilometros
	 */
	public void setKilometros(int kilometros) {
		this.kilometros = kilometros;
	}
	/**
	 * @return devuelve caballos
	 */
	public int getCaballos() {
		return caballos;
	}
	/**
	 * @param modifica los caballos
	 */
	public void setCaballos(int caballos) {
		this.caballos = caballos;
	}
	/**
	 * @return devuelve año fabricacion
	 */
	public int getAnyoFabri() {
		return anyoFabri;
	}
	/**
	 * @param devuelve anyoFabri
	 */
	public void setAnyoFabri(int anyoFabri) {
		this.anyoFabri = anyoFabri;
	}
	/**
	 * @return devuelve la marca
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * @param modifica la marca
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}
	/**
	 * @return devuelve el modelo
	 */
	public String getModelo() {
		return modelo;
	}
	/**
	 * @param modifica el modelo
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
}
