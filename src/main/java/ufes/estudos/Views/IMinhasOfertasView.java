package ufes.estudos.Views;

import ufes.estudos.Model.transacao.Oferta;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;

public interface IMinhasOfertasView {
    void atualizarTabela(List<Oferta> ofertas);
    JTable getTabelaOfertas();
    void setAlterarListener(ActionListener listener);
    void setCancelarListener(ActionListener listener);
    void exibirMensagem(String mensagem);
}