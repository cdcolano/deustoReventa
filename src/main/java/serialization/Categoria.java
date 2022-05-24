package serialization;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author jonzp
 * Clase categoria con sus atributos 
 */
@PersistenceCapable(detachable = "true")
public class Categoria {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private int id;
	private String nombre;
	
	/**
	 * @return devuelve id de una categoria
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param modifica el id de una categoria
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return devuelve el nombre de una categoria
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param  modifica el nombre de una categoria
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	@Persistent(defaultFetchGroup = "true", mappedBy="categoria",  dependentElement = "true")
	@Join
	private List<Producto>productos;//se consigue a través del producto
	
	/**
	 * devuelve el nombre
	 */
	public String toString() {
		return nombre;
	}
}
