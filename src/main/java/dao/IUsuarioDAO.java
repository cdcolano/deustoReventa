package dao;

import java.util.List;

import deustoReventa.serialization.Usuario;

public interface IUsuarioDAO{
	public void storeUsuario(Usuario usuario);
	public List<Usuario> getUsuarios();
}
