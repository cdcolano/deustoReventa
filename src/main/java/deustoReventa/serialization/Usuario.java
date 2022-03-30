package deustoReventa.serialization;
import java.util.*;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(detachable = "true")
public class Usuario {
	private String email;
	private String password;
	private Tarjeta tarjeta;
	
	private List<Producto> productosEnVenta;
	
	private List<Producto>productosVendidos;
	
	private List<Producto>productosComprados;
	
	private List<Mensaje>mensajesRecibidos;
	

	private List<Mensaje>mensajesEnviados;
	
	@Persistent(defaultFetchGroup = "true")
	@Join
	private List<Producto>productosFavoritos;

	
	private int denuncias;
	@Persistent(defaultFetchGroup = "true")
	@Join
	private List<Usuario>vendedoresLike;//esta es asi seguro
	
	private List<Oferta>ofertasRecibidas;//se consigue a través del producto
	
	
	private List<Oferta>ofertasEnviadas;
	
	public Usuario() {
		productosVendidos= new ArrayList<>();
		productosEnVenta= new ArrayList<Producto>();
		mensajesRecibidos=new ArrayList<>();
		mensajesEnviados= new ArrayList<>();
		productosFavoritos= new ArrayList<>();
		productosComprados= new ArrayList<>();
		vendedoresLike= new ArrayList<>();
		ofertasRecibidas= new ArrayList<>();
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Producto> getProductosEnVenta() {
		return productosEnVenta;
	}
	public void setProductosEnVenta(List<Producto> productosEnVenta) {
		this.productosEnVenta = productosEnVenta;
	}
	public List<Producto> getProductosVendidos() {
		return productosVendidos;
	}
	public void setProductosVendidos(List<Producto> productosVendidos) {
		this.productosVendidos = productosVendidos;
	}
	public List<Mensaje> getMensajesRecibidos() {
		return mensajesRecibidos;
	}
	public void setMensajesRecibidos(List<Mensaje> mensajesRecibidos) {
		this.mensajesRecibidos = mensajesRecibidos;
	}
	public List<Mensaje> getMensajesEnviados() {
		return mensajesEnviados;
	}
	public void setMensajesEnviados(List<Mensaje> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}
	public List<Producto> getProductosFavoritos() {
		return productosFavoritos;
	}
	public void setProductosFavoritos(List<Producto> productosFavoritos) {
		this.productosFavoritos = productosFavoritos;
	}
	public List<Producto> getProductosComprados() {
		return productosComprados;
	}
	public void setProductosComprados(List<Producto> productosComprados) {
		this.productosComprados = productosComprados;
	}
	public int getDenuncias() {
		return denuncias;
	}
	public void setDenuncias(int denuncias) {
		this.denuncias = denuncias;
	}
	public List<Usuario> getVendedoresLike() {
		return vendedoresLike;
	}
	public void setVendedoresLike(List<Usuario> vendedoresLike) {
		this.vendedoresLike = vendedoresLike;
	}
	public List<Oferta> getOfertasRecibidas() {
		return ofertasRecibidas;
	}
	public void setOfertasRecibidas(List<Oferta> ofertasRecibidas) {
		this.ofertasRecibidas = ofertasRecibidas;
	}

	public List<Oferta> getOfertasEnviadas() {
		return ofertasEnviadas;
	}

	public void setOfertasEnviadas(List<Oferta> ofertasEnviadas) {
		this.ofertasEnviadas = ofertasEnviadas;
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}
	
}
