package ufes.estudos.Views;

import java.awt.event.ActionListener;

public interface IEscolherPerfilCadastroView {
    boolean isVendedorSelecionado();
    boolean isCompradorSelecionado();
    void setFinalizarListener(ActionListener listener);
    void exibirMensagem(String mensagem);
    void fechar();
}