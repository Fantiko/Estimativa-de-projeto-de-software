package ufes.estudos.Views;

import ufes.estudos.Model.Usuario.Usuario; // IMPORT ADICIONADO
import java.awt.event.ActionListener;

public interface IMainView {
    void setTitulo(String titulo);
    void exibirMenuVendedor(Usuario usuario); // MÉTODO MODIFICADO
    void exibirMenuComprador(Usuario usuario);
    void exibirMenuAdmin();
    void setLogoutListener(ActionListener listener);
    void configurarBotaoTrocaPerfil(String texto, ActionListener listener, boolean visivel);
    void fechar();
}