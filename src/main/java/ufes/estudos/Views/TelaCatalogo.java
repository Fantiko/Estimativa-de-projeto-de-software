package ufes.estudos.Views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaCatalogo extends JInternalFrame implements ICatalogoView {
    private JTable tabelaCatalogo;
    private DefaultTableModel tableModel;
    private JButton btnComprar;
    private JButton btnHistorico;

    public TelaCatalogo() {
        super("Catálogo de Produtos", true, true, true, true);
        setSize(800, 500);
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Tabela
        String[] colunas = {"ID-C", "Peça", "Vendedor", "Preço Final (R$)", "MCI (simulado)", "GWP Evitado (kg CO₂)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaCatalogo = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaCatalogo);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnComprar = new JButton("Comprar Selecionado");
        btnHistorico = new JButton("Mostrar Histórico");
        painelBotoes.add(btnHistorico);
        painelBotoes.add(btnComprar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    @Override
    public void atualizarTabela(List<Object[]> dadosLinhas) {
        tableModel.setRowCount(0); // Limpa a tabela
        for (Object[] linha : dadosLinhas) {
            tableModel.addRow(linha);
        }
    }

    @Override
    public JTable getTabelaCatalogo() {
        return tabelaCatalogo;
    }

    @Override
    public void setComprarListener(ActionListener listener) {
        btnComprar.addActionListener(listener);
    }

    @Override
    public void setHistoricoListener(ActionListener listener) {
        btnHistorico.addActionListener(listener);
    }

    @Override
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}