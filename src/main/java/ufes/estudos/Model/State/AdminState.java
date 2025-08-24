package ufes.estudos.Model.State;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IMainView;

public class AdminState implements IMainState {
    @Override
    public void configurarTela(IMainView view, Usuario usuario) {
        view.setTitulo("Painel Administrativo");
        view.exibirMenuAdmin(usuario); // <<< PASSE O USUÁRIO AQUI
    }
}