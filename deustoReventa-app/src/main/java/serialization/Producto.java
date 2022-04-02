package serialization;

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
	private Date fechaPubli;
	
	@Persistent(defaultFetchGroup = "true")
	private Categoria categoria;
	
	@Persistent(defaultFetchGroup = "true", mappedBy="producto",  dependentElement = "true")
	@Join
	private List<Oferta>ofertasRecibidas;//se consigue a través del producto
	
	//@Persistent(defaultFetchGroup = "true")
	@Join
	private Usuario vendedor;
	private boolean reservado;
	private boolean vendido;

	public Producto(Usuario u, double precioSalida, String nombre) {
		vendido=false;
		reservado=false;
		vendedor=u;
		this.nombre=nombre;
		this.precioSalida=precioSalida;
		this.fechaPubli=new Date(System.currentTimeMillis());
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
	
	public boolean isVendido() {
		return vendido;
	}
	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}
	
	public Producto clone() {
		Producto p= new Producto(vendedor, precioSalida, nombre);
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

}
