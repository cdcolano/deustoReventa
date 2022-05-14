package serialization;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

public class Reclamacion {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private String contenido;
	private long fecha;
	private int importeReclamado;
	private String emailComprador;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido=contenido;
	}
	public long getFecha() {
		return fecha;
	}
	public void setFecha(long fecha) {
		this.fecha=fecha;
	}
	public int getImporteReclamado() {
		return importeReclamado;
	}
	public void setImporteReclamado(int importeReclamado) {
		this.importeReclamado = importeReclamado;
	}
	public String getEmailComprador() {
		return emailComprador;
	}
	public void setEmailComprador(String emailComprador) {
		this.emailComprador=emailComprador;
	}
	
	public Reclamacion(String contenido,int importeReclamado) {
		this.contenido=contenido;
		this.fecha = System.currentTimeMillis();
		this.importeReclamado = importeReclamado;
	}
	
	public Reclamacion() {
		
	}
}
