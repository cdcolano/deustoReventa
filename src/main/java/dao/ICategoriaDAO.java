package dao;

import java.util.List;

import serialization.Categoria;

/**Interfaz que implementa el CategoriaDao
 * @author Ander
 *
 */
public interface ICategoriaDAO {
	/**Metodo que guarda una categoria en BD
	 * @param cat categoria que se guarda
	 */
	public void storeCategoria(Categoria cat);
	/**metodo que devuelve la lista de categorias
	 * @return lista de categorias
	 */
	public List<Categoria> getCategorias();
}
