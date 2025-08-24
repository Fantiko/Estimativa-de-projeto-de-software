package ufes.estudos.repository;

import ufes.estudos.Model.Usuario.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private static UsuarioRepository instance;
    private final List<Usuario> usuarios;

    private UsuarioRepository() {
        this.usuarios = new ArrayList<>();
    }

    public static UsuarioRepository getInstance() {
        if (instance == null) {
            instance = new UsuarioRepository();
        }
        return instance;
    }

    public void addUsuario(Usuario usuario) {
        // Evita duplicados
        if (findByUsername(usuario.getUsuario()) == null) {
            this.usuarios.add(usuario);
        }
    }

    public Usuario findByUsername(String username) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(username)) {
                return u;
            }
        }
        return null;
    }
}