package ufes.estudos.Presenter;

import ufes.estudos.Model.Usuario.SolicitacaoPerfil;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IAprovarPerfisView;
import ufes.estudos.observer.Observer;
import ufes.estudos.repository.SolicitacaoRepository;
import ufes.estudos.repository.UsuarioRepository;
import javax.swing.*;

public class AprovarPerfisPresenter implements Observer {
    private final IAprovarPerfisView view;
    private final SolicitacaoRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public AprovarPerfisPresenter(IAprovarPerfisView view) {
        this.view = view;
        this.solicitacaoRepository = SolicitacaoRepository.getInstance();
        this.usuarioRepository = UsuarioRepository.getInstance();

        // Se inscreve para receber notificações de novas solicitações
        this.solicitacaoRepository.addObserver(this);

        this.view.setAprovarListener(e -> processarSolicitacao(true));
        this.view.setReprovarListener(e -> processarSolicitacao(false));

        carregarSolicitacoes();
    }

    private void carregarSolicitacoes() {
        view.atualizarTabela(solicitacaoRepository.getSolicitacoes());
    }

    private void processarSolicitacao(boolean aprovar) {
        int selectedRow = view.getTabela().getSelectedRow();
        if (selectedRow < 0) {
            view.exibirMensagem("Por favor, selecione uma solicitação.");
            return;
        }

        String nomeUsuario = (String) view.getTabela().getValueAt(selectedRow, 0);
        String perfilSolicitado = (String) view.getTabela().getValueAt(selectedRow, 1);

        if (aprovar) {
            Usuario usuario = usuarioRepository.findByUsername(nomeUsuario);
            if (usuario != null) {
                if (perfilSolicitado.equals("Vendedor")) {
                    usuario.setVendedor(true);
                } else if (perfilSolicitado.equals("Comprador")) {
                    usuario.setComprador(true);
                }
                view.exibirMensagem("Usuário " + nomeUsuario + " agora é " + perfilSolicitado + "!");
            } else {
                view.exibirMensagem("Erro: Usuário não encontrado.");
            }
        }

        // Remove a solicitação da lista, seja aprovada ou reprovada
        solicitacaoRepository.removeSolicitacao(nomeUsuario, perfilSolicitado);
        // A tabela será atualizada automaticamente pelo Observer
    }

    @Override
    public void update(String tipoNotificacao, Object dados) {
        // Qualquer mudança na lista de solicitações, recarrega a tabela
        carregarSolicitacoes();
    }
}