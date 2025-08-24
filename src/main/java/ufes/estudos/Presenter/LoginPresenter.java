package ufes.estudos.Presenter;

import ufes.estudos.Model.State.AdminState;
import ufes.estudos.Model.State.CompradorState;
import ufes.estudos.Model.State.IMainState;
import ufes.estudos.Model.State.VendedorState;
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.ILoginView;
import ufes.estudos.Views.MainView;
import ufes.estudos.Views.TelaCadastroEtapa1;
import ufes.estudos.Views.TelaEscolherPerfil;
import ufes.estudos.repository.PerfilRepository;
import ufes.estudos.repository.RepositoriesIntefaces.UsuarioRepository;
import ufes.estudos.repository.RepositoriesSQLite.UsuarioSQLiteRepository;
import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager; // Import necessário
import ufes.estudos.service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginPresenter {

    private final ILoginView view;
    private final UsuarioService usuarioService;

    public LoginPresenter(ILoginView view) {
        this.view = view;
        this.usuarioService = new UsuarioService(new UsuarioSQLiteRepository(new SQLiteConnectionManager()));
        this.view.setLoginListener(e -> autenticar());
        this.view.setCadastrarListener(e -> abrirTelaCadastro());
    }

    private void autenticar() {
        String username = view.getUsername();
        String password = view.getSenha();

        if (username.isBlank() || password.isBlank()) {
            view.exibirMensagem("Usuário e senha são obrigatórios.");
            return;
        }

        Optional<Usuario> optionalUsuario = usuarioService.logar(username, password);

        if (optionalUsuario.isPresent()) {
            Usuario usuarioLogado = optionalUsuario.get();

            if (usuarioLogado.isAdmin()) {
                abrirTelaPrincipal(usuarioLogado, new AdminState());
            } else if (usuarioLogado.isVendedor() && usuarioLogado.isComprador()) {
                abrirTelaEscolhaPerfil(usuarioLogado);
            } else if (usuarioLogado.isVendedor()) {
                carregarPerfilVendedor(usuarioLogado); // Método que estava faltando
                abrirTelaPrincipal(usuarioLogado, new VendedorState());
            } else if (usuarioLogado.isComprador()) {
                carregarPerfilComprador(usuarioLogado); // Método que estava faltando
                abrirTelaPrincipal(usuarioLogado, new CompradorState());
            } else {
                view.exibirMensagem("Usuário não possui um perfil de Vendedor ou Comprador. Contate o administrador.");
            }
        } else {
            view.exibirMensagem("Usuário ou senha inválidos.");
        }
    }

    // --- MÉTODOS ADICIONADOS AQUI ---
    private void carregarPerfilVendedor(Usuario usuario) {
        PerfilRepository perfilRepository = PerfilRepository.getInstance();
        if (perfilRepository.getVendedor(usuario.getNome()) == null) {
            perfilRepository.addVendedor(new PerfilVendedor(usuario));
        }
    }

    private void carregarPerfilComprador(Usuario usuario) {
        PerfilRepository perfilRepository = PerfilRepository.getInstance();
        if (perfilRepository.getComprador(usuario.getNome()) == null) {
            perfilRepository.addComprador(new PerfilComprador(usuario));
        }
    }
    // --- FIM DA ADIÇÃO ---

    private void abrirTelaEscolhaPerfil(Usuario usuarioLogado) {
        if (view instanceof JFrame) {
            ((JFrame) view).dispose();
        }
        TelaEscolherPerfil telaPerfil = new TelaEscolherPerfil();
        new EscolherPerfilPresenter(telaPerfil, usuarioLogado);
        telaPerfil.setVisible(true);
    }

    private void abrirTelaCadastro() {
        if (view instanceof JFrame) {
            ((JFrame) view).dispose();
        }
        TelaCadastroEtapa1 telaCadastroEtapa1 = new TelaCadastroEtapa1(null);
        new CadastroEtapa1Presenter(telaCadastroEtapa1, null);
        telaCadastroEtapa1.setVisible(true);
    }

    private void abrirTelaPrincipal(Usuario usuarioLogado, IMainState state) {
        if (view instanceof JFrame) {
            ((JFrame) view).dispose();
        }
        MainView mainView = new MainView();
        new MainPresenter(mainView, state, usuarioLogado);
        mainView.setVisible(true);
    }
}