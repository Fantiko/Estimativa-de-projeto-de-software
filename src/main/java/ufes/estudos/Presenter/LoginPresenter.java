package ufes.estudos.Presenter;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.ILoginView;
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
        String usuario = view.getUsuario();
        String senha = view.getSenha();

        if (usuario.equals("admin") && senha.equals("123")) {
            view.exibirMensagem("Login realizado com sucesso!");
            // abrir tela principal aqui se quiser
        } else if (usuario.equals("user") && senha.equals("123")) {
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
}
