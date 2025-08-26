package ufes.estudos.repository;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.transacao.Oferta;
import ufes.estudos.dao.OfertaDAO;
import ufes.estudos.observer.Subject;
import java.util.List;

public class OfertaRepository extends Subject {
    private static OfertaRepository instance;
    private final OfertaDAO ofertaDAO;

    private OfertaRepository() {
        this.ofertaDAO = new OfertaDAO(new SQLiteConnectionManager());
    }

    public static OfertaRepository getInstance() {
        if (instance == null) {
            instance = new OfertaRepository();
        }
        return instance;
    }

    public void addOferta(Oferta oferta) {
        // A lógica de remover oferta antiga do mesmo comprador é mais complexa com DB,
        // por enquanto vamos apenas adicionar. Pode ser um UPDATE OR INSERT no futuro.
        ofertaDAO.insert(oferta);
        notifyObservers("ADD_OFERTA", oferta);
    }

    public void removeOferta(Oferta oferta) {
        ofertaDAO.delete(oferta);
        notifyObservers("REMOVE_OFERTA", oferta);
    }

    public void removerOfertasPorItem(String idcItem) {
        ofertaDAO.deleteByItem(idcItem);
        notifyObservers("REMOVE_OFERTA_ITEM", idcItem);
    }

    public List<Oferta> getOfertas() {
        return ofertaDAO.getAll();
    }
}