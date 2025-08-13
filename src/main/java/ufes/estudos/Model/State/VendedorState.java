package ufes.estudos.Model.State;

import ufes.estudos.Views.IMainView;

public class VendedorState implements IMainState {
    @Override
    public void configurarTela(IMainView view) {
        view.setTitulo("Painel do Vendedor");
        view.exibirMenuVendedor();
    }
}
