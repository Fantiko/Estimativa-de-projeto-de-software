package ufes.estudos.Presenter;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.Views.IDetalhesAnuncioView;
import ufes.estudos.observer.Observer;
import ufes.estudos.repository.AnuncioRepository;

import javax.swing.JOptionPane;

public class DetalhesAnuncioPresenter implements Observer {
    private final IDetalhesAnuncioView view;
    private final AnuncioRepository anuncioRepository;
    private Item itemOriginal;

    public DetalhesAnuncioPresenter(IDetalhesAnuncioView view, Item item) {
        this.view = view;
        this.itemOriginal = item;
        this.anuncioRepository = AnuncioRepository.getInstance();
        this.anuncioRepository.addObserver(this); // Observa mudanças no repositório

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
        Item dadosEditadosDaView = view.getDadosAnuncioEditado();
        if (dadosEditadosDaView != null) {
            // Recria o Item com os dados da edição mais os dados que não mudam
            Item itemFinal = new Item(
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
                    this.itemOriginal.getIdVendedor(),    // Passa o nome do vendedor original
                    this.itemOriginal.getGwpBase(),         // <<< Passa o gwpBase original
                    this.itemOriginal.getGwpAvoided()       // <<< Passa o gwpAvoided original
            );

            anuncioRepository.updateAnuncio(itemFinal);
            view.exibirMensagem("Anúncio atualizado com sucesso!");
            view.fechar();
        } else {
            view.exibirMensagem("Erro nos dados. Verifique os campos numéricos.");
        }
    }

    private void deletarAnuncio() {
        int confirmacao = view.exibirConfirmacaoDelecao();
        // 0 corresponde ao botão "Apagar"
        if (confirmacao == JOptionPane.YES_OPTION) {
            anuncioRepository.deleteAnuncio(itemOriginal.getIdentificadorCircular());
            view.exibirMensagem("Anúncio removido com sucesso!");
            view.fechar();
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
        if ("UPDATE".equals(tipoNotificacao) && dados instanceof Item) {
            Item itemAtualizado = (Item) dados;
            // Verifica se a atualização é sobre o item que esta tela está mostrando
            if (itemAtualizado.getIdentificadorCircular().equals(idcDoItemEmTela)) {
                this.itemOriginal = itemAtualizado;
                view.exibirAnuncio(itemAtualizado);
            }
        }
        // Se a notificação for de DELETE
        else if ("DELETE".equals(tipoNotificacao) && dados instanceof String) {
            String idcRemovido = (String) dados;
            // Verifica se a remoção é do item que esta tela está mostrando
            if (idcRemovido.equals(idcDoItemEmTela)) {
                view.exibirMensagem("Este anúncio foi removido por outra ação.");
                view.fechar();
            }
        }
        // Notificações do tipo "ADD" serão simplesmente ignoradas.
    }
}