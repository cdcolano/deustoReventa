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
	

	@Persistent(defaultFetchGroup = "true", dependentElement = "true")
	@Join
	private List<Oferta>ofertasRecibidas;//se consigue a través del producto
	
	//@Persistent(defaultFetchGroup = "true")

	private boolean reservado;
	private boolean vendido;
	private String emailVendedor;
	
	public Producto(double precioSalida, String nombre, Categoria c) {
		vendido=false;
		reservado=false;
		this.categoria=c;
		this.nombre=nombre;
		this.precioSalida=precioSalida;
		this.fechaPubli=System.currentTimeMillis();
		ofertasRecibidas=new ArrayList<>();
	}
	
	public Producto() {
		vendido=false;
		reservado=false;
		}
	
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
	public long getFechaPubli() {
		return fechaPubli;
	}
	public void setFechaPubli(long fechaPubli) {
		this.fechaPubli = fechaPubli;
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
	
	public boolean isVendido() {
		return vendido;
	}
	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}
	
	public Producto clone() {
		Producto p= new Producto(precioSalida, nombre, categoria);
		p.setVendido(this.vendido);
		p.setReservado(this.reservado);
		p.setId(this.id);
		return p;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Oferta> getOfertasRecibidas() {
		return ofertasRecibidas;
	}

	public void setOfertasRecibidas(List<Oferta> ofertasRecibidas) {
		this.ofertasRecibidas = ofertasRecibidas;
	}

	public String getEmailVendedor() {
		return emailVendedor;
	}

	public void setEmailVendedor(String emailVendedor) {
		this.emailVendedor = emailVendedor;
	}

	

}
