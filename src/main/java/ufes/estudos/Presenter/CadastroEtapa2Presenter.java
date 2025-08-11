package ufes.estudos.Presenter;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.TelaCadastroEtapa1;
import ufes.estudos.Views.TelaCadastroEtapa2;

import javax.swing.*;

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

        JOptionPane.showMessageDialog(view,
                "Cadastro concluído!\nUsuário: " + usuario.getUsuario() +
                        "\nData de criação: " + usuario.getDataCriacao());

        view.dispose();
        // Aqui poderia voltar para a tela de login
    }
}
