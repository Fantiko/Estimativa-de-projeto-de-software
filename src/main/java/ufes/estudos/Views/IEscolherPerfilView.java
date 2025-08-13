package ufes.estudos.Views;

import javax.swing.*;

public interface IEscolherPerfilView {
    JButton getBtnLogar();
    boolean isVendedorSelecionado();
    boolean isCompradorSelecionado();
    void fechar();
    void mostrarMensagem(String msg);
}
