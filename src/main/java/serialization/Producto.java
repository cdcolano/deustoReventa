package serialization;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;

/**
 * 
 * @author jonzp
 * clase producto 
 *
 */
@PersistenceCapable(detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Producto {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private double precioSalida;
	private String nombre;
	private long fechaPubli;
	
	@Persistent(defaultFetchGroup = "true")
	private Categoria categoria;
	

	//@Persistent(defaultFetchGroup = "true", dependentElement = "true")
	@Persistent(defaultFetchGroup = "true")
	@Join
	private List<Oferta>ofertasRecibidas;//se consigue a través del producto
	
	//@Persistent(defaultFetchGroup = "true")

	private boolean reservado;
	private boolean vendido;
	private String emailVendedor;
	
	/**
	 * 
	 * @param precioSalida del producto
	 * @param nombre del producto
	 * @param c categoria del producto
	 * 
	 */
	public Producto(double precioSalida, String nombre, Categoria c) {
		vendido=false;
		reservado=false;
		this.categoria=c;
		this.nombre=nombre;
		this.precioSalida=precioSalida;
		this.fechaPubli=System.currentTimeMillis();
		ofertasRecibidas=new ArrayList<>();
	}
	
	/**
	 * estado del producto
	 * 
	 */
	public Producto() {
		vendido=false;
		reservado=false;
		}
	
	/**
	 * @return devuelve el precio de salida
	 */
	public double getPrecioSalida() {
		return precioSalida;
	}
	/**
	 * @param modifica el precioSalida
	 */
	public void setPrecioSalida(double precioSalida) {
		this.precioSalida = precioSalida;
	}
	/**
	 * 
	 * @return devuelve el nombre
	 * 
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param modfica el nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * 
	 * @return devuelve fecha de publicación
	 * 
	 */
	public long getFechaPubli() {
		return fechaPubli;
	}
	/**
	 * @param modifica la fechaPubli
	 */
	public void setFechaPubli(long fechaPubli) {
		this.fechaPubli = fechaPubli;
	}
	
	/**
	 * @return devuelve si el producto esta reservado o  no
	 */
	public boolean isReservado() {
		return reservado;
	}
	/**
	 * @param modifica la reservada
	 */
	public void setReservado(boolean reservado) {
		this.reservado = reservado;
	}

	/**
	 * 
	 * @return devuelve el id
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
	 * @return devuelve si el producto esta vendido o no
	 */
	public boolean isVendido() {
		return vendido;
	}
	/**
	 * @param modifica el estado de vendido
	 */
	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}
	

	/** 
	 * @return devuelve la categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * @param modifica la categoria
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * 
	 * @return devuelve las ofertas recibidas por el producto
	 * 
	 */
	public List<Oferta> getOfertasRecibidas() {
		return ofertasRecibidas;
	}

	/**
	 * 
	 * @param modifca las ofertasRecibidas
	 * 
	 */
	public void setOfertasRecibidas(List<Oferta> ofertasRecibidas) {
		this.ofertasRecibidas = ofertasRecibidas;
	}

	/**
	 * 
	 * @return devuelve el email del vendedor
	 * 
	 */
	public String getEmailVendedor() {
		return emailVendedor;
	}

	/**
	 * 
	 * @param modifica el emailVendedor
	 * 
	 */
	public void setEmailVendedor(String emailVendedor) {
		this.emailVendedor = emailVendedor;
	}
	
	public String toString() {
		return nombre + " " + precioSalida + "Euros";
	}
	
	

	

}
