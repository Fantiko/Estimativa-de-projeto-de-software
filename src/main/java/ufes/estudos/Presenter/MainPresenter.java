package ufes.estudos.Presenter;

import ufes.estudos.Model.State.IMainState;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IMainView;
import ufes.estudos.Views.TelaLogin;

public class MainPresenter {
    private final IMainView view;
    private final IMainState state;
    private final Usuario usuario;

    public MainPresenter(IMainView view, IMainState state, Usuario usuario) {
        this.view = view;
        this.state = state;
        this.usuario = usuario;

        state.configurarTela(view);
        this.view.setLogoutListener(e -> logout());
    }

    private void logout() {
        view.fechar();

        TelaLogin view = new TelaLogin();
        new LoginPresenter(view);
        view.setVisible(true);
    }
}

