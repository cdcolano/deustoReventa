package deustoReventa.serialization;

import java.sql.Date;

import javax.jdo.annotations.PersistenceCapable;


@PersistenceCapable(detachable = "true")
public class Oferta {
	private int id;
	private double cantidad;
	private Date fecha;
	private boolean estado;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Usuario getUsuario_emisor() {
		return usuario_emisor;
	}
	public void setUsuario_emisor(Usuario usuario_emisor) {
		this.usuario_emisor = usuario_emisor;
	}
	private Producto producto;
	private Usuario usuario_emisor;
}
