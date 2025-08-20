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
        Item itemEditado = view.getDadosAnuncioEditado();
        if (itemEditado != null) {
            anuncioRepository.updateAnuncio(itemEditado);
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
    public void update() {
        // Se o item que esta tela está exibindo for alterado ou deletado,
        // a tela se fecha para evitar inconsistência de dados.
        Item itemAtual = anuncioRepository.findByIdc(itemOriginal.getIdentificadorCircular());
        if (itemAtual == null) {
            view.exibirMensagem("Este anúncio foi removido por outra ação.");
            view.fechar();
        } else {
            // Atualiza a tela caso os dados tenham sido alterados externamente
            this.itemOriginal = itemAtual;
            view.exibirAnuncio(itemAtual);
        }
    }
}