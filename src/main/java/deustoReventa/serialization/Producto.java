package deustoReventa.serialization;

import java.util.Date;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Producto {
	
	@PrimaryKey
	private int id;
	private double precioSalida;
	private String nombre;
	private Date fechaPubli;
	
	@Persistent(defaultFetchGroup = "true")
	@Join
	private Categoria categoria;
	
	@Persistent(defaultFetchGroup = "true")
	@Join
	private Usuario vendedor;
	private boolean reservado;

	public double getPrecioSalida() {
		return precioSalida;
	}
	public void setPrecioSalida(double precioSalida) {
		this.precioSalida = precioSalida;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFechaPubli() {
		return fechaPubli;
	}
	public void setFechaPubli(Date fechaPubli) {
		this.fechaPubli = fechaPubli;
	}
	public Usuario getVendedor() {
		return vendedor;
	}
	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}
	public boolean isReservado() {
		return reservado;
	}
	public void setReservado(boolean reservado) {
		this.reservado = reservado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
