package ufes.estudos.repository;

import ufes.estudos.Model.transacao.Venda;
import java.util.ArrayList;
import java.util.List;

public class VendaRepository {
    private static VendaRepository instance;
    private final List<Venda> vendas;

    private VendaRepository() {
        this.vendas = new ArrayList<>();
    }

    public static VendaRepository getInstance() {
        if (instance == null) {
            instance = new VendaRepository();
        }
        return instance;
    }

    public void addVenda(Venda venda) {
        this.vendas.add(venda);
    }

    public List<Venda> getVendas() {
        return new ArrayList<>(vendas);
    }
}