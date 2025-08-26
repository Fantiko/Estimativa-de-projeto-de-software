package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Item;
import ufes.estudos.dao.ItemDAO;
import ufes.estudos.observer.Observer;
import ufes.estudos.observer.Subject;
import ufes.estudos.repository.RepositoriesIntefaces.AnuncioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnuncioSQLiteRepository extends Subject implements AnuncioRepository {
    private final ItemDAO itemDAO;

    public AnuncioSQLiteRepository(SQLiteConnectionManager connectionManager) {
        this.itemDAO = new ItemDAO(connectionManager);
    }

    @Override
    public void addAnuncio(Item item) {
        itemDAO.insert(item);
        notifyObservers("ADD_ITEM", item);
    }

    @Override
    public void deleteAnuncio(String idc) {
        itemDAO.delete(idc);
        notifyObservers("DELETE_ITEM", idc);
    }

    @Override
    public Optional<Item> findByIdc(String idc) {
        return Optional.ofNullable(itemDAO.getByIdc(idc));
    }

    @Override
    public List<Item> getAnuncios() {
        return itemDAO.getAll();
    }

    @Override
    public List<Item> getAnunciosByVendedor(int idVendedor) {
        return getAnuncios().stream()
                .filter(item -> item.getIdVendedor() == idVendedor)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAnuncio(Item item) {
        itemDAO.update(item); // Delega para o DAO
        notifyObservers("UPDATE_ITEM", item); // Notifica a interface
    }

}