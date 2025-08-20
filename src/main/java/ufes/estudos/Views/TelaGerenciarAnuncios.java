package ufes.estudos.Views;

import java.awt.event.MouseEvent; // <<< ADICIONE ESTA LINHA
import ufes.estudos.Model.Item.Item;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;

import java.util.List;

public class TelaGerenciarAnuncios extends JInternalFrame implements IGerenciarAnunciosView {

    private JTable tabelaAnuncios;
    private DefaultTableModel tableModel;
    private JDesktopPane desktopPane; // Referência ao painel principal

    public TelaGerenciarAnuncios(JDesktopPane desktopPane) {
        super("Gerencias Meus Anúncios", true, true, true, true);
        this.desktopPane = desktopPane; // Armazena a referência
        setSize(700, 400);
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        String[] colunas = {"ID-C", "Tipo Peça", "Subcategoria", "Tamanho", "Cor", "Preço Base (R$)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células não editáveis
            }
        };
        tabelaAnuncios = new JTable(tableModel);
        // Adiciona o listener para o clique duplo

        JScrollPane scrollPane = new JScrollPane(tabelaAnuncios);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Adicione este método para passar o JTable para o presenter
    public JTable getTabelaAnuncios() {
        return tabelaAnuncios;
    }

    @Override
    public void atualizarTabela(List<Item> anuncios) {
        // Limpa a tabela
        tableModel.setRowCount(0);

        // Preenche com os novos dados
        for (Item item : anuncios) {
            Object[] rowData = {
                    item.getIdentificadorCircular(),
                    item.getTipoPeca(),
                    item.getSubcategoria(),
                    item.getTamanho(),
                    item.getCorPredominante(),
                    String.format("%.2f", item.getPrecoBase())
            };
            tableModel.addRow(rowData);
        }
    }

    @Override
    public void fechar() {
        dispose();
    }

    // Dentro de TelaGerenciarAnuncios.java
    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }
}