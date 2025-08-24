package ufes.estudos.Views;

import ufes.estudos.Model.Usuario.SolicitacaoPerfil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaAprovarPerfis extends JInternalFrame implements IAprovarPerfisView {
    private JTable tabela;
    private DefaultTableModel tableModel;
    private JButton btnAprovar;
    private JButton btnReprovar;

    public TelaAprovarPerfis() {
        super("Aprovar Solicitações de Perfil", true, true, true, true);
        setSize(600, 400);
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        String[] colunas = {"Usuário", "Perfil Solicitado", "Data da Solicitação"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAprovar = new JButton("Aprovar Solicitação");
        btnReprovar = new JButton("Reprovar Solicitação");
        painelBotoes.add(btnReprovar);
        painelBotoes.add(btnAprovar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    @Override
    public void atualizarTabela(List<SolicitacaoPerfil> solicitacoes) {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (SolicitacaoPerfil s : solicitacoes) {
            tableModel.addRow(new Object[]{
                    s.getNomeUsuario(),
                    s.getPerfilSolicitado(),
                    s.getDataSolicitacao().format(formatter)
            });
        }
    }

    @Override public JTable getTabela() { return tabela; }
    @Override public void setAprovarListener(ActionListener listener) { btnAprovar.addActionListener(listener); }
    @Override public void setReprovarListener(ActionListener listener) { btnReprovar.addActionListener(listener); }
    @Override public void exibirMensagem(String mensagem) { JOptionPane.showMessageDialog(this, mensagem); }
}