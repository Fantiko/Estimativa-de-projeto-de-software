package ufes.estudos.Views;

import java.awt.event.ActionListener;

public interface IMainView {

    void setTitulo(String painelDoVendedor);

    void exibirMenuVendedor();

    void exibirMenuComprador();

    void exibirMenuAdmin();

    //void mostrarMensagem(String mensagem);

    void setLogoutListener(ActionListener listener);

    void fechar();
}
