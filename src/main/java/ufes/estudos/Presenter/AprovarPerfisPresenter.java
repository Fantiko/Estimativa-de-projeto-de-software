package ufes.estudos.Presenter;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Views.IAprovarPerfisView;
import ufes.estudos.observer.Observer;
import ufes.estudos.repository.RepositoriesSQLite.PerfilCompradorSQLiteRepository;
import ufes.estudos.repository.RepositoriesSQLite.PerfilVendedorSQLiteRepository;
import ufes.estudos.repository.RepositoriesSQLite.UsuarioSQLiteRepository;
import ufes.estudos.repository.SolicitacaoRepository;
import ufes.estudos.service.PerfilCompradorService;
import ufes.estudos.service.PerfilVendedorService;
import ufes.estudos.service.UsuarioService;

import java.util.Optional;

public class AprovarPerfisPresenter implements Observer {
    private final IAprovarPerfisView view;
    private final SolicitacaoRepository solicitacaoRepository;
    // --- SERVIÇOS QUE CONVERSAM COM O BANCO ---
    private final UsuarioService usuarioService;
    private final PerfilVendedorService perfilVendedorService;
    private final PerfilCompradorService perfilCompradorService;

    public AprovarPerfisPresenter(IAprovarPerfisView view) {
        this.view = view;
        this.solicitacaoRepository = SolicitacaoRepository.getInstance();

        // Inicializa os serviços com as implementações do SQLite
        this.usuarioService = new UsuarioService(new UsuarioSQLiteRepository(new SQLiteConnectionManager()));
        this.perfilVendedorService = new PerfilVendedorService(new PerfilVendedorSQLiteRepository(new SQLiteConnectionManager()));
        this.perfilCompradorService = new PerfilCompradorService(new PerfilCompradorSQLiteRepository());


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
            Optional<Usuario> optionalUsuario = usuarioService.buscarPorUsuario(nomeUsuario);

            if (optionalUsuario.isPresent()) {
                Usuario usuario = optionalUsuario.get();

                // 1. ATUALIZA O STATUS DO USUÁRIO
                if (perfilSolicitado.equals("Vendedor")) {
                    usuario.setVendedor(true);
                    perfilVendedorService.criarPerfilVendedor(usuario); // Cria o perfil na tabela de vendedores
                } else if (perfilSolicitado.equals("Comprador")) {
                    usuario.setComprador(true);
                    perfilCompradorService.criarPerfilComprador(usuario); // Cria o perfil na tabela de compradores
                }

                // 2. SALVA A ALTERAÇÃO NO BANCO DE DADOS
                usuarioService.atualizar(usuario);
                view.exibirMensagem("Perfil de " + perfilSolicitado + " aprovado para " + nomeUsuario + "!");

            } else {
                view.exibirMensagem("Erro: Usuário '" + nomeUsuario + "' não foi encontrado no banco de dados.");
            }
        }

        // 3. REMOVE A SOLICITAÇÃO DA LISTA
        solicitacaoRepository.removeSolicitacao(nomeUsuario, perfilSolicitado);
    }

    @Override
    public void update(String tipoNotificacao, Object dados) {
        carregarSolicitacoes();
    }
}