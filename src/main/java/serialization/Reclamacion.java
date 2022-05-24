package serialization;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author jonzp
 *clase reclamacion
 */
@PersistenceCapable(detachable = "true")
public class Reclamacion {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private String contenido;
	private long fecha;
	private double importeReclamado;
	private String emailComprador;
	
	/**
	 * @return el id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param modifica id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return devuelve el contenido de la reclamacion
	 */
	public String getContenido() {
		return contenido;
	}
	/**
	 * @param modifica el contenido de la reclamacion
	 */
	public void setContenido(String contenido) {
		this.contenido=contenido;
	}
	/**
	 * @return devuelve la fecha
	 */
	public long getFecha() {
		return fecha;
	}
	/**
	 * @param modifica la fecha
	 */
	public void setFecha(long fecha) {
		this.fecha=fecha;
	}
	/**
	 * @return devuelve el importe reclamado
	 */
	public double getImporteReclamado() {
		return importeReclamado;
	}
	/**
	 * @param modifica el importeReclamado
	 */
	public void setImporteReclamado(double importeReclamado) {
		this.importeReclamado = importeReclamado;
	}
	/**
	 * @return devuelve el email del comprador
	 */
	public String getEmailComprador() {
		return emailComprador;
	}
	/**
	 * @param modifica el emailComprador
	 */
	public void setEmailComprador(String emailComprador) {
		this.emailComprador=emailComprador;
	}
	
	/**
	 * @param contenido que tiene la reclamacion
	 * @param importeReclamado cantidad que reclama
	 */
	public Reclamacion(String contenido,double importeReclamado) {
		this.contenido=contenido;
		this.fecha = System.currentTimeMillis();
		this.importeReclamado = importeReclamado;
	}
	
	public Reclamacion() {
		
	}
}
