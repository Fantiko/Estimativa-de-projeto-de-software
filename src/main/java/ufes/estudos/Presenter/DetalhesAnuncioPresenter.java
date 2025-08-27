package ufes.estudos.Presenter;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IDetalhesAnuncioView;
import ufes.estudos.observer.Observer;
import ufes.estudos.repository.RepositoriesIntefaces.AnuncioRepository;
import ufes.estudos.repository.RepositoriesSQLite.AnuncioSQLiteRepository;
import ufes.estudos.service.LogService;

import javax.swing.*;

public class DetalhesAnuncioPresenter implements Observer {
    private final IDetalhesAnuncioView view;
    private final AnuncioRepository anuncioRepository;
    private Item itemOriginal;
    private final Usuario usuarioLogado; // Necessário para o log

    public DetalhesAnuncioPresenter(IDetalhesAnuncioView view, Item item, Usuario usuarioLogado) {
        this.view = view;
        this.itemOriginal = item;
        this.usuarioLogado = usuarioLogado; // Guarda o usuário que abriu a tela
        this.anuncioRepository = new AnuncioSQLiteRepository(new SQLiteConnectionManager());
        this.anuncioRepository.addObserver(this);

        this.view.exibirAnuncio(item);
        this.view.setEditarListener(e -> entrarModoEdicao());
        this.view.setSalvarListener(e -> salvarEdicao());
        this.view.setDeletarListener(e -> deletarAnuncio());
        this.view.setCancelarListener(e -> cancelarEdicao());
    }

    private void entrarModoEdicao() {
        view.setCamposEditaveis(true);
        view.setModoEdicao(true);
    }

    private void salvarEdicao() {
        Item itemFinal = null;
        try {
            Item dadosEditadosDaView = view.getDadosAnuncioEditado();
            if (dadosEditadosDaView == null) {
                view.exibirMensagem("Erro nos dados. Verifique os campos numéricos.");
                return;
            }

            // Recria o Item com os dados da edição mais os dados que não mudam
            itemFinal = new Item(
                    dadosEditadosDaView.getIdentificadorCircular(),
                    dadosEditadosDaView.getTipoPeca(),
                    dadosEditadosDaView.getSubcategoria(),
                    dadosEditadosDaView.getTamanho(),
                    dadosEditadosDaView.getCorPredominante(),
                    dadosEditadosDaView.getMaterial(),
                    dadosEditadosDaView.getDefeito(),
                    dadosEditadosDaView.getEstadoConservacao(),
                    dadosEditadosDaView.getMassaEstimada(),
                    dadosEditadosDaView.getPrecoBase(),
                    this.itemOriginal.getVendedorId(),
                    this.itemOriginal.getGwpBase(),
                    this.itemOriginal.getGwpAvoided()
            );

            anuncioRepository.updateAnuncio(itemFinal);

            // --- LOG DE SUCESSO ---
            LogService.getInstance().getLogger().logSucesso("Alteração de item", itemFinal.getIdentificadorCircular(), itemFinal.getTipoPeca(), usuarioLogado);

            view.exibirMensagem("Anúncio atualizado com sucesso!");
            view.fechar();

        } catch (Exception ex) {
            String idc = (itemFinal != null) ? itemFinal.getIdentificadorCircular() : itemOriginal.getIdentificadorCircular();
            // --- LOG DE FALHA ---
            LogService.getInstance().getLogger().logFalha("Alteração de item", ex.getMessage(), "Item", usuarioLogado, idc);
            view.exibirMensagem("Erro ao salvar a edição: " + ex.getMessage());
        }
    }

    private void deletarAnuncio() {
        try {
            int confirmacao = view.exibirConfirmacaoDelecao();
            if (confirmacao == JOptionPane.YES_OPTION) {
                String idc = itemOriginal.getIdentificadorCircular();
                String nomeItem = itemOriginal.getTipoPeca();

                anuncioRepository.deleteAnuncio(idc);

                // --- LOG DE SUCESSO ---
                LogService.getInstance().getLogger().logSucesso("Exclusão de item", idc, nomeItem, usuarioLogado);

                view.exibirMensagem("Anúncio removido com sucesso!");
                view.fechar();
            }
        } catch (Exception ex) {
            // --- LOG DE FALHA ---
            LogService.getInstance().getLogger().logFalha("Exclusão de item", ex.getMessage(), "Item", usuarioLogado, itemOriginal.getIdentificadorCircular());
            view.exibirMensagem("Erro ao deletar o anúncio: " + ex.getMessage());
        }
    }

    private void cancelarEdicao() {
        view.exibirAnuncio(itemOriginal); // Restaura os dados originais
        view.setCamposEditaveis(false);
        view.setModoEdicao(false);
    }

    @Override
    public void update(String tipoNotificacao, Object dados) {
        String idcDoItemEmTela = itemOriginal.getIdentificadorCircular();

        // Se a notificação for de UPDATE
        if ("UPDATE_ITEM".equals(tipoNotificacao) && dados instanceof Item itemAtualizado) {
            if (itemAtualizado.getIdentificadorCircular().equals(idcDoItemEmTela)) {
                this.itemOriginal = itemAtualizado;
                view.exibirAnuncio(itemAtualizado);
            }
        }
        // Se a notificação for de DELETE
        else if ("DELETE_ITEM".equals(tipoNotificacao) && dados instanceof String idcRemovido) {
            if (idcRemovido.equals(idcDoItemEmTela)) {
                view.exibirMensagem("Este anúncio foi removido por outra ação.");
                this.anuncioRepository.removeObserver(this); // Deixa de observar
                view.fechar();
            }
        }
    }
}