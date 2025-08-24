package ufes.estudos.Views;

import ufes.estudos.Model.transacao.Oferta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaMinhasOfertas extends JInternalFrame implements IMinhasOfertasView {
    private JTable tabelaOfertas;
    private DefaultTableModel tableModel;
    private JButton btnAlterar;
    private JButton btnCancelar;

    public TelaMinhasOfertas() {
        super("Minhas Ofertas (Carrinho)", true, true, true, true);
        setSize(700, 450);
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        String[] colunas = {"ID do Item", "Valor da Minha Oferta (R$)", "Data da Oferta"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaOfertas = new JTable(tableModel);
        add(new JScrollPane(tabelaOfertas), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCancelar = new JButton("Cancelar Oferta");
        btnAlterar = new JButton("Alterar Oferta");
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnAlterar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    @Override
    public void atualizarTabela(List<Oferta> ofertas) {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Oferta o : ofertas) {
            tableModel.addRow(new Object[]{
                    o.getIdcItem(),
                    String.format("%.2f", o.getValorOfertado()),
                    o.getDataOferta().format(formatter)
            });
        }
    }

    @Override public JTable getTabelaOfertas() { return tabelaOfertas; }
    @Override public void setAlterarListener(ActionListener listener) { btnAlterar.addActionListener(listener); }
    @Override public void setCancelarListener(ActionListener listener) { btnCancelar.addActionListener(listener); }
    @Override public void exibirMensagem(String mensagem) { JOptionPane.showMessageDialog(this, mensagem); }
}