package ufes.estudos;

import ufes.estudos.Presenter.LoginPresenter;
import ufes.estudos.Views.TelaLogin;

public class Main {
    public static void main(String[] args) {
        TelaLogin view = new TelaLogin();
        new LoginPresenter(view);
        view.setVisible(true);
    }
}
