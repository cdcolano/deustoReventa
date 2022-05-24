package serialization;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 * @author jonzp
 * clase compra con sus atributos
 * 
 */
@PersistenceCapable(detachable = "true")
public class Compra {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private String opinion;
	private double precio;
	private boolean reclamado;
	
	//@Persistent(defaultFetchGroup = "true")
	//@Join
	//private Usuario comprador;
	
	private int idCat;
	
	/**
	 * 
	 * @param idCat 
	 * modifica el idcCat de la clase compra
	 * 
	 */
	public void setIdCat(int idCat) {
		this.idCat = idCat;
	}
	private int producto;
	
	
	/**
	 * 
	 * @return devuelve el id de la compra
	 * 
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id modifica el id de la compra
	 * 
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return devuelve el producto de la compra
	 * 
	 */
	public int getProducto() {
		return producto;
	}
	/**
	 * 
	 * @param modifica el producto
	 */
	public void setProducto(int producto) {
		this.producto = producto;
	}
	/**
	 * 
	 * @return devuelve el producto 
	 * 
	 */
	public String getOpinion() {
		return opinion;
	}
	/**
	 * 
	 * @param modifica opinion
	 * 
	 */
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	/**
	 * 
	 * @return devuelve el precio de la compra
	 * 
	 */
	public double getPrecio() {
		return precio;
	}
	/**
	 * @param modifica el  precio de la compra
	 * 
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}
//	public Usuario getComprador() {
//		return comprador;
//	}
//	public void setComprador(Usuario comprador) {
//		this.comprador = comprador;
//	}
	/**
	 * 
	 * @return el idCat de la compra
	 * 
	 */
	public int getIdCat() {
		// TODO Auto-generated method stub
		return idCat;
	}
	
	/**
	 * @return el booleano que indica si ha sido reclamado o no
	 */
	public boolean getReclamado() {
		return reclamado;
	}
	/**
	 * @param reclamado modifica el valor de reclamado
	 */
	public void setReclamado(boolean reclamado) {
		this.reclamado=reclamado;
	}
	
}
