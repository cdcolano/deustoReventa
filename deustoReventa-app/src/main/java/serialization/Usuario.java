package serialization;
import java.util.*;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

@PersistenceCapable(detachable = "true")
public class Usuario {
	
	@PrimaryKey
	private String email;
	private String password;
	private Tarjeta tarjeta;
	private int denuncias;
	
	
	private List<Producto> productosEnVenta;
	
	private List<Producto>productosVendidos;
	
	
	@Persistent(defaultFetchGroup = "true",dependentElement = "true", mappedBy="vendedor")
	private List<Producto>productos;
	
	@Persistent(defaultFetchGroup = "true",  dependentElement = "true", mappedBy="comprador")
	@Join
	private List<Compra>compras;
	
	
	@Persistent(defaultFetchGroup = "true",   mappedBy="receptor")
	@Join
	private List<Mensaje>mensajesRecibidos;
	
	@Persistent(defaultFetchGroup = "true",  mappedBy="enviador")
	@Join
	private List<Mensaje>mensajesEnviados;
	
	
	@Persistent(defaultFetchGroup = "true",  dependentElement = "true")
	@Join
	private List<Producto>productosFavoritos;

	
	
	@Persistent(defaultFetchGroup = "true",  dependentElement = "true")
	@Join
	private List<Usuario>vendedoresLike;//esta es asi seguro
	
	
	
	@Persistent(defaultFetchGroup = "true", mappedBy="ofertor", dependentElement = "true")
	@Join
	private List<Oferta>ofertasEnviadas;
	
	public Usuario() {
		productosVendidos= new ArrayList<>();
		productosEnVenta= new ArrayList<Producto>();
		mensajesRecibidos=new ArrayList<>();
		mensajesEnviados= new ArrayList<>();
		productosFavoritos= new ArrayList<>();
		compras= new ArrayList<>();
		vendedoresLike= new ArrayList<>();
		ofertasEnviadas= new ArrayList<>();
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
	
	public void addProductoEnVenta(Producto p) {
		productosEnVenta.add(p);
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

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	
	public Usuario clone() {
		Usuario u= new Usuario();
		u.setTarjeta(this.tarjeta);
		u.getCompras().addAll(compras);
		u.getMensajesEnviados().addAll(mensajesEnviados);
		u.getMensajesRecibidos().addAll(mensajesRecibidos);
		u.getOfertasEnviadas().addAll(ofertasEnviadas);
		u.getProductosEnVenta().addAll(productosEnVenta);
		u.getProductosFavoritos().addAll(productosFavoritos);
		u.getProductosVendidos().addAll(productosVendidos);
		u.getVendedoresLike().addAll(vendedoresLike);
		
		u.setEmail(this.email);
		u.setDenuncias(denuncias);
		u.setPassword(password);
		return u;
		
	}
	
}
