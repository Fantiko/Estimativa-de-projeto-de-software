package ufes.estudos.Views;

import java.awt.event.ActionListener;

public interface IAdicionarAnuncioView {
    String getIdc();
    String getTipoPeca();
    String getSubcategoria();
    String getTamanho();
    String getCor();
    String getComposicao();
    String getMassa();
    String getEstado();
    String getDefeito();
    String getPreco();

    void setDefeitosOptions(String[] defeitos);
    void setTipoPecaListener(ActionListener listener);
    void setSalvarListener(ActionListener listener);
    void setCancelarListener(ActionListener listener);
    void exibirMensagem(String mensagem);
    void fechar();
}