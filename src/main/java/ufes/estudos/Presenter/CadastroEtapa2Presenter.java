package ufes.estudos.Presenter;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.TelaCadastroEtapa1;
import ufes.estudos.Views.TelaCadastroEtapa2;
import ufes.estudos.Views.TelaLogin;

import javax.swing.*;
import java.time.format.DateTimeFormatter;

public class CadastroEtapa2Presenter {

    private TelaCadastroEtapa2 view;
    private Usuario usuario;

    public CadastroEtapa2Presenter(TelaCadastroEtapa2 view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;

        this.view.setVoltarListener(e -> voltar());
        this.view.setFinalizarListener(e -> finalizar());


    }

    private void voltar() {
        view.dispose();
        TelaCadastroEtapa1 telaCadastro = new TelaCadastroEtapa1(usuario);
        new CadastroEtapa1Presenter(telaCadastro, usuario);
        telaCadastro.setVisible(true);

        usuario.setRazaoSocial(view.getRazaoSocial());
        usuario.setEmail(view.getEmail());
        usuario.setTelefone(view.getTelefone());
    }

    private void finalizar() {
        if (view.getEmail().isEmpty()) {
            JOptionPane.showMessageDialog(view, "O e-mail é obrigatório!");
            return;
        }

        usuario.setRazaoSocial(view.getRazaoSocial());
        usuario.setEmail(view.getEmail());
        usuario.setTelefone(view.getTelefone());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String dataFormatada = usuario.getDataCriacao().format(formatter);

        JOptionPane.showMessageDialog(view,
                "Cadastro concluído!\nUsuário: " + usuario.getUsuario() +
                        "\nData de criação: " + dataFormatada);

        view.dispose();
        TelaLogin telaLogin = new TelaLogin();
        new LoginPresenter(telaLogin);
        telaLogin.setVisible(true);
        // Aqui poderia voltar para a tela de login
    }
}
