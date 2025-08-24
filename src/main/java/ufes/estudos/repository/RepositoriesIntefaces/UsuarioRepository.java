package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    void adicionar(Usuario usuario);
    void atualizar(Usuario usuario);
    void remover(int id);

    Optional<Usuario> buscarPorId(int id);
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> buscarTodos();
}
