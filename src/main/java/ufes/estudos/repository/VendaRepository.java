package ufes.estudos.repository;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.transacao.Venda;
import ufes.estudos.dao.VendaDAO;
import ufes.estudos.observer.Subject; // Adicionado para futuras notificações
import java.util.List;

public class VendaRepository extends Subject { // Estendido para futuras notificações
    private static VendaRepository instance;
    private final VendaDAO vendaDAO;

    private VendaRepository() {
        this.vendaDAO = new VendaDAO(new SQLiteConnectionManager());
    }

    public static VendaRepository getInstance() {
        if (instance == null) {
            instance = new VendaRepository();
        }
        return instance;
    }

    public void addVenda(Venda venda) {
        vendaDAO.insert(venda);
        // Futuramente, se alguma tela precisar ser notificada de novas vendas, a linha abaixo funcionará
        // notifyObservers("NOVA_VENDA", venda);
    }

    public List<Venda> getVendas() {
        return vendaDAO.getAll();
    }
}