package ufes.estudos.Presenter;

import ufes.estudos.Model.Item.Defeito;
import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Item.Material;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Model.eventos.EventoTimeline;
import ufes.estudos.Model.eventos.TipoEvento;
import ufes.estudos.Views.IAdicionarAnuncioView;
import ufes.estudos.repository.AnuncioRepository;
import ufes.estudos.repository.PerfilRepository;
import ufes.estudos.repository.TimelineRepository;
import ufes.estudos.service.IdService; // 1. Importar o novo serviço
import ufes.estudos.service.ReputacaoService;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AdicionarAnuncioPresenter {
    private final IAdicionarAnuncioView view;
    private final Map<String, String[]> defeitosMap;
    private final IdService idService;
    private final AnuncioRepository anuncioRepository;
    private final Usuario usuario;

    private static final Map<String, Double> FATORES_EMISSAO = Map.of(
            "Algodão", 1.5, "Poliéster", 5.5, "Couro", 17.0, "Metal", 2.0,
            "Plástico de base fóssil", 3.0, "Outros", 2.5
    );
    private static final Map<String, Double> PERCENTUAIS_DEFEITO;
    static {
        PERCENTUAIS_DEFEITO = Map.ofEntries(Map.entry("Rasgo estruturante", 0.30),
                Map.entry("Ausência de botão principal", 0.15),
                Map.entry("Zíper parcialmente funcional", 0.25),
                Map.entry("Mancha permanente", 0.20),
                Map.entry("Desgaste por pilling acentuado", 0.10),
                Map.entry("Sola sem relevo funcional", 0.40),
                Map.entry("Descolamento parcial da entressola", 0.35),
                Map.entry("Arranhões profundos", 0.20),
                Map.entry("Palmilha original ausente", 0.10),
                Map.entry("Odor persistente leve", 0.05),
                Map.entry("Alça reparada", 0.15),
                Map.entry("Fecho defeituoso", 0.20),
                Map.entry("Desbotamento extenso", 0.10),
                Map.entry("Forro rasgado", 0.25),
                Map.entry("Oxidação visível", 0.15),
                Map.entry("Pedra ausente", 0.20),
                Map.entry("Fecho frouxo", 0.10),
                Map.entry("", 0.0));
    }


    public AdicionarAnuncioPresenter(IAdicionarAnuncioView view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;
        this.defeitosMap = carregarDefeitos();
        this.idService = new IdService();
        this.anuncioRepository = AnuncioRepository.getInstance();

        this.view.setSalvarListener(this::salvarAnuncio);
        this.view.setCancelarListener(e -> this.view.fechar());
        this.view.setTipoPecaListener(this::atualizarDefeitos);
        atualizarDefeitos(null);
    }

    private Map<String, String[]> carregarDefeitos() {
        Map<String, String[]> map = new HashMap<>();
        map.put("Vestuário", new String[]{"", "Rasgo estruturante", "Ausência de botão principal", "Zíper parcialmente funcional", "Mancha permanente", "Desgaste por pilling acentuado"});
        map.put("Calçados", new String[]{"", "Sola sem relevo funcional", "Descolamento parcial da entressola", "Arranhões profundos", "Palmilha original ausente", "Odor persistente leve"});
        map.put("Bolsas e Mochilas", new String[]{"", "Alça reparada", "Fecho defeituoso", "Desbotamento extenso", "Forro rasgado"});
        map.put("Bijuterias e Acessórios", new String[]{"", "Oxidação visível", "Pedra ausente", "Fecho frouxo"});
        return map;
    }

    private void atualizarDefeitos(ActionEvent e) {
        String tipoPeca = view.getTipoPeca();
        String[] defeitos = defeitosMap.getOrDefault(tipoPeca, new String[]{""});
        view.setDefeitosOptions(defeitos);
    }

    private void salvarAnuncio(ActionEvent e) {
        if (view.getTipoPeca().isEmpty() || view.getSubcategoria().isBlank() || view.getTamanho().isBlank() ||
                view.getCor().isBlank() || view.getComposicao().isEmpty() || view.getMassa().isBlank() ||
                view.getEstado().isEmpty() || view.getDefeito().isEmpty() || view.getPreco().isBlank()) {
            view.exibirMensagem("Todos os campos, exceto ID-C, são obrigatórios.");
            return;
        }

        try {
            double massa = Double.parseDouble(view.getMassa().replace(',', '.'));
            double preco = Double.parseDouble(view.getPreco().replace(',', '.'));
            String nomeMaterial = view.getComposicao();
            String nomeDefeito = view.getDefeito();

            // 1. CALCULAR GWP
            // Simplificação: assumimos 100% do material principal (fração_i = 1.0)
            double ef = FATORES_EMISSAO.getOrDefault(nomeMaterial, 2.5);
            double gwpBase = massa * ef;
            double gwpAvoided = gwpBase - (0.05 * gwpBase); // ou gwpBase * 0.95

            // 2. OBTER DADOS DO DEFEITO
            double percentualDefeito = PERCENTUAIS_DEFEITO.getOrDefault(nomeDefeito, 0.0);

            String idc = view.getIdc();
            if (idc.isBlank()) {
                // 4. Usar a instância do serviço para gerar o ID
                do {
                    idc = idService.generate();
                } while (!idService.isUnique(idc)); // Prepara para a futura verificação de unicidade
            }

            // TODO: Adicionar lógica para verificar limite de 30 anúncios por vendedor.

            Material material = new Material(nomeMaterial, ef);
            Defeito defeito = new Defeito(nomeDefeito, percentualDefeito);

            Item novoItem = new Item(
                    idc, view.getTipoPeca(), view.getSubcategoria(), view.getTamanho(),
                    view.getCor(), material, defeito, view.getEstado(), massa, preco,
                    usuario.getNome(), gwpBase, gwpAvoided
            );

            anuncioRepository.addAnuncio(novoItem);
            double mci = 1 - defeito.getPercentual();
            String detalhes = "Item publicado pelo vendedor " + usuario.getNome() + ". Ciclo: " + novoItem.getCiclo();
            EventoTimeline evento = new EventoTimeline(novoItem.getIdentificadorCircular(), TipoEvento.PUBLICADO, novoItem.getGwpAvoided(), mci, detalhes);
            TimelineRepository.getInstance().addEvento(evento);
            ReputacaoService.getInstance().processarCadastroItemCompleto(PerfilRepository.getInstance().getVendedor(usuario.getNome()));
            view.exibirMensagem("Anúncio salvo com sucesso!\nID-C: " + novoItem.getIdentificadorCircular());
            view.fechar();

        } catch (NumberFormatException ex) {
            view.exibirMensagem("Massa e Preço devem ser valores numéricos válidos.");
        }
    }


}