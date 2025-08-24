package ufes.estudos.Presenter;

import ufes.estudos.Model.Usuario.SolicitacaoPerfil;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IEscolherPerfilCadastroView;
import ufes.estudos.Views.TelaLogin;
import ufes.estudos.repository.SolicitacaoRepository;

public class EscolherPerfilCadastroPresenter {
    private final IEscolherPerfilCadastroView view;
    private final Usuario usuario;
    private final SolicitacaoRepository solicitacaoRepository;

    public EscolherPerfilCadastroPresenter(IEscolherPerfilCadastroView view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;
        this.solicitacaoRepository = SolicitacaoRepository.getInstance();
        this.view.setFinalizarListener(e -> finalizar());
    }

    private void finalizar() {
        boolean vendedor = view.isVendedorSelecionado();
        boolean comprador = view.isCompradorSelecionado();

        if (!vendedor && !comprador) {
            view.exibirMensagem("Você deve selecionar pelo menos um perfil para solicitar.");
            return;
        }

        if (vendedor) {
            solicitacaoRepository.addSolicitacao(new SolicitacaoPerfil(usuario.getUsuario(), "Vendedor"));
        }
        if (comprador) {
            solicitacaoRepository.addSolicitacao(new SolicitacaoPerfil(usuario.getUsuario(), "Comprador"));
        }

        view.exibirMensagem("Cadastro concluído! Suas solicitações de perfil foram enviadas para análise do administrador.");

        view.fechar();
        TelaLogin telaLogin = new TelaLogin();
        new LoginPresenter(telaLogin);
        telaLogin.setVisible(true);
    }
}