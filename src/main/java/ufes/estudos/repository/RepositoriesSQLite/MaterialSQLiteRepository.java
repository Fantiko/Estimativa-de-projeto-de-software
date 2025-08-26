package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Material;
import ufes.estudos.dao.MaterialDAO;
import ufes.estudos.repository.RepositoriesIntefaces.MaterialRepository;
import java.util.List;
import java.util.Optional;

public class MaterialSQLiteRepository implements MaterialRepository {
    private final MaterialDAO materialDAO;

    public MaterialSQLiteRepository(SQLiteConnectionManager connectionManager) {
        this.materialDAO = new MaterialDAO(connectionManager);
    }

    @Override
    public void adicionar(Material material) {
        materialDAO.insert(material);
    }

    @Override
    public Optional<Material> buscarPorId(int id) {
        return Optional.ofNullable(materialDAO.getById(id));
    }

    @Override
    public Optional<Material> buscarPorNome(String nome) {
        return Optional.ofNullable(materialDAO.getByName(nome));
    }

    @Override
    public List<Material> buscarTodos() {
        return materialDAO.getAll();
    }
}