package dao;

import java.util.List;

import serialization.Categoria;

public interface ICategoriaDAO {
	public void storeCategoria(Categoria cat);
	public List<Categoria> getCategorias();
	public void deleteAllCategorias();
}
