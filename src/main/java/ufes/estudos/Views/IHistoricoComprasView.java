package ufes.estudos.Views;

import ufes.estudos.Model.transacao.Venda;
import java.util.List;

public interface IHistoricoComprasView {
    void atualizarTabela(List<Venda> vendas);
}