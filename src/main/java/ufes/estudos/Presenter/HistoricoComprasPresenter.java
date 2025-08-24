package ufes.estudos.Presenter;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Model.transacao.Venda;
import ufes.estudos.Views.IHistoricoComprasView;
import ufes.estudos.repository.VendaRepository;
import java.util.List;
import java.util.stream.Collectors;

public class HistoricoComprasPresenter {
    private final IHistoricoComprasView view;

    public HistoricoComprasPresenter(IHistoricoComprasView view, Usuario comprador) {
        this.view = view;
        carregarHistorico(comprador);
    }

    private void carregarHistorico(Usuario comprador) {
        List<Venda> todasVendas = VendaRepository.getInstance().getVendas();
        List<Venda> minhasVendas = todasVendas.stream()
                .filter(v -> v.getNomeComprador().equals(comprador.getNome()))
                .collect(Collectors.toList());
        view.atualizarTabela(minhasVendas);
    }
}