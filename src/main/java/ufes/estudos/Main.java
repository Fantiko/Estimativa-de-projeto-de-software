package ufes.estudos;

import ufes.estudos.Presenter.LoginPresenter;
import ufes.estudos.Views.TelaLogin;
import ufes.estudos.dao.SetupDAO; // <<< IMPORT ADICIONADO

public class Main {
    public static void main(String[] args) {
        // --- LÓGICA DE INICIALIZAÇÃO DO BANCO ADICIONADA AQUI ---
        // Garante que as tabelas existam e que os dados base sejam inseridos
        SetupDAO setup = new SetupDAO();
        setup.criaTabelas();
        setup.popularTabelasBase();
        // --- FIM DA LÓGICA DE INICIALIZAÇÃO ---

        // O resto do programa continua normalmente
        TelaLogin view = new TelaLogin();
        new LoginPresenter(view);
        view.setVisible(true);
    }
}