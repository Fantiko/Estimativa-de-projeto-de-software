package ufes.estudos.Presenter;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.transacao.Oferta;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IMinhasOfertasView;
import ufes.estudos.Views.TelaNegociacao;
import ufes.estudos.observer.Observer;
import ufes.estudos.repository.AnuncioRepository;
import ufes.estudos.repository.OfertaRepository;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class MinhasOfertasPresenter implements Observer {
    private final IMinhasOfertasView view;
    private final Usuario comprador;
    private final OfertaRepository ofertaRepository;
    private final AnuncioRepository anuncioRepository;

    public MinhasOfertasPresenter(IMinhasOfertasView view, Usuario comprador) {
        this.view = view;
        this.comprador = comprador;
        this.ofertaRepository = OfertaRepository.getInstance();
        this.anuncioRepository = AnuncioRepository.getInstance();
        this.ofertaRepository.addObserver(this);

        this.view.setAlterarListener(e -> alterarOferta());
        this.view.setCancelarListener(e -> cancelarOferta());

        carregarMinhasOfertas();
    }

    private void carregarMinhasOfertas() {
        List<Oferta> minhasOfertas = ofertaRepository.getOfertas().stream()
                .filter(o -> o.getIdComprador() == comprador.getId())
                .collect(Collectors.toList());
        view.atualizarTabela(minhasOfertas);
    }

    private void cancelarOferta() {
        Oferta ofertaSelecionada = getOfertaSelecionada();
        if (ofertaSelecionada != null) {
            ofertaRepository.removeOferta(ofertaSelecionada);
            view.exibirMensagem("Oferta cancelada com sucesso.");
        }
    }

    private void alterarOferta() {
        Oferta ofertaSelecionada = getOfertaSelecionada();
        if (ofertaSelecionada == null) return;

        Item itemOfertado = anuncioRepository.findByIdc(ofertaSelecionada.getIdcItem());
        if (itemOfertado == null) {
            view.exibirMensagem("Este item não está mais disponível para negociação.");
            ofertaRepository.removeOferta(ofertaSelecionada);
            return;
        }

        double precoFinalAnuncio = itemOfertado.getPrecoBase() * (1 - itemOfertado.getDefeito().getPercentual());

        JFrame framePrincipal = (JFrame) SwingUtilities.getWindowAncestor((JComponent) view);
        TelaNegociacao tela = new TelaNegociacao(framePrincipal);
        new NegociacaoPresenter(tela, itemOfertado, this.comprador, precoFinalAnuncio);
        tela.setVisible(true);
    }

    // --- MÉTODO CORRIGIDO AQUI ---
    private Oferta getOfertaSelecionada() {
        int selectedRow = view.getTabelaOfertas().getSelectedRow();
        if (selectedRow < 0) {
            view.exibirMensagem("Selecione uma oferta.");
            return null;
        }
        String idc = (String) view.getTabelaOfertas().getValueAt(selectedRow, 0);

        // A comparação agora é feita usando os IDs
        return ofertaRepository.getOfertas().stream()
                .filter(o -> o.getIdcItem().equals(idc) && o.getIdComprador() == comprador.getId())
                .findFirst().orElse(null);
    }

    @Override
    public void update(String tipoNotificacao, Object dados) {
        carregarMinhasOfertas();
    }
}