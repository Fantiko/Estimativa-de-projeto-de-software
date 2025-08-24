package ufes.estudos.Views;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import ufes.estudos.Model.Usuario.SolicitacaoPerfil;

public interface IAprovarPerfisView {
    void atualizarTabela(List<SolicitacaoPerfil> solicitacoes);
    JTable getTabela();
    void setAprovarListener(ActionListener listener);
    void setReprovarListener(ActionListener listener);
    void exibirMensagem(String mensagem);
}