package ufes.estudos.Presenter;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Usuario.Usuario; // IMPORT ADICIONADO
import ufes.estudos.Views.ICatalogoView;
import ufes.estudos.repository.AnuncioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CatalogoPresenter {
    private final ICatalogoView view;
    private final AnuncioRepository anuncioRepository;
    private final Usuario usuario; // CAMPO ADICIONADO

    public CatalogoPresenter(ICatalogoView view, Usuario usuario) { // PARÂMETRO ADICIONADO
        this.view = view;
        this.usuario = usuario; // ATRIBUIÇÃO ADICIONADA
        this.anuncioRepository = AnuncioRepository.getInstance();

        carregarCatalogo();

        this.view.setComprarListener(e -> comprarItemSelecionado());
        this.view.setHistoricoListener(e -> view.exibirMensagem("Funcionalidade 'Histórico de Compras' ainda não implementada."));
    }

    private void carregarCatalogo() {
        List<Item> todosAnuncios = anuncioRepository.getAnuncios();
        List<Object[]> dadosParaTabela = new ArrayList<>();

        for (Item item : todosAnuncios) {
            // AQUI ESTÁ O FILTRO: Pula o item se o nome do vendedor for igual ao do usuário logado
            if (item.getNomeVendedor().equals(this.usuario.getNome())) {
                continue; // Pula para a próxima iteração do loop
            }

            double precoBase = item.getPrecoBase();
            double percentualDesconto = calcularDescontoPorDefeito(item.getDefeito().getDefeito());
            double precoFinal = precoBase * (1 - percentualDesconto);
            double gwpEvitado = item.getMassaEstimada() * item.getMaterial().getFatorEmissao(); // Simulação
            double mciSimulado = simularMCI(item.getEstadoConservacao());

            dadosParaTabela.add(new Object[]{
                    item.getIdentificadorCircular(),
                    item.getTipoPeca(),
                    item.getNomeVendedor(),
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
    }

    private double calcularDescontoPorDefeito(String defeito) {
        Map<String, Double> mapaDescontos = Map.of(
                "Rasgo estruturante", 0.30,
                "Mancha permanente", 0.20,
                "Zíper parcialmente funcional", 0.25,
                "Sola sem relevo funcional", 0.40
        );
        return mapaDescontos.getOrDefault(defeito, 0.10);
    }

    private double simularMCI(String estado) {
        return switch (estado) {
            case "Muito Usado" -> 0.85;
            case "Usado" -> 0.60;
            case "Novo" -> 0.30;
            default -> 0.50;
        };
    }
}