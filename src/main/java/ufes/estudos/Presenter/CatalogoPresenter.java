package ufes.estudos.Presenter;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.ICatalogoView;
import ufes.estudos.Views.TelaNegociacao;
import ufes.estudos.repository.RepositoriesIntefaces.AnuncioRepository;
import ufes.estudos.repository.RepositoriesIntefaces.UsuarioRepository;
import ufes.estudos.repository.RepositoriesSQLite.AnuncioSQLiteRepository;
import ufes.estudos.repository.RepositoriesSQLite.UsuarioSQLiteRepository; // <<< IMPORT CORRIGIDO

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CatalogoPresenter {
    private final ICatalogoView view;
    private final AnuncioRepository anuncioRepository;
    private final UsuarioRepository usuarioRepository;
    private final Usuario usuario;

    public CatalogoPresenter(ICatalogoView view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;
        this.anuncioRepository = new AnuncioSQLiteRepository(new SQLiteConnectionManager());

        // <<< INSTANCIAÇÃO CORRIGIDA >>>
        this.usuarioRepository = new UsuarioSQLiteRepository(new SQLiteConnectionManager());

        carregarCatalogo();
        this.view.setComprarListener(e -> abrirTelaDeNegociacao());
        // O botão de histórico ainda é um placeholder, podemos implementá-lo depois
        this.view.setHistoricoListener(e -> view.exibirMensagem("Funcionalidade 'Histórico de Compras' ainda não implementada."));
    }

    private void carregarCatalogo() {
        // 1. Busca todos os usuários uma vez e cria um mapa de ID para Nome
        Map<Integer, String> mapaNomesVendedores = usuarioRepository.buscarTodos().stream()
                .collect(Collectors.toMap(Usuario::getId, Usuario::getNome));

        List<Item> todosAnuncios = anuncioRepository.getAnuncios();
        List<Object[]> dadosParaTabela = new ArrayList<>();

        for (Item item : todosAnuncios) {
            if (item.getIdVendedor() == this.usuario.getId()) {
                continue; // Pula os itens do próprio usuário
            }

            // 2. Usa o mapa para buscar o nome do vendedor pelo ID
            String nomeVendedor = mapaNomesVendedores.getOrDefault(item.getIdVendedor(), "Desconhecido");

            double precoFinal = item.getPrecoBase() * (1 - item.getDefeito().getPercentual());
            double mci = 1 - item.getDefeito().getPercentual();

            dadosParaTabela.add(new Object[]{
                    item.getIdentificadorCircular(),
                    item.getTipoPeca(),
                    nomeVendedor,
                    String.format("%.2f", precoFinal),
                    String.format("%.2f", mci),
                    String.format("%.4f", item.getGwpAvoided())
            });
        }
        view.atualizarTabela(dadosParaTabela);
    }

    private void abrirTelaDeNegociacao() {
        int selectedRow = view.getTabelaCatalogo().getSelectedRow();
        if (selectedRow < 0) {
            view.exibirMensagem("Por favor, selecione um item para fazer uma oferta.");
            return;
        }

        String idc = (String) view.getTabelaCatalogo().getValueAt(selectedRow, 0);

        // --- CORREÇÃO AQUI ---
        // O método findByIdc já retorna o Item ou null, então removemos o .orElse(null)
        Item itemSelecionado = anuncioRepository.findByIdc(idc).orElse(null);

        if (itemSelecionado == null) {
            view.exibirMensagem("Erro: Item não encontrado ou não está mais disponível.");
            carregarCatalogo(); // Atualiza a tabela para remover o item
            return;
        }

        String precoFinalStr = (String) view.getTabelaCatalogo().getValueAt(selectedRow, 3);
        double precoFinal = Double.parseDouble(precoFinalStr.replace(',', '.'));

        JFrame framePrincipal = (JFrame) SwingUtilities.getWindowAncestor((JComponent) view);
        TelaNegociacao tela = new TelaNegociacao(framePrincipal);
        new NegociacaoPresenter(tela, itemSelecionado, this.usuario, precoFinal);
        tela.setVisible(true);
    }
}