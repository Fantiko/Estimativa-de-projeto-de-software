package ufes.estudos.service;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.repository.RepositoriesIntefaces.ItemRepository;
import ufes.estudos.service.ServiceInterfaces.ItemServiceInterface;

import java.util.List;
import java.util.Optional;

public class ItemService implements ItemServiceInterface {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Optional<Item> buscarPorId(String identificadorCircular) {
        return itemRepository.buscarPorId(identificadorCircular);
    }

    @Override
    public void adicionar(Item item) {
        itemRepository.adicionar(item);
    }

    @Override
    public void atualizar(Item item) {
        itemRepository.atualizar(item);
    }

    @Override
    public void remover(String identificadorCircular) {
        itemRepository.remover(identificadorCircular);
    }

    @Override
    public List<Item> buscarPorVendedorId(int vendedorId) {
        return itemRepository.buscarPorVendedorId(vendedorId);
    }

    @Override
    public int contarItensPorVendedor(int vendedorId) {
        return itemRepository.contarItensPorVendedor(vendedorId);
    }
}
