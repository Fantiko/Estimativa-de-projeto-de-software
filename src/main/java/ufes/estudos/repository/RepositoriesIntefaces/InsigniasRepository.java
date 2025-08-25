package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Usuario.Insignia;

import java.util.List;

public interface InsigniasRepository {
    void adicionarInsignia(int perfilId, int insigniaId);
    String buscarDescricao(int idInsignea);
    List<Insignia> buscarInsignias(int perfilId);
    int contarInsignias();
    List<Insignia> listarTodasInsignias();

    void removerInsignia(int perfilId, int insigniaId);
}
