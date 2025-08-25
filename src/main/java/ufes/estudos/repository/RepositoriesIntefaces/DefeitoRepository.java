package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Item.Defeito;
import java.util.List;

public interface DefeitoRepository {
    List<Defeito> buscarTodos();
    // No futuro, podemos adicionar outros métodos como adicionar, buscarPorNome, etc.
}