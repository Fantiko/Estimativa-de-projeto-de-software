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
import ufes.estudos.repository.UsuarioRepository;

import javax.swing.*;
import java.awt.*;

public class LoginPresenter {

    private final ILoginView view;

    public LoginPresenter(ILoginView view) {
        this.view = view;
        this.view.setLoginListener(e -> autenticar());
        this.view.setCadastrarListener(e -> abrirTelaCadastro());
    }

    private void autenticar() {
        String username = view.getUsername();
        String senha = view.getSenha();

        // Usuário Administrador
        if (username.equals("admin") && senha.equals("123")) {
            Usuario usuarioLogado = new Usuario("admin", "123", "N/A", "admin@sistema.com", "Administrador do Sistema");
            usuarioLogado.setAdmin(true);
            UsuarioRepository.getInstance().addUsuario(usuarioLogado);
            abrirTelaPrincipal(usuarioLogado, new AdminState());

            // MODIFICADO: Usuário 'user' agora é Vendedor E Comprador
        } else if (username.equals("user") && senha.equals("123")) {
            Usuario usuarioLogado = new Usuario("user", "123", "27999990001", "user@gmail.com", "Usuário Padrão");
            usuarioLogado.setVendedor(true);
            usuarioLogado.setComprador(true);
            UsuarioRepository.getInstance().addUsuario(usuarioLogado);
            abrirTelaEscolhaPerfil(usuarioLogado); // Como ele tem os 2 perfis, precisa escolher

            // NOVO: Usuário 'vendedor' é APENAS vendedor
        } else if (username.equals("vendedor") && senha.equals("123")) {
            Usuario usuarioLogado = new Usuario("vendedor", "123", "27999990002", "vendedor@gmail.com", "Vendedor Exclusivo");
            usuarioLogado.setVendedor(true);
            usuarioLogado.setComprador(false);
            UsuarioRepository.getInstance().addUsuario(usuarioLogado);
            PerfilRepository.getInstance().addVendedor(new PerfilVendedor(usuarioLogado)); // <<< ADICIONE
            abrirTelaPrincipal(usuarioLogado, new VendedorState());
             // Vai direto para o painel de vendedor

            // NOVO: Usuário 'comprador' é APENAS comprador
        } else if (username.equals("comprador") && senha.equals("123")) {
            Usuario usuarioLogado = new Usuario("comprador", "123", "27999990003", "comprador@gmail.com", "Comprador Exclusivo");
            usuarioLogado.setVendedor(false);
            usuarioLogado.setComprador(true);
            UsuarioRepository.getInstance().addUsuario(usuarioLogado);
            PerfilRepository.getInstance().addComprador(new PerfilComprador(usuarioLogado)); // <<< ADICIONE
            abrirTelaPrincipal(usuarioLogado, new CompradorState()); // Vai direto para o painel de comprador

        } else {
            JOptionPane.showMessageDialog((Component) view, "Login ou senha incorretos!");
        }
    }

    private void abrirTelaEscolhaPerfil(Usuario usuarioLogado) {
        if (view instanceof javax.swing.JFrame) {
            ((javax.swing.JFrame) view).dispose();
        }
        TelaEscolherPerfil telaPerfil = new TelaEscolherPerfil();
        new EscolherPerfilPresenter(telaPerfil, usuarioLogado);
        telaPerfil.setVisible(true);
    }

    private void abrirTelaCadastro() {
        if (view instanceof javax.swing.JFrame) {
            ((javax.swing.JFrame) view).dispose();
        }
        TelaCadastroEtapa1 telaCadastroEtapa1 = new TelaCadastroEtapa1(null);
        new CadastroEtapa1Presenter(telaCadastroEtapa1, null);
        telaCadastroEtapa1.setVisible(true);
    }

    // Método genérico para abrir a tela principal
    private void abrirTelaPrincipal(Usuario usuarioLogado, IMainState state) {
        if (view instanceof javax.swing.JFrame) {
            ((javax.swing.JFrame) view).dispose();
        }
        MainView mainView = new MainView();
        new MainPresenter(mainView, state, usuarioLogado);
        mainView.setVisible(true);
    }
}