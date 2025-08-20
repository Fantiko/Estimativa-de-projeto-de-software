package ufes.estudos.Model.State;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IMainView;

public class CompradorState implements IMainState {
    @Override
    public void configurarTela(IMainView view, Usuario usuario) { // PARÂMETRO ADICIONADO
        view.setTitulo("Painel do Comprador");
        view.exibirMenuComprador();
    }
}