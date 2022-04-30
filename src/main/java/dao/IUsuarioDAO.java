package dao;

import java.util.List;

import serialization.Compra;
import serialization.Producto;
import serialization.Usuario;

public interface IUsuarioDAO{
	public void storeUsuario(Usuario usuario);
	public List<Usuario> getUsuarios();
	public Usuario getUsuario(String email);
}
