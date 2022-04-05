<<<<<<< HEAD:src/main/java/deustoReventa/serialization/Compra.java
package deustoReventa.serialization;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Compra {
	
	@PrimaryKey
	private int id;
	@Persistent(defaultFetchGroup = "true")
	@Join
	private Usuario comprador;
	private Usuario vendedor;
	
	@Persistent(defaultFetchGroup = "true")
	@Join
	private Producto producto;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Usuario getComprador() {
		return comprador;
	}
	public void setComprador(Usuario comprador) {
		this.comprador = comprador;
	}
	public Usuario getVendedor() {
		return vendedor; 
	}
	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
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
	private String opinion;
	private double precio;
}
=======
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
	
	//@Persistent(defaultFetchGroup = "true")
	//@Join
	//private Usuario comprador;
	
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
//	public Usuario getComprador() {
//		return comprador;
//	}
//	public void setComprador(Usuario comprador) {
//		this.comprador = comprador;
//	}
	
}
>>>>>>> 6a1b44c2b1d1a99206621f7020096cef616667e4:deustoReventa/src/main/java/serialization/Compra.java
