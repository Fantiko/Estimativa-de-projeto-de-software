package ufes.estudos.Presenter;

import ufes.estudos.Model.State.AdminState;
import ufes.estudos.Model.State.IMainState;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.ILoginView;
import ufes.estudos.Views.MainView;
import ufes.estudos.Views.TelaCadastroEtapa1;
import ufes.estudos.Views.TelaEscolherPerfil;

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

        if (username.equals("admin") && senha.equals("123")) {
            Usuario usuarioLogado = new Usuario("admin", "123", "27992671690",
                    "userteste@gmail.com", "Ademiro da Silva User");

            abrirTelaAdmin(usuarioLogado);

        } else if (username.equals("user") && senha.equals("123")) {
            Usuario usuarioLogado = new Usuario("user", "123", "27992671690",
                    "userteste@gmail.com", "Usuário da Silva User"); //USUÁRIO TEMPORÁRIO PRA TESTES

            abrirTelaEscolhaPerfil(usuarioLogado);

        }else {
            JOptionPane.showMessageDialog((Component) view, "Login ou senha incorretos!");
        }


    }

    private void abrirTelaEscolhaPerfil(Usuario usuarioLogado) {
        // Fecha a tela de login
        if (view instanceof JFrame) {
            ((JFrame) view).dispose();
        }

        // Abre a tela de escolha de perfil
        TelaEscolherPerfil telaPerfil = new TelaEscolherPerfil();
        new EscolherPerfilPresenter(telaPerfil, usuarioLogado);
        telaPerfil.setVisible(true);
    }

    private void abrirTelaCadastro() {
        // Fecha a tela de login
        if (view instanceof javax.swing.JFrame) {
            ((javax.swing.JFrame) view).dispose();
        }

        // Abre a tela de cadastro
        TelaCadastroEtapa1 telaCadastroEtapa1 = new TelaCadastroEtapa1(null);
        new CadastroEtapa1Presenter(telaCadastroEtapa1, null);
        telaCadastroEtapa1.setVisible(true);
    }

    private void abrirTelaAdmin(Usuario usuarioLogado){
        // Fecha a tela de login
        if (view instanceof javax.swing.JFrame) {
            ((javax.swing.JFrame) view).dispose();
        }

        //cria o estado de admin
        IMainState state = new AdminState();

        MainView view = new MainView();
        new MainPresenter(view, state, usuarioLogado);
        view.setVisible(true);
        // abrir tela principal aqui se quiser
    }
}
