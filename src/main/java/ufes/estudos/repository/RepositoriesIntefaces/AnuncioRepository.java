package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.observer.Observer;
import java.util.List;
import java.util.Optional;

public interface AnuncioRepository {
    void addAnuncio(Item item);
    void updateAnuncio(Item item);
    void deleteAnuncio(String idc);
    Optional<Item> findByIdc(String idc);
    List<Item> getAnuncios();
    List<Item> getAnunciosByVendedor(int idVendedor);
    void addObserver(Observer observer);

    // --- MÉTODO ADICIONADO AQUI ---
    void removeObserver(Observer observer);
}