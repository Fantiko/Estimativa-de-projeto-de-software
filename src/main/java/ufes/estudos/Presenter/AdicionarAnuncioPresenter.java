package ufes.estudos.Presenter;

import ufes.estudos.Model.Item.Defeito;
import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Item.Material;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IAdicionarAnuncioView;
import ufes.estudos.repository.AnuncioRepository;
import ufes.estudos.service.IdService; // 1. Importar o novo serviço

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class AdicionarAnuncioPresenter {
    private final IAdicionarAnuncioView view;
    private final Map<String, String[]> defeitosMap;
    private final IdService idService;
    private final AnuncioRepository anuncioRepository;
    private final Usuario usuario; // CAMPO ADICIONADO



    public AdicionarAnuncioPresenter(IAdicionarAnuncioView view, Usuario usuario) { // PARÂMETRO ADICIONADO
        this.view = view;
        this.usuario = usuario; // ATRIBUIÇÃO ADICIONADA
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

            String idc = view.getIdc();
            if (idc.isBlank()) {
                // 4. Usar a instância do serviço para gerar o ID
                do {
                    idc = idService.generate();
                } while (!idService.isUnique(idc)); // Prepara para a futura verificação de unicidade
            }

            // TODO: Adicionar lógica para verificar limite de 30 anúncios por vendedor.

            Material material = new Material(view.getComposicao(), 0.0);
            Defeito defeito = new Defeito(view.getDefeito(), 0.0);

            Item novoItem = new Item(
                    idc, view.getTipoPeca(), view.getSubcategoria(), view.getTamanho(),
                    view.getCor(), material, defeito, view.getEstado(), massa, preco,
                    usuario.getNome() // <<< ADICIONE O NOME DO VENDEDOR AQUI
            );

            // TODO: Persistir o 'novoItem'
            anuncioRepository.addAnuncio(novoItem);

            view.exibirMensagem("Anúncio salvo com sucesso!\nID-C: " + novoItem.getIdentificadorCircular());
            view.fechar();

        } catch (NumberFormatException ex) {
            view.exibirMensagem("Massa e Preço devem ser valores numéricos válidos.");
        }
    }
}