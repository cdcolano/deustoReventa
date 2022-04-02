package serialization;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class ProductoOrdenador extends Producto {
	private int ram;
	private String cpu;
	private int memoria;
	private String grafica;
	private String placaBase;
	
	
	public int getRam() {
		return ram;
	}
	public void setRam(int ram) {
		this.ram = ram;
	}
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public int getMemoria() {
		return memoria;
	}
	public void setMemoria(int memoria) {
		this.memoria = memoria;
	}
	public String getGrafica() {
		return grafica;
	}
	public void setGrafica(String grafica) {
		this.grafica = grafica;
	}
	public String getPlacaBase() {
		return placaBase;
	}
	public void setPlacaBase(String placaBase) {
		this.placaBase = placaBase;
	}

}
