package ufes.estudos.Presenter;

import ufes.estudos.Views.ILoginView;
import ufes.estudos.Views.TelaCadastroEtapa1;

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
        } else {
            view.exibirMensagem("Usuário ou senha inválidos!");
        }
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
