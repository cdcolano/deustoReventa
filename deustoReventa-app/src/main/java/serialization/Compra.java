package serialization;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Compra {
	
	@PrimaryKey
	private int id;
	private String opinion;
	private double precio;
	
	@Persistent(defaultFetchGroup = "true")
	@Join
	private Usuario comprador;
	
	@Persistent(defaultFetchGroup = "true")
	@Join
	private Producto producto;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public Usuario getComprador() {
		return comprador;
	}
	public void setComprador(Usuario comprador) {
		this.comprador = comprador;
	}
	
}
