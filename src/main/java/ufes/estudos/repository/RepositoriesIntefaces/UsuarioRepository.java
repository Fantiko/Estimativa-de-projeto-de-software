package ufes.estudos.repository.RepositoriesIntefaces;

import ufes.estudos.Model.Usuario.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> adicionar(Usuario usuario);
    void atualizar(Usuario usuario);
    void remover(int id);

    Optional<Usuario> buscarPorId(int id);
    Optional<Usuario> buscarPorUsuario(String usuario);
    List<Usuario> buscarTodos();
}
