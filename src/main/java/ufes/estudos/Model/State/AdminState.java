package ufes.estudos.Model.State;

import ufes.estudos.Views.IMainView;

public class AdminState implements IMainState {
    @Override
    public void configurarTela(IMainView view) {
        view.setTitulo("Painel Administrativo");
        view.exibirMenuAdmin();
    }

}

