package ufes.estudos.service.ServiceInterfaces;

import ufes.estudos.Model.Usuario.Usuario;
import java.util.Optional;

public interface UsuarioServiceInterface {
    Optional<Usuario> logar(String usuario, String senha);
    Optional<Usuario> registrar(Usuario usuario);
    void atualizar(Usuario usuario);
    Optional<Usuario> buscarPorUsuario(String username);
    long totalUsuarios();
}