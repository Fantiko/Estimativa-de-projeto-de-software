package ufes.estudos.Presenter;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Usuario.Usuario; // IMPORT ADICIONADO
import ufes.estudos.Views.TelaDetalhesAnuncio;
import ufes.estudos.Views.TelaGerenciarAnuncios;
import ufes.estudos.observer.Observer;
import ufes.estudos.repository.RepositoriesIntefaces.AnuncioRepository;
import ufes.estudos.repository.RepositoriesSQLite.AnuncioSQLiteRepository;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GerenciarAnunciosPresenter implements Observer {

    private final TelaGerenciarAnuncios view;
    private final AnuncioRepository anuncioRepository;
    private final Usuario usuario; // CAMPO ADICIONADO

    public GerenciarAnunciosPresenter(TelaGerenciarAnuncios view, Usuario usuario) { // PARÂMETRO ADICIONADO
        this.view = view;
        this.usuario = usuario; // ATRIBUIÇÃO ADICIONADA
        this.anuncioRepository = new AnuncioSQLiteRepository(new SQLiteConnectionManager());
        this.anuncioRepository.addObserver(this);

        // ... (Listeners da tabela e dos botões permanecem iguais)
        this.view.getTabelaAnuncios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    abrirDetalhesDoAnuncio();
                }
            }
        });

        this.view.getTabelaAnuncios().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean isRowSelected = view.getTabelaAnuncios().getSelectedRow() != -1;
                view.getBtnVerDetalhes().setEnabled(isRowSelected);
            }
        });

        this.view.getBtnVerDetalhes().addActionListener(e -> abrirDetalhesDoAnuncio());
        this.view.getBtnDeletar().addActionListener(e -> deletarAnuncioSelecionado());

        carregarAnuncios();
    }

    // ... (métodos abrirDetalhesDoAnuncio e deletarAnuncioSelecionado permanecem iguais)

    private void abrirDetalhesDoAnuncio() {
        int selectedRow = view.getTabelaAnuncios().getSelectedRow();
        if (selectedRow >= 0) {
            String idc = (String) view.getTabelaAnuncios().getValueAt(selectedRow, 0);
            Item itemSelecionado = anuncioRepository.findByIdc(idc).orElse(null);

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
        }
    }


    private void carregarAnuncios() {
        // A chamada agora passa o ID do usuário, e não mais o nome
        List<Item> anuncios = anuncioRepository.getAnunciosByVendedor(this.usuario.getId());
        view.atualizarTabela(anuncios);
    }

    @Override
    public void update(String tipoNotificacao, Object dados) {
        System.out.println("GerenciarAnunciosPresenter foi notificado. Atualizando a view...");
        carregarAnuncios();
    }
}