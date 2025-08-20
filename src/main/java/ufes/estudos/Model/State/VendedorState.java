package ufes.estudos.Model.State;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IMainView;

public class VendedorState implements IMainState {
    @Override
    public void configurarTela(IMainView view, Usuario usuario) { // PARÂMETRO ADICIONADO
        view.setTitulo("Painel do Vendedor");
        view.exibirMenuVendedor(usuario); // USUÁRIO É PASSADO AQUI
    }
}