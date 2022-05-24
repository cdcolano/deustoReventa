package serialization;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

/**
 * @author jonzp
 *clase tarjeta
 */
@PersistenceCapable(detachable = "true")
public class Tarjeta {
	
	@PrimaryKey
	private String nombre;
	private int codigoSecreto;
	private int mesVencimiento;
	private int anyoVencimiento;
	
	
	/**
	 * @return devuelve el nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param MODIFICA el nombre 
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return devuelve el pin
	 */
	public int getCodigoSecreto() {
		return codigoSecreto;
	}
	/**
	 * @param modifica el codigoSecreto
	 */
	public void setCodigoSecreto(int codigoSecreto) {
		this.codigoSecreto = codigoSecreto;
	}
	/**
	 * @return devuelve cuando see caduca la tarjeta
	 */
	public int getMesVencimiento() {
		return mesVencimiento;
	}
	/**
	 * @param modifica el mesVencimiento de la tarjeta
	 */
	public void setMesVencimiento(int mesVencimiento) {
		this.mesVencimiento = mesVencimiento;
	}
	/**
	 * @return devuelve el año de vencimiento de la tarjeta
	 */
	public int getAnyoVencimiento() {
		return anyoVencimiento;
	}
	/**
	 * @param modifica el anyoVencimiento de la tarjeta
	 */
	public void setAnyoVencimiento(int anyoVencimiento) {
		this.anyoVencimiento = anyoVencimiento;
	}
}
