package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    void adicionar(Item item);
    void atualizar(Item item);
    void remover(String identificadorCircular);

    Optional<Item> buscarPorId(String identificadorCircular);
    List<Item> buscarPorVendedorId(int vendedorId);
    List<Item> buscarTodos();
}
