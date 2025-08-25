package ufes.estudos.repository;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.observer.Subject;
import java.util.ArrayList;
import java.util.List;

public class AnuncioRepository extends Subject {

    private static AnuncioRepository instance;
    private final List<Item> anuncios;

    private AnuncioRepository() {
        this.anuncios = new ArrayList<>();
    }

    public static AnuncioRepository getInstance() {
        if (instance == null) {
            instance = new AnuncioRepository();
        }
        return instance;
    }

    public void addAnuncio(Item item) {
        // TODO: Adicionar verificação do limite de 30 anúncios por vendedor
        this.anuncios.add(item);
        System.out.println("Repositório notificado. Notificando observadores...");
        notifyObservers("ADD", item); // Notifica todos os observadores que houve mudança
    }

    public List<Item> getAnuncios() {
        return new ArrayList<>(anuncios); // Retorna uma cópia para evitar modificação externa
    }

    // ... (código existente)

    public void updateAnuncio(Item itemAtualizado) {
        for (int i = 0; i < anuncios.size(); i++) {
            if (anuncios.get(i).getIdentificadorCircular().equals(itemAtualizado.getIdentificadorCircular())) {
                anuncios.set(i, itemAtualizado);
                System.out.println("Repositório atualizado. Notificando observadores...");
                notifyObservers("UPDATE", itemAtualizado); // Notifica que houve mudança
                return;
            }
        }
    }

    public void deleteAnuncio(String idc) {
        boolean removed = anuncios.removeIf(item -> item.getIdentificadorCircular().equals(idc));
        if (removed) {
            System.out.println("Repositório removeu item. Notificando observadores...");
            notifyObservers("DELETE", idc); // Notifica que houve mudança
        }
    }

    public Item findByIdc(String idc) {
        return anuncios.stream()
                .filter(item -> item.getIdentificadorCircular().equals(idc))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retorna uma lista de anúncios pertencentes a um vendedor específico.
     * @param nomeVendedor O nome do vendedor para filtrar os anúncios.
     * @return Uma lista contendo apenas os itens do vendedor especificado.
     */
    // O método agora recebe um int (idVendedor) em vez de uma String
    public List<Item> getAnunciosByVendedor(int idVendedor) {
        List<Item> anunciosDoVendedor = new ArrayList<>();
        for (Item item : this.anuncios) {
            // A comparação agora é de int com int (e não precisa checar por null)
            if (item.getIdVendedor() == idVendedor) {
                anunciosDoVendedor.add(item);
            }
        }
        return anunciosDoVendedor;
    }
}