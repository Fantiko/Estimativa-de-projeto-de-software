package ufes.estudos.Presenter;

import ufes.estudos.Views.IEscolherPerfilView;
import ufes.estudos.Model.Usuario.Usuario;

import javax.swing.*;
import java.awt.*;

public class EscolherPerfilPresenter {

    private final IEscolherPerfilView view;
    private Usuario usuario;

    public EscolherPerfilPresenter(IEscolherPerfilView view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;

        inicializar();
    }

    private void inicializar() {
        view.getBtnLogar().addActionListener(e -> {
            if (!view.isVendedorSelecionado() && !view.isCompradorSelecionado()) {
                view.mostrarMensagem("Selecione um perfil para continuar!");
                return;
            }

            if (view.isVendedorSelecionado()) {
                usuario.setVendedor(true);
            } else {
                usuario.setComprador(true);
            }

            // Aqui você pode ir para a tela principal
            JOptionPane.showMessageDialog((Component) view, "Indo para tela principal...");
            view.fechar();
            //new TelaPrincipalPresenter(usuario); // exemplo
        });
    }
}
