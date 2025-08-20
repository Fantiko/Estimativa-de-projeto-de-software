package ufes.estudos.Views;

import ufes.estudos.Model.Item.Item;
import java.awt.event.ActionListener;

public interface IDetalhesAnuncioView {
    void exibirAnuncio(Item item);
    void setCamposEditaveis(boolean editavel);
    void setModoEdicao(boolean modoEdicao);
    Item getDadosAnuncioEditado();

    void setEditarListener(ActionListener listener);
    void setSalvarListener(ActionListener listener);
    void setDeletarListener(ActionListener listener);
    void setCancelarListener(ActionListener listener);

    int exibirConfirmacaoDelecao();
    void exibirMensagem(String mensagem);
    void fechar();
}