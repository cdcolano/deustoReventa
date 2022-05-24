package dao;

import java.util.List;

import serialization.Compra;
import serialization.Producto;
import serialization.Usuario;

/** Interfaz que implementa el UsuarioDAO
 * @author Jon Eguiluz
 *
 */
public interface IUsuarioDAO{
	public void storeUsuario(Usuario usuario);
	public List<Usuario> getUsuarios();
	public Usuario getUsuario(String email);
}
