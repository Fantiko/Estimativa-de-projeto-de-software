package ufes.estudos.Model.State;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IMainView;

public interface IMainState {
    void configurarTela(IMainView view, Usuario usuario);
}
