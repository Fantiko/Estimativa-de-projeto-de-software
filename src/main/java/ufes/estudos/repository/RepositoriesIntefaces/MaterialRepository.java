package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Item.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository {
    void adicionar(Material material);
    Optional<Material> buscarPorId(int id);
    Optional<Material> buscarPorNome(String nome);
    List<Material> buscarTodos();
}
