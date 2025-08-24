package ufes.estudos.service;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.repository.RepositoriesIntefaces.UsuarioRepository;
import ufes.estudos.service.ServiceInterfaces.UsuarioServiceInterface;

import java.util.Optional;

public class UsuarioService implements UsuarioServiceInterface {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean logar(String usuarioDigitado, String senhaDigitada) {
        Optional<Usuario> usuarioNoBanco = usuarioRepository.buscarPorUsuario(usuarioDigitado);

        if (usuarioNoBanco.isPresent()) {
            Usuario usuario = usuarioNoBanco.get();
            //TODO COLOCAR HASH DE SENHA AQUI
            return verificarSenha(senhaDigitada, usuario.getSenha());
        }

        return false;
    }

    @Override
    public Optional<Usuario> registrar(Usuario usuario) {
        return usuarioRepository.adicionar(usuario);
    }

    // Método auxiliar para a lógica de verificação de senha
    private boolean verificarSenha(String senhaDigitada, String senhaArmazenada) {
        // Lógica de hash e comparação aqui
        return senhaDigitada.equals(senhaArmazenada);
    }

}
