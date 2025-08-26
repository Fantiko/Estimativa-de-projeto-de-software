package ufes.estudos.service.ServiceInterfaces;

import ufes.estudos.Model.Item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemServiceInterface {

    Optional<Item> buscarPorId(String identificadorCircular);
    void adicionar(Item item);

    void atualizar(Item item);

    void remover(String identificadorCircular);

    List<Item> buscarPorVendedorId(int vendedorId);

    int contarItensPorVendedor(int vendedorId);



}
