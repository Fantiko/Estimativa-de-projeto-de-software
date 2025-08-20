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

    private final TelaGerenciarAnuncios view; // Troca a interface pela classe concreta
    private final AnuncioRepository anuncioRepository;

    public GerenciarAnunciosPresenter(TelaGerenciarAnuncios view) {
        this.view = view;
        this.anuncioRepository = AnuncioRepository.getInstance();
        this.anuncioRepository.addObserver(this);

        // Configura o listener do clique na tabela
        this.view.getTabelaAnuncios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Duplo clique
                    abrirDetalhesDoAnuncio();
                }
            }
        });

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

    private void carregarAnuncios() {
        List<Item> anuncios = anuncioRepository.getAnuncios();
        view.atualizarTabela(anuncios);
    }

    @Override
    public void update() {
        System.out.println("GerenciarAnunciosPresenter foi notificado. Atualizando a view...");
        carregarAnuncios();
    }
}