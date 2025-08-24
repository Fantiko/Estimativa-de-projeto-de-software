package ufes.estudos.Views;

import java.awt.event.ActionListener;

public interface INegociacaoView {
    void setNomeItem(String nome);
    void setPrecoOriginal(String preco);
    void setFaixaDeOferta(String faixa);
    String getValorOferta();
    void setEnviarOfertaListener(ActionListener listener);
    void exibirMensagem(String mensagem);
    void fechar();
}