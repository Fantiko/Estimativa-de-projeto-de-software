package ufes.estudos.Presenter;

import ufes.estudos.Model.State.CompradorState;
import ufes.estudos.Model.State.IMainState;
import ufes.estudos.Model.State.VendedorState;
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IEscolherPerfilView;
import ufes.estudos.Views.MainView;
import ufes.estudos.repository.PerfilRepository;

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
            // Dentro do método inicializar() do EscolherPerfilPresenter
// ...
            IMainState state;
            if (view.isVendedorSelecionado()) {
                usuario.setVendedor(true);
                state = new VendedorState();
                // Adiciona o perfil ao repositório
                PerfilRepository.getInstance().addVendedor(new PerfilVendedor(usuario));
            } else {
                usuario.setComprador(true);
                state = new CompradorState();
                // Adiciona o perfil ao repositório
                PerfilRepository.getInstance().addComprador(new PerfilComprador(usuario));
            }
// ...
            view.fechar();


            //chamar a tela aqui
            MainView view = new MainView();
            new MainPresenter(view, state, usuario);
            view.setVisible(true);
        });
    }
}
