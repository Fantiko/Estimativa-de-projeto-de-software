package ufes.estudos;

import org.example.LogAdapter.Logger;
import ufes.estudos.Presenter.LoginPresenter;
import ufes.estudos.Views.TelaLogin;
import ufes.estudos.dao.SetupDAO;

public class Main {
    public static void main(String[] args) {
        SetupDAO setup = new SetupDAO();
        setup.criaTabelas();
        setup.popularTabelasBase();

        TelaLogin view = new TelaLogin();
        new LoginPresenter(view);
        view.setVisible(true);
    }
}