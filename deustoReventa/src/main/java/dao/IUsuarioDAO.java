package dao;

import java.util.List;

import serialization.Compra;
import serialization.Producto;
import serialization.Usuario;

public interface IUsuarioDAO{
	public void storeUsuario(Usuario usuario);
	public List<Usuario> getUsuarios();
	public void deleteAllUsuarios();
	public Usuario getUsuario(String email);
	public int getComprasSize();
	public void deleteUsuario(Usuario usuario);
	public void updateVentasUsuario(String usuario, Producto p);
	public void updateUsuario(Usuario u, Compra c);
}
