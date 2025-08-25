package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Defeito;
import ufes.estudos.dao.DefeitoDAO;
import ufes.estudos.repository.RepositoriesIntefaces.DefeitoRepository;
import java.util.List;
import java.util.Optional;

public class DefeitoSQLiteRepository implements DefeitoRepository {
    private final DefeitoDAO defeitoDAO;

    public DefeitoSQLiteRepository(SQLiteConnectionManager connectionManager) {
        this.defeitoDAO = new DefeitoDAO(connectionManager);
    }

    @Override
    public List<Defeito> buscarTodos() {
        return defeitoDAO.getAll();
    }

    // Outros métodos da interface DefeitoRepository podem ser implementados aqui se necessário
}