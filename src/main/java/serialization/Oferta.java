package serialization;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/**
 * 
 * @author jonzp
 *clase oferta
 *
 */
@PersistenceCapable(detachable = "true")
public class Oferta {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private double cantidad;
	private long fecha;
	private boolean estado;
	private String emailEmisor;
	
//	@Persistent(defaultFetchGroup = "true")
//	private Usuario ofertor;
	
//	@Persistent(defaultFetchGroup = "true")
//	private Producto producto;
	
	/**
	 * 
	 * @return devuelve el id
	 * 
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param modifica el id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return devuelve la cantidad
	 * 
	 */
	public double getCantidad() {
		return cantidad;
	}
	/**
	 * 
	 * @param modifica cantidad
	 * 
	 */
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * 
	 * @return devuelve la fecha
	 * 
	 */
	public long getFecha() {
		return fecha;
	}
	/**
	 * 
	 * @param modifica la  fecha
	 * 
	 */
	public void setFecha(long fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return devuelve el estado
	 */
	public boolean isEstado() {
		return estado;
	}
	/**
	 * 
	 * @param modifica el estado
	 * 
	 */
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	/**
	 * 
	 * @return devuelve el email del emisor
	 * 
	 */
	public String getEmailEmisor() {
		return emailEmisor;
	}
	
	/**
	 * @param modifica emailEmisor
	 */
	public void setEmailEmisor(String emailEmisor) {
		this.emailEmisor = emailEmisor;
	}
/*	public Usuario getOfertor() {
		return ofertor;
	}
	public void setOfertor(Usuario ofertor) {
		this.ofertor = ofertor;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
*/
	
}
