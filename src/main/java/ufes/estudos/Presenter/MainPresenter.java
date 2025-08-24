package ufes.estudos.Presenter;

import ufes.estudos.Model.State.CompradorState;
import ufes.estudos.Model.State.IMainState;
import ufes.estudos.Model.State.VendedorState;
import ufes.estudos.Model.Usuario.SolicitacaoPerfil;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IMainView;
import ufes.estudos.Views.MainView;
import ufes.estudos.Views.TelaLogin;
import ufes.estudos.repository.SolicitacaoRepository;

import javax.swing.*;

public class MainPresenter {
    private final IMainView view;
    private final IMainState state;
    private final Usuario usuario;

    public MainPresenter(IMainView view, IMainState state, Usuario usuario) {
        this.view = view;
        this.state = state;
        this.usuario = usuario;

        view.setNomeUsuarioLogado(usuario.getNome());

        state.configurarTela(view, usuario); // CHAMADA MODIFICADA
        this.view.setLogoutListener(e -> logout());
        configurarTrocaDePerfil();
    }

    private void configurarTrocaDePerfil() {
        // Lógica para quando o usuário está no painel de VENDEDOR
        if (state instanceof VendedorState) {
            if (usuario.isComprador()) {
                view.configurarBotaoTrocaPerfil("Trocar para Comprador", e -> trocarDePerfil(), true);
            } else {
                view.configurarBotaoTrocaPerfil("Solicitar Perfil de Comprador", e -> solicitarPerfil("Comprador"), true);
            }
        }
        // Lógica para quando o usuário está no painel de COMPRADOR
        else if (state instanceof CompradorState) {
            if (usuario.isVendedor()) {
                view.configurarBotaoTrocaPerfil("Trocar para Vendedor", e -> trocarDePerfil(), true);
            } else {
                view.configurarBotaoTrocaPerfil("Solicitar Perfil de Vendedor", e -> solicitarPerfil("Vendedor"), true);
            }
        }
        // Para qualquer outro tipo de painel (ex: Admin), o botão fica invisível
        else {
            view.configurarBotaoTrocaPerfil(null, null, false);
        }
    }

    private void trocarDePerfil() {
        view.fechar(); // Fecha a tela atual

        IMainState novoState;
        // Se estava como vendedor, o novo estado é de comprador, e vice-versa
        if (state instanceof VendedorState) {
            novoState = new CompradorState();
        } else {
            novoState = new VendedorState();
        }

        // Abre a nova tela principal com o estado trocado
        MainView novaView = new MainView();
        new MainPresenter(novaView, novoState, usuario);
        novaView.setVisible(true);
    }

    private void solicitarPerfil(String tipoPerfil) {
        SolicitacaoPerfil novaSolicitacao = new SolicitacaoPerfil(usuario.getUsuario(), tipoPerfil);
        SolicitacaoRepository.getInstance().addSolicitacao(novaSolicitacao);

        JOptionPane.showMessageDialog((JFrame) view,
                "Solicitação para se tornar um " + tipoPerfil + " foi enviada para análise.",
                "Solicitação Enviada",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        view.fechar();

        TelaLogin view = new TelaLogin();
        new LoginPresenter(view);
        view.setVisible(true);
    }
}