package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.dao.InsigniasDAO;
import ufes.estudos.repository.RepositoriesIntefaces.InsigniasRepository;

import java.util.List;

public class InsigniaCompradorSQLiteRepository implements InsigniasRepository {
    private final InsigniasDAO insigniasDAO;

    // Construtor corrigido para receber o gerenciador de conexão
    public InsigniaCompradorSQLiteRepository(SQLiteConnectionManager connectionManager) {
        this.insigniasDAO = new InsigniasDAO(connectionManager);
    }

    @Override
    public void adicionar(String nome, String descricao) {
        insigniasDAO.insert(nome, descricao);
    }

    // --- MÉTODO QUE FALTAVA ADICIONADO AQUI ---
    @Override
    public List<Insignia> buscarTodas() {
        return insigniasDAO.getAll();
    }

    @Override
    public int contarInsignias() {
        return buscarTodas().size();
    }

    @Override
    public void removerInsignia(int idPerfil, int idInsignia) {
        // Lógica futura para remover a relação entre a insígnia e o perfil
        System.out.println("Funcionalidade 'removerInsignia' ainda não implementada.");
    }
}