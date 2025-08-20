package ufes.estudos.Presenter;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.Views.TelaDetalhesAnuncio;
import ufes.estudos.Views.TelaGerenciarAnuncios;
import ufes.estudos.observer.Observer;
import ufes.estudos.repository.AnuncioRepository;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GerenciarAnunciosPresenter implements Observer {

    private final TelaGerenciarAnuncios view;
    private final AnuncioRepository anuncioRepository;

    public GerenciarAnunciosPresenter(TelaGerenciarAnuncios view) {
        this.view = view;
        this.anuncioRepository = AnuncioRepository.getInstance();
        this.anuncioRepository.addObserver(this);

        // Configura o listener do duplo clique na tabela (ação principal)
        this.view.getTabelaAnuncios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    abrirDetalhesDoAnuncio();
                }
            }
        });

        // Configura o listener para a SELEÇÃO na tabela (para habilitar o botão)
        this.view.getTabelaAnuncios().getSelectionModel().addListSelectionListener(e -> {
            // e.getValueIsAdjusting() evita que o evento dispare duas vezes
            if (!e.getValueIsAdjusting()) {
                // Habilita o botão se alguma linha estiver selecionada
                boolean isRowSelected = view.getTabelaAnuncios().getSelectedRow() != -1;
                view.getBtnVerDetalhes().setEnabled(isRowSelected);
            }
        });

        // Configura a ação do novo botão "Ver Mais Detalhes"
        this.view.getBtnVerDetalhes().addActionListener(e -> abrirDetalhesDoAnuncio());

        // Configura a ação do novo botão "Deletar"
        this.view.getBtnDeletar().addActionListener(e -> deletarAnuncioSelecionado());

        carregarAnuncios();
    }

    private void abrirDetalhesDoAnuncio() {
        int selectedRow = view.getTabelaAnuncios().getSelectedRow();
        if (selectedRow >= 0) {
            String idc = (String) view.getTabelaAnuncios().getValueAt(selectedRow, 0);
            Item itemSelecionado = anuncioRepository.findByIdc(idc);

            if (itemSelecionado != null) {
                TelaDetalhesAnuncio detalhesView = new TelaDetalhesAnuncio();
                new DetalhesAnuncioPresenter(detalhesView, itemSelecionado);

                JDesktopPane desktopPane = view.getDesktopPane();
                desktopPane.add(detalhesView);
                detalhesView.setVisible(true);
            }
        }
    }

    private void deletarAnuncioSelecionado() {
        int selectedRow = view.getTabelaAnuncios().getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Por favor, selecione um anúncio para deletar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String idc = (String) view.getTabelaAnuncios().getValueAt(selectedRow, 0);

        Object[] options = {"Apagar", "Cancelar"};
        int confirmacao = JOptionPane.showOptionDialog(view,
                "Deseja mesmo apagar o item de ID-C: " + idc + "?",
                "Confirmação de Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null, options, options[1]);

        if (confirmacao == JOptionPane.YES_OPTION) {
            anuncioRepository.deleteAnuncio(idc);
            JOptionPane.showMessageDialog(view, "Anúncio removido com sucesso!");
            // A tabela será atualizada automaticamente pelo padrão Observer
        }
    }

    private void carregarAnuncios() {
        List<Item> anuncios = anuncioRepository.getAnuncios();
        view.atualizarTabela(anuncios);
    }

    @Override
    public void update(String tipoNotificacao, Object dados) {
        System.out.println("GerenciarAnunciosPresenter foi notificado. Atualizando a view...");
        carregarAnuncios();
    }
}