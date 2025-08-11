package ufes.estudos.Presenter;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.TelaCadastroEtapa1;
import ufes.estudos.Views.TelaCadastroEtapa2;
import ufes.estudos.Views.TelaLogin;

public class CadastroEtapa1Presenter {

    private TelaCadastroEtapa1 view;
    private Usuario usuario;

    public CadastroEtapa1Presenter(TelaCadastroEtapa1 view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;

        this.view.setAvancarListener(e -> avancar());
        this.view.setCancelarListener(e -> cancelar());
    }

    private void avancar() {
        String usuarioStr = view.getUsuario();
        String senha = view.getSenha();
        String confirmarSenha = view.getConfirmarSenha();

        if (usuarioStr.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            view.exibirMensagem("Preencha todos os campos!");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            view.exibirMensagem("As senhas não coincidem!");
            return;
        }

        if (usuario == null){
            usuario = new Usuario(usuarioStr, senha);
        }else{
            usuario.setUsuario(usuarioStr);
            usuario.setSenha(senha);
        }

        // Chamar o Model para salvar o usuário aqui
        view.dispose();

        TelaCadastroEtapa2 telaEtapa2 = new TelaCadastroEtapa2(usuario);
        new CadastroEtapa2Presenter(telaEtapa2, usuario);
        telaEtapa2.setVisible(true);

        //view.exibirMensagem("Cadastro realizado com sucesso!");

        // Fechar a tela ou abrir a de login aqui
    }

    private void cancelar(){
        view.dispose();
        TelaLogin telaLogin = new TelaLogin();
        new LoginPresenter(telaLogin);
        telaLogin.setVisible(true);
    }
}
