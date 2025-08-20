package ufes.estudos.Presenter;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.Views.ICatalogoView;
import ufes.estudos.repository.AnuncioRepository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CatalogoPresenter {
    private final ICatalogoView view;
    private final AnuncioRepository anuncioRepository;

    public CatalogoPresenter(ICatalogoView view) {
        this.view = view;
        this.anuncioRepository = AnuncioRepository.getInstance();

        carregarCatalogo();

        // Ações dos botões (por enquanto, apenas exibem mensagens)
        this.view.setComprarListener(e -> comprarItemSelecionado());
        this.view.setHistoricoListener(e -> view.exibirMensagem("Funcionalidade 'Histórico de Compras' ainda não implementada."));
    }

    private void carregarCatalogo() {
        List<Item> anuncios = anuncioRepository.getAnuncios();
        List<Object[]> dadosParaTabela = new ArrayList<>();

        for (Item item : anuncios) {
            double precoBase = item.getPrecoBase();
            double percentualDesconto = calcularDescontoPorDefeito(item.getDefeito().getDefeito());
            double precoFinal = precoBase * (1 - percentualDesconto);
            double gwpEvitado = item.getMassaEstimada() * item.getMaterial().getFatorEmissao();
            double mciSimulado = simularMCI(item.getEstadoConservacao());

            dadosParaTabela.add(new Object[]{
                    item.getIdentificadorCircular(),
                    item.getTipoPeca(),
                    item.getNomeVendedor(), // <<<< ALTERAÇÃO PRINCIPAL AQUI
                    String.format("%.2f", precoFinal),
                    String.format("%.2f", mciSimulado),
                    String.format("%.4f", gwpEvitado)
            });
        }
        view.atualizarTabela(dadosParaTabela);
    }

    private void comprarItemSelecionado() {
        int selectedRow = view.getTabelaCatalogo().getSelectedRow();
        if (selectedRow < 0) {
            view.exibirMensagem("Por favor, selecione um item para comprar.");
            return;
        }
        String idc = (String) view.getTabelaCatalogo().getValueAt(selectedRow, 0);
        view.exibirMensagem("Compra do item " + idc + " realizada com sucesso! (Funcionalidade em desenvolvimento)");
        // Aqui entraria a lógica de transação, remoção do item do catálogo, etc.
    }

    // REGRA DE NEGÓCIO: Define o desconto com base na descrição do defeito
    private double calcularDescontoPorDefeito(String defeito) {
        Map<String, Double> mapaDescontos = Map.of(
                "Rasgo estruturante", 0.30, // 30%
                "Mancha permanente", 0.20, // 20%
                "Zíper parcialmente funcional", 0.25, // 25%
                "Sola sem relevo funcional", 0.40 // 40%
        );
        return mapaDescontos.getOrDefault(defeito, 0.10); // 10% para outros defeitos
    }

    // REGRA DE NEGÓCIO: Simula o MCI com base no estado de conservação
    private double simularMCI(String estado) {
        switch (estado) {
            case "Muito Usado":
                return 0.85; // Mais circular
            case "Usado":
                return 0.60;
            case "Novo":
                return 0.30; // Menos circular
            default:
                return 0.50;
        }
    }
}