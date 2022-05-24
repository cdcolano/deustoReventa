package serialization;


import java.util.*;

import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;



/**
 * @author jonzp
 * clase usuario
 */
@PersistenceCapable(detachable = "true")
public class Usuario {
	
	
	@PrimaryKey
	private String email;
	private String password;
	private Tarjeta tarjeta;
	private int denuncias;
	
	
	/**
	 * productos en venta del usuario
	 */
	private List<Producto> productosEnVenta;
	
	/**
	 * productos vendidos por el usuario
	 */
	private List<Producto>productosVendidos;
	
	/**
	 * reclamaciones realizadas por el usuario
	 */
	@Persistent(defaultFetchGroup = "true",dependentElement = "true")
	@Join
	private List<Reclamacion>reclamacionesHechas;
	
	
	/**
	 * productos del usuario
	 */
	@Persistent(defaultFetchGroup = "true",dependentElement = "true")
	@Join
	private List<Producto>productos;
	
	/**
	 * compras realizadas por el usuario
	 */
	@Persistent(defaultFetchGroup = "true",  dependentElement = "true")
	@Join
	private List<Compra>compras;
	
	
	/**
	 * mensajes que recibe el usaurio
	 */
	@Persistent(defaultFetchGroup = "true")
	@Join
	private List<Mensaje>mensajesRecibidos;
	
	/**
	 * mensajes enviados por el usuairo
	 */
	@Persistent(defaultFetchGroup = "true")
	@Join
	private List<Mensaje>mensajesEnviados;
	
	
	/**
	 * productos FAV 
	 */
	@Persistent(defaultFetchGroup = "true",  dependentElement = "true")
	@Join
	private List<Producto>productosFavoritos;

	
	/**
	 *  FAV que recibe vendedor
	 */
	@Persistent(defaultFetchGroup = "true",  dependentElement = "true")
	@Join
	private List<Usuario>vendedoresLike;//esta es asi seguro
	
	
	
	/**
	 * oferta realizada por un comprador  a un vendedor 
	 */
	@Persistent(defaultFetchGroup = "true", dependentElement = "true")
	@Join
	private List<Oferta>ofertasEnviadas;
	
	
	public Usuario() {
		productosVendidos= new ArrayList<>();
		productosEnVenta= new ArrayList<Producto>();
		mensajesRecibidos=new ArrayList<>();
		mensajesEnviados= new ArrayList<>();
		productosFavoritos= new ArrayList<>();
		productos= new ArrayList<>();
		compras= new ArrayList<>();
		vendedoresLike= new ArrayList<>();
		ofertasEnviadas= new ArrayList<>();
		reclamacionesHechas= new ArrayList<>();
	}
	
	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param modifica email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return devuelve xcontraseña
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param modifica la password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/** 
	 * @return los productos que hay en venta
	 */
	public List<Producto> getProductosEnVenta() {
		return productosEnVenta;
	}
	
	/**
	 * @param añade nuevos productos a la cventa
	 */
	public void addProductoEnVenta(Producto p) {
		productosEnVenta.add(p);
	}
	
	/**
	 * @param modifica los productosEnVenta
	 */
	public void setProductosEnVenta(List<Producto> productosEnVenta) {
		this.productosEnVenta = productosEnVenta;
	}
	/**
	 * @return los rproductos que hay en venta
	 */
	public List<Producto> getProductosVendidos() {
		return productosVendidos;
	}
	/**
	 * @param modifica los productosVendidos
	 */
	public void setProductosVendidos(List<Producto> productosVendidos) {
		this.productosVendidos = productosVendidos;
	}
	/**
	 * @return los mensajes enviados
	 */
	public List<Mensaje> getMensajesRecibidos() {
		return mensajesRecibidos;
	}
	/**
	 * @param modifica los mensajesRecibidos
	 */
	public void setMensajesRecibidos(List<Mensaje> mensajesRecibidos) {
		this.mensajesRecibidos = mensajesRecibidos;
	}
	/**
	 * @return devuelve la lista de mensajes enviados
	 */
	public List<Mensaje> getMensajesEnviados() {
		return mensajesEnviados;
	}
	/**
	 * @param modifica la lista mensajesEnviados
	 */
	public void setMensajesEnviados(List<Mensaje> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}
	/**
	 * @return devuelve la lista de productos FAV
	 */
	public List<Producto> getProductosFavoritos() {
		return productosFavoritos;
	}
	/**
	 * @param mdoifica la lista de productosFavoritos
	 */
	public void setProductosFavoritos(List<Producto> productosFavoritos) {
		this.productosFavoritos = productosFavoritos;
	}
	
	/**
	 * @return devuelve las reclamaciones hechas
	 */
	public List<Reclamacion> getReclamacionesHechas() {
		return reclamacionesHechas;
	}
	/**
	 * @param modifica las reclamacionesHechas
	 */
	public void setReclamacionesHechas(List<Reclamacion> reclamacionesHechas) {
		this.reclamacionesHechas = reclamacionesHechas;
	}

	/**
	 * @return devuelve las denuncias
	 */
	public int getDenuncias() {
		return denuncias;
	}
	/**
	 * @param modifica las  denuncias
	 */
	public void setDenuncias(int denuncias) {
		this.denuncias = denuncias;
	}
	/**
	 * @return devuelve la lista de usuarios con FAV
	 */
	public List<Usuario> getVendedoresLike() {
		return vendedoresLike;
	}
	/**
	 * @param modifica la  lista de usuarios vendedoresLike
	 */
	public void setVendedoresLike(List<Usuario> vendedoresLike) {
		this.vendedoresLike = vendedoresLike;
	}

	/**
	 * @return devuelve la lista de ofertas enviadas
	 */
	public List<Oferta> getOfertasEnviadas() {
		return ofertasEnviadas;
	}

	/**
	 * @param modifica las ofertasEnviadas
	 */
	public void setOfertasEnviadas(List<Oferta> ofertasEnviadas) {
		this.ofertasEnviadas = ofertasEnviadas;
	}

	/**
	 * @return devuelve la tarjeta
	 */
	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	/**
	 * @param modifica la tarjeta
	 */
	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

	/**
	 * @return devuelve la lista de compras
	 */
	public List<Compra> getCompras() {
		return compras;
	}

	/**
	 * @param modifica la lista de compras
	 */
	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	/**
	 * @return devuelve la lista de productos
	 */
	public List<Producto> getProductos() {
		return productos;
	}

	/**
	 * @param modfica la lista de productos
	 */
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
		u.getProductos().addAll(productos);
		u.getProductosFavoritos().addAll(productosFavoritos);
		u.getProductosVendidos().addAll(productosVendidos);
		u.getVendedoresLike().addAll(vendedoresLike);
		
		u.setEmail(this.email);
		u.setDenuncias(denuncias);
		u.setPassword(password);
		return u;
		
	}
	
}
