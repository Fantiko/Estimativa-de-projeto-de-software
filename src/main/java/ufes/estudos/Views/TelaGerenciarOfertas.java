package ufes.estudos.Views;

import ufes.estudos.Model.transacao.Oferta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaGerenciarOfertas extends JInternalFrame implements IGerenciarOfertasView {
    private JTable tabelaOfertas;
    private DefaultTableModel tableModel;
    private JButton btnAceitar;
    private JButton btnRecusar;

    public TelaGerenciarOfertas() {
        super("Gerenciar Ofertas Recebidas", true, true, true, true);
        setSize(700, 450);
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        String[] colunas = {"ID do Item", "Comprador", "Valor Ofertado (R$)", "Data da Oferta"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaOfertas = new JTable(tableModel);
        add(new JScrollPane(tabelaOfertas), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRecusar = new JButton("Recusar Oferta");
        btnAceitar = new JButton("Aceitar Oferta");
        painelBotoes.add(btnRecusar);
        painelBotoes.add(btnAceitar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    @Override
    public void atualizarTabela(List<Object[]> dadosLinhas) {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Object[] linha : dadosLinhas) {
            String idcItem = (String) linha[0];
            String nomeComprador = (String) linha[1];
            double valor = (double) linha[2];
            LocalDateTime data = (LocalDateTime) linha[3];

            tableModel.addRow(new Object[]{
                    idcItem,
                    nomeComprador,
                    String.format("%.2f", valor),
                    data.format(formatter)
            });
        }
    }

    @Override public JTable getTabelaOfertas() { return tabelaOfertas; }
    @Override public void setAceitarListener(ActionListener listener) { btnAceitar.addActionListener(listener); }
    @Override public void setRecusarListener(ActionListener listener) { btnRecusar.addActionListener(listener); }
    @Override public void exibirMensagem(String mensagem) { JOptionPane.showMessageDialog(this, mensagem); }
}