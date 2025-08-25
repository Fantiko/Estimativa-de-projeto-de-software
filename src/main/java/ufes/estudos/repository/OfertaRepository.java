package ufes.estudos.repository;

import ufes.estudos.Model.transacao.Oferta;
import ufes.estudos.observer.Subject;
import java.util.ArrayList;
import java.util.List;

public class OfertaRepository extends Subject {
    private static OfertaRepository instance;
    private final List<Oferta> ofertas;

    private OfertaRepository() {
        this.ofertas = new ArrayList<>();
    }

    public static OfertaRepository getInstance() {
        if (instance == null) {
            instance = new OfertaRepository();
        }
        return instance;
    }

    public void addOferta(Oferta oferta) {
        // --- LÓGICA CORRIGIDA AQUI ---
        // Lógica para permitir apenas uma oferta por comprador por item, agora usando IDs
        ofertas.removeIf(o -> o.getIdcItem().equals(oferta.getIdcItem()) && o.getIdComprador() == oferta.getIdComprador());
        this.ofertas.add(oferta);
        notifyObservers("ADD_OFERTA", oferta);
    }

    public List<Oferta> getOfertas() {
        return new ArrayList<>(ofertas);
    }

    public void removeOferta(Oferta oferta) {
        // --- LÓGICA CORRIGIDA AQUI ---
        ofertas.removeIf(o -> o.getIdcItem().equals(oferta.getIdcItem()) && o.getIdComprador() == oferta.getIdComprador());
        notifyObservers("REMOVE_OFERTA", oferta);
    }

    public void removerOfertasPorItem(String idcItem) {
        boolean removed = ofertas.removeIf(o -> o.getIdcItem().equals(idcItem));
        if (removed) {
            notifyObservers("REMOVE_OFERTA_ITEM", idcItem);
        }
    }
}