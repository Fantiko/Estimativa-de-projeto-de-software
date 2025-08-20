package ufes.estudos.Views;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;

public interface ICatalogoView {
    void atualizarTabela(List<Object[]> dadosLinhas);
    JTable getTabelaCatalogo();
    void setComprarListener(ActionListener listener);
    void setHistoricoListener(ActionListener listener);
    void exibirMensagem(String mensagem);
}