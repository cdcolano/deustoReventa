package deustoReventa.serialization;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Unique;

@PersistenceCapable(detachable = "true")
public class Tarjeta {
	
	@Unique
	private String nombre;
	private int codigoSecreto;
	private int mesVencimiento;
	private int anyoVencimiento;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCodigoSecreto() {
		return codigoSecreto;
	}
	public void setCodigoSecreto(int codigoSecreto) {
		this.codigoSecreto = codigoSecreto;
	}
	public int getMesVencimiento() {
		return mesVencimiento;
	}
	public void setMesVencimiento(int mesVencimiento) {
		this.mesVencimiento = mesVencimiento;
	}
	public int getAnyoVencimiento() {
		return anyoVencimiento;
	}
	public void setAnyoVencimiento(int anyoVencimiento) {
		this.anyoVencimiento = anyoVencimiento;
	}
}
