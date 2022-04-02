package serialization;

import java.sql.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Mensaje {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private String contenido;
	private Date fecha;
	
	@Persistent(defaultFetchGroup = "true")
	private Usuario enviador;
	@Persistent(defaultFetchGroup = "true")
	private Usuario receptor;
	
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
		this.contenido = contenido;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Usuario getReceptor() {
		return receptor;
	}
	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}
	public Usuario getEnviador() {
		return enviador;
	}
	public void setEnviador(Usuario enviador) {
		this.enviador = enviador;
	}

}
