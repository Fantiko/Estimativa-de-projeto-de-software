package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Usuario.Insignia;

import java.util.List;

public interface InsigniasRepository {
    void adicionar(String nome, String descricao);
    Insignia buscarDescricao(String nome);
    List<Insignia> buscarTodas();
    int contarInsignias();
}
