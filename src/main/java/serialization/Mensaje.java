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
 * Clase compra
 *
 */
@PersistenceCapable(detachable = "true")
public class Mensaje {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private String contenido;
	private long fecha;
	private String enviado;
	private String recibido;
	
	//@Persistent(defaultFetchGroup = "true")
	//private Usuario enviador;
	//@Persistent(defaultFetchGroup = "true")
	//private Usuario receptor;
	
	/**
	 * 
	 * @return email del usuario que ha enviado un mensaje
	 * 
	 */
	public String getEnviado() {
		return enviado;
	}
	/**
	 * 
	 * @param modifica email enviado 
	 * 
	 */
	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}
	
	/**
	 * 
	 * @return email del usuario que ha recibido un mensaje
	 * 
	 */
	public String getRecibido() {
		return recibido;
	}
	/**
	 * 
	 * @param modifica el email recibido 
	 * 
	 */
	public void setRecibido(String recibido) {
		this.recibido = recibido;
	}
	
	/**
	 * 
	 * @return devuelve el id del mensaje
	 * 
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param modifica el id 
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return devuelve el contenido
	 * 
	 */
	public String getContenido() {
		return contenido;
	}
	/**
	 * 
	 * @param modifica el contenido
	 * 
	 */
	public void setContenido(String contenido) {
		this.contenido = contenido;
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
	 * @param modifica la fecha
	 * 
	 */
	public void setFecha(long fecha) {
		this.fecha = fecha;
	}
//	public Usuario getReceptor() {
//		return receptor;
//	}
//	public void setReceptor(Usuario receptor) {
//		this.receptor = receptor;
/*	}
	public Usuario getEnviador() {
		return enviador;
	}
	public void setEnviador(Usuario enviador) {
		this.enviador = enviador;
	}*/

}
