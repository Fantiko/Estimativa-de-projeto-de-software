package ufes.estudos.Model.State;

import ufes.estudos.Views.IMainView;


public class CompradorState implements IMainState {
    @Override
    public void configurarTela(IMainView view) {
        view.setTitulo("Painel do Comprador");
        view.exibirMenuComprador();
    }

}

