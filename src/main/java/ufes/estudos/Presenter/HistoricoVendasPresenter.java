package ufes.estudos.Presenter;

import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Model.transacao.Venda;
import ufes.estudos.Views.IHistoricoVendasView;
import ufes.estudos.repository.VendaRepository;
import java.util.List;
import java.util.stream.Collectors;

public class HistoricoVendasPresenter {
    private final IHistoricoVendasView view;

    public HistoricoVendasPresenter(IHistoricoVendasView view, Usuario vendedor) {
        this.view = view;
        carregarHistorico(vendedor);
    }

    private void carregarHistorico(Usuario vendedor) {
        List<Venda> todasVendas = VendaRepository.getInstance().getVendas();
        // A ÚNICA DIFERENÇA: Filtra pelo nome do VENDEDOR
        List<Venda> minhasVendas = todasVendas.stream()
                .filter(v -> v.getNomeVendedor().equals(vendedor.getNome()))
                .collect(Collectors.toList());
        view.atualizarTabela(minhasVendas);
    }
}