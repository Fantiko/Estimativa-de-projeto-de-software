package ufes.estudos.Presenter;

import ufes.estudos.Model.State.CompradorState;
import ufes.estudos.Model.State.IMainState;
import ufes.estudos.Model.State.VendedorState;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IEscolherPerfilView;
import ufes.estudos.Views.MainView;

import javax.swing.*;
import java.awt.*;

public class EscolherPerfilPresenter {

    private final IEscolherPerfilView view;
    private final Usuario usuario;

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
            IMainState state;
            if (view.isVendedorSelecionado()) {
                usuario.setVendedor(true);
                state = new VendedorState();
            } else {
                usuario.setComprador(true);
                state = new CompradorState();
            }

            // Aqui você pode ir para a tela principal
            JOptionPane.showMessageDialog((Component) view, "Indo para tela principal...");
            view.fechar();


            //chamar a tela aqui
            MainView view = new MainView();
            new MainPresenter(view, state, usuario);
            view.setVisible(true);
        });
    }
}
