	package serialization;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

/**
 * @author jonzp
 * clase producto ordenador
 *
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class ProductoOrdenador extends Producto {
	private int ram;
	private String cpu;
	private int memoria;
	private String grafica;
	private String placaBase;
	
	
	/**
	 * 
	 * @return devuelve la ram del ordenador
	 * 
	 */
	public int getRam() {
		return ram;
	}
	
	/**
	 * 
	 * @param modifica ram
	 * 
	 */
	public void setRam(int ram) {
		this.ram = ram;
	}
	
	
	/**
	 * @return devuelve cpu
	 */
	public String getCpu() {
		return cpu;
	}
	

	/**
	 * 
	 * @param modifica cpu
	 * 
	 */
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	
	/**´
	 * 
	 * @return devuelve memoria
	 * 
	 */
	public int getMemoria() {
		return memoria;
	}
	
	/**
	 * 
	 * @param modifica la memoria
	 * 
	 */
	public void setMemoria(int memoria) {
		this.memoria = memoria;
	}
	
	/**
	 * @return modifica la grafica
	 */
	public String getGrafica() {
		return grafica;
	}
	
	/**
	 * @param modifica la  grafica
	 */
	public void setGrafica(String grafica) {
		this.grafica = grafica;
	}
	
	
	/**
	 * @return devuelve placa base
	 */
	
	public String getPlacaBase() {
		return placaBase;
	}
	
	
	/**
	 * @param modifica placaBase
	 */
	public void setPlacaBase(String placaBase) {
		this.placaBase = placaBase;
	}

}
