package ufes.estudos.Views;

import ufes.estudos.Model.transacao.Venda;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaHistoricoCompras extends JInternalFrame implements IHistoricoComprasView {
    private DefaultTableModel tableModel;

    public TelaHistoricoCompras() {
        super("Meu Histórico de Compras", true, true, true, true);
        setSize(700, 400);
        setLayout(new BorderLayout());

        String[] colunas = {"ID do Item", "Vendedor", "Valor Pago (R$)", "GWP Evitado (kg CO₂)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }

    @Override
    public void atualizarTabela(List<Venda> vendas) {
        tableModel.setRowCount(0);
        for(Venda v : vendas) {
            tableModel.addRow(new Object[]{
                    v.getIdcItem(),
                    v.getNomeVendedor(),
                    String.format("%.2f", v.getValorFinal()),
                    String.format("%.4f", v.getGwpEvitado())
            });
        }
    }
}