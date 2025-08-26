package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.dao.InsigniasDAO;
import ufes.estudos.repository.RepositoriesIntefaces.InsigniasRepository;

import java.util.List;

public class InsigniasSQLiteRepository implements InsigniasRepository {
    private final InsigniasDAO insigniasDAO;

    public InsigniasSQLiteRepository(SQLiteConnectionManager connectionManager) {
        this.insigniasDAO = new InsigniasDAO(connectionManager);
    }

    @Override
    public void adicionar(String nome, String descricao) {
        insigniasDAO.insert(nome, descricao);
    }

    @Override
    public List<Insignia> buscarTodas() {
        return insigniasDAO.getAll();
    }

    @Override
    public int contarInsignias() {
        return buscarTodas().size();
    }

    // --- MÉTODO ADICIONADO PARA RESOLVER O ERRO ---
    // A interface exige este método, então o adicionamos.
    // A lógica interna pode ser implementada no futuro.
    @Override
    public void removerInsignia(int idPerfil, int idInsignia) {
        // TODO: Implementar a lógica para remover uma insígnia de um perfil no banco de dados.
        // Ex: "DELETE FROM perfilCompradorInsignias WHERE perfilCompradorId = ? AND insigniaId = ?"
        System.out.println("Funcionalidade 'removerInsignia' ainda não implementada.");
    }
}