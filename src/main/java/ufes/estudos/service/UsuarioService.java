package ufes.estudos.service;

import com.pss.senha.validacao.ValidadorSenha;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.repository.RepositoriesIntefaces.UsuarioRepository;
import ufes.estudos.service.ServiceInterfaces.UsuarioServiceInterface;
import java.util.Optional;

public class UsuarioService implements UsuarioServiceInterface {
    private final UsuarioRepository usuarioRepository;
    private final ValidadorSenha validadorSenha = new ValidadorSenha();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;

    }

    @Override
    public Optional<Usuario> logar(String usuarioDigitado, String senhaDigitada) {
        Optional<Usuario> optionalUsuario = usuarioRepository.buscarPorUsuario(usuarioDigitado);

        if (optionalUsuario.isPresent()) {
            Usuario usuarioDoBanco = optionalUsuario.get();
            if (senhaDigitada.equals(usuarioDoBanco.getSenha())) {
                return optionalUsuario;
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> registrar(Usuario usuario) {
        if(usuarioRepository.buscarPorUsuario(usuario.getUsuario()).isPresent()){
            return Optional.empty();
        }
        return usuarioRepository.adicionar(usuario);
    }

    @Override
    public void atualizar(Usuario usuario) {
        usuarioRepository.atualizar(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorUsuario(String username) {
        return usuarioRepository.buscarPorUsuario(username);
    }

    @Override
    public long totalUsuarios() {
        return usuarioRepository.contarUsuarios();
    }
}