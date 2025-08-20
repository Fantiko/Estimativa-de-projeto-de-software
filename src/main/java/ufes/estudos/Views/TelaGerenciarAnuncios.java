package ufes.estudos.Views;

import ufes.estudos.Model.Item.Item;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaGerenciarAnuncios extends JInternalFrame implements IGerenciarAnunciosView {

    private JTable tabelaAnuncios;
    private DefaultTableModel tableModel;
    private JDesktopPane desktopPane;
    private JButton btnDeletar;
    private JButton btnVerDetalhes;

    public TelaGerenciarAnuncios(JDesktopPane desktopPane) {
        super("Gerencias Meus Anúncios", true, true, true, true);
        this.desktopPane = desktopPane;
        setSize(700, 400);
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Tabela de anúncios (centro)
        String[] colunas = {"ID-C", "Tipo Peça", "Subcategoria", "Tamanho", "Cor", "Preço Base (R$)"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaAnuncios = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaAnuncios);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões no rodapé (sul)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnDeletar = new JButton("Deletar Selecionado");
        btnVerDetalhes = new JButton("Ver Mais Detalhes");

        // O botão de detalhes começa desabilitado
        btnVerDetalhes.setEnabled(false);

        painelBotoes.add(btnDeletar);
        painelBotoes.add(btnVerDetalhes);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    // Métodos para o Presenter acessar os componentes da View
    public JTable getTabelaAnuncios() {
        return tabelaAnuncios;
    }

    public JButton getBtnDeletar() {
        return btnDeletar;
    }

    public JButton getBtnVerDetalhes() {
        return btnVerDetalhes;
    }

    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }

    @Override
    public void atualizarTabela(List<Item> anuncios) {
        tableModel.setRowCount(0);
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
}