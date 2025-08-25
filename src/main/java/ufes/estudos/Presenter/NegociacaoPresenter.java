package ufes.estudos.Presenter;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.eventos.EventoTimeline;
import ufes.estudos.Model.eventos.TipoEvento;
import ufes.estudos.Model.transacao.Oferta;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.INegociacaoView;
import ufes.estudos.repository.OfertaRepository;
import ufes.estudos.repository.PerfilRepository;
import ufes.estudos.repository.TimelineRepository;
import ufes.estudos.service.*;

public class NegociacaoPresenter {
    private final INegociacaoView view;
    private final Item item;
    private final Usuario comprador;
    private final double precoFinalAnuncio;
    private final double valorMinimoOferta;
    private final double valorMaximoOferta;

    public NegociacaoPresenter(INegociacaoView view, Item item, Usuario comprador, double precoFinalAnuncio) {
        this.view = view;
        this.item = item;
        this.comprador = comprador;
        this.precoFinalAnuncio = precoFinalAnuncio;

        // Regra de negócio: oferta de 1% a 20% abaixo do valor
        this.valorMinimoOferta = precoFinalAnuncio * 0.80; // 20% de desconto
        this.valorMaximoOferta = precoFinalAnuncio * 0.99; // 1% de desconto

        inicializar();
    }

    private void inicializar() {
        view.setNomeItem(item.getTipoPeca() + " - " + item.getSubcategoria());
        view.setPrecoOriginal(String.format("R$ %.2f", precoFinalAnuncio));
        view.setFaixaDeOferta(String.format("R$ %.2f a R$ %.2f", valorMinimoOferta, valorMaximoOferta));
        view.setEnviarOfertaListener(e -> enviarOferta());
    }

    private void enviarOferta() {
        try {
            double valorOfertado = Double.parseDouble(view.getValorOferta().replace(',', '.'));

            if (valorOfertado < valorMinimoOferta || valorOfertado > valorMaximoOferta) {
                view.exibirMensagem("Valor da oferta fora da faixa permitida!");
                return;
            }

            Oferta novaOferta = new Oferta(item.getIdentificadorCircular(), comprador.getId(), item.getIdVendedor(), valorOfertado);
            OfertaRepository.getInstance().addOferta(novaOferta);

            // --- CÓDIGO DA LINHA DO TEMPO ADICIONADO AQUI ---
            double mci = 1 - item.getDefeito().getPercentual();
            String detalhes = "Oferta de R$ " + String.format("%.2f", valorOfertado) + " feita pelo comprador " + comprador.getNome();
            EventoTimeline evento = new EventoTimeline(item.getIdentificadorCircular(), TipoEvento.OFERTA_ENVIADA, item.getGwpAvoided(), mci, detalhes);
            TimelineRepository.getInstance().addEvento(evento);
            // --- FIM DO CÓDIGO ADICIONADO ---

            ReputacaoService.getInstance().processarOfertaEnviada(PerfilRepository.getInstance().getComprador(comprador.getNome()));
            view.exibirMensagem("Oferta enviada com sucesso!");
            view.fechar();

        } catch (NumberFormatException ex) {
            view.exibirMensagem("Por favor, insira um valor numérico válido para a oferta.");
        }
    }
}