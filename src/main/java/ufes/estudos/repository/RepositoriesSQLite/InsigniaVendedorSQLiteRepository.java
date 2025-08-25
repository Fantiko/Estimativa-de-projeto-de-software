package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.repository.RepositoriesIntefaces.InsigniasRepository;

import java.util.List;

public class InsigniaVendedorSQLiteRepository implements InsigniasRepository {
    @Override
    public void adicionarInsignia(int perfilId, int insigniaId) {

    }

    @Override
    public String buscarDescricao(int idInsignea) {
        return null;
    }

    @Override
    public List<Insignia> buscarInsignias(int perfilId) {
        return null;
    }

    @Override
    public int contarInsignias() {
        return 0;
    }

    @Override
    public List<Insignia> listarTodasInsignias() {
        return null;
    }

    @Override
    public void removerInsignia(int perfilId, int insigniaId) {

    }
}
