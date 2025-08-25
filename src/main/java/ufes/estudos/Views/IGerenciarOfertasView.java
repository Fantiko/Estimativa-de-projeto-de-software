package ufes.estudos.Views;

import ufes.estudos.Model.transacao.Oferta;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;

public interface IGerenciarOfertasView {
    void atualizarTabela(List<Object[]> dadosLinhas);
    JTable getTabelaOfertas();
    void setAceitarListener(ActionListener listener);
    void setRecusarListener(ActionListener listener);
    void exibirMensagem(String mensagem);
}