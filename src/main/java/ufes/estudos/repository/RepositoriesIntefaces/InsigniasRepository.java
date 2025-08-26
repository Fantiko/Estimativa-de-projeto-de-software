package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Usuario.Insignia;
import java.util.List;

public interface InsigniasRepository {
    void adicionar(String nome, String descricao);
    List<Insignia> buscarTodas();
    int contarInsignias();

    // Método que estava faltando na implementação
    void removerInsignia(int idPerfil, int idInsignia);
}