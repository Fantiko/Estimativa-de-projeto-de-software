package ufes.estudos.service.ServiceInterfaces;

import ufes.estudos.Model.Usuario.Usuario;

import java.util.Optional;

public interface UsuarioServiceInterface {
    boolean logar(String usuario, String senha);
    Optional<Usuario> registrar(Usuario usuario);

}
