package ufes.estudos.Presenter;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Model.transacao.Oferta;
import ufes.estudos.Model.transacao.Venda;
import ufes.estudos.Views.IGerenciarOfertasView;
import ufes.estudos.observer.Observer;
import ufes.estudos.repository.RepositoriesIntefaces.AnuncioRepository;
import ufes.estudos.repository.OfertaRepository;
import ufes.estudos.repository.PerfilRepository;
import ufes.estudos.repository.RepositoriesIntefaces.UsuarioRepository;
import ufes.estudos.repository.RepositoriesSQLite.*;
import ufes.estudos.repository.VendaRepository;
import ufes.estudos.service.LogService;
import ufes.estudos.service.PerfilCompradorService;
import ufes.estudos.service.PerfilVendedorService;
import ufes.estudos.service.ReputacaoService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GerenciarOfertasPresenter implements Observer {
    private final IGerenciarOfertasView view;
    private final Usuario vendedorUsuario;
    private final OfertaRepository ofertaRepository;
    private final AnuncioRepository anuncioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final VendaRepository vendaRepository;

    // --- SERVIÇOS ADICIONADOS PARA ATUALIZAR PERFIS ---
    private final PerfilVendedorService perfilVendedorService;
    private final PerfilCompradorService perfilCompradorService;

    private List<Oferta> minhasOfertasCache;

    public GerenciarOfertasPresenter(IGerenciarOfertasView view, Usuario vendedor) {
        this.view = view;
        this.vendedorUsuario = vendedor;
        this.ofertaRepository = OfertaRepository.getInstance();
        this.anuncioRepository = new AnuncioSQLiteRepository(new SQLiteConnectionManager());
        this.perfilRepository = PerfilRepository.getInstance();
        this.vendaRepository = VendaRepository.getInstance();

        SQLiteConnectionManager manager = new SQLiteConnectionManager();
        this.usuarioRepository = new UsuarioSQLiteRepository(manager);

        // --- INICIALIZAÇÃO DOS SERVIÇOS ---
        this.perfilVendedorService = new PerfilVendedorService(
                new PerfilVendedorSQLiteRepository(manager),
                new InsigniasSQLiteRepository(manager)
        );
        this.perfilCompradorService = new PerfilCompradorService(
                new PerfilCompradorSQLiteRepository(manager),
                new InsigniasSQLiteRepository(manager)
        );

        this.ofertaRepository.addObserver(this);
        this.view.setAceitarListener(e -> aceitarOferta());
        this.view.setRecusarListener(e -> recusarOferta());
        carregarOfertas();
    }

    private void aceitarOferta() {
        Oferta ofertaGanhadora = getOfertaSelecionada();
        if (ofertaGanhadora == null) return;

        Item itemVendido = anuncioRepository.findByIdc(ofertaGanhadora.getIdcItem()).orElse(null);
        if (itemVendido == null) {
            view.exibirMensagem("Erro: O item do anúncio não foi encontrado. A oferta será removida.");
            ofertaRepository.removerOfertasPorItem(ofertaGanhadora.getIdcItem());
            return;
        }

        Usuario compradorUsuario = usuarioRepository.buscarPorId(ofertaGanhadora.getIdComprador()).orElse(null);
        if(compradorUsuario == null){
            view.exibirMensagem("Erro: Comprador não encontrado no sistema.");
            return;
        }

        // O PerfilRepository em memória é usado para obter o objeto de perfil da sessão atual
        PerfilVendedor perfilVendedor = perfilRepository.getVendedor(vendedorUsuario.getNome());
        PerfilComprador perfilComprador = perfilRepository.getComprador(compradorUsuario.getNome());

        double gwpEvitado = itemVendido.getGwpAvoided();

        // ATUALIZA OS INDICADORES E SALVA NO BANCO
        if(perfilVendedor != null) {
            perfilVendedor.setBeneficioClimaticoContribuido(perfilVendedor.getBeneficioClimaticoContribuido() + gwpEvitado);
            perfilVendedor.setVendasConcluidas(perfilVendedor.getVendasConcluidas() + 1);
            perfilVendedorService.atualizar(perfilVendedor); // Salva no DB
        }
        if(perfilComprador != null) {
            perfilComprador.setCO2Evitado(perfilComprador.getCO2Evitado() + gwpEvitado);
            perfilComprador.setComprasFinalizadas(perfilComprador.getComprasFinalizadas() + 1);
            perfilCompradorService.atualizar(perfilComprador); // Salva no DB
        }

        // REGISTRA A VENDA
        Venda novaVenda = new Venda(itemVendido.getIdentificadorCircular(), compradorUsuario.getNome(), vendedorUsuario.getNome(), ofertaGanhadora.getValorOfertado(), gwpEvitado);
        vendaRepository.addVenda(novaVenda);
        // --- LOG DE SUCESSO ---
        LogService.getInstance().getLogger().logSucesso("Conclusão da transação", itemVendido.getIdentificadorCircular(), "Venda", vendedorUsuario);

        // ATRIBUI REPUTAÇÃO (que também salva no banco através do ReputacaoService)
        ReputacaoService.getInstance().processarVendaConcluida(vendedorUsuario.getNome(), compradorUsuario.getNome());
        Duration duracao = Duration.between(ofertaGanhadora.getDataOferta(), LocalDateTime.now());
        boolean dentroDoPrazo = duracao.toHours() < 24;
        ReputacaoService.getInstance().processarRespostaOferta(perfilVendedor, dentroDoPrazo);

        // LIMPA O MERCADO
        anuncioRepository.deleteAnuncio(itemVendido.getIdentificadorCircular());
        ofertaRepository.removerOfertasPorItem(itemVendido.getIdentificadorCircular());

        view.exibirMensagem("Venda finalizada para " + compradorUsuario.getNome() + "!\n"
                + String.format("Benefício climático de %.4f kg CO₂ registrado.", gwpEvitado));
    }

    private void carregarOfertas() {
        Map<Integer, String> mapaNomesCompradores = usuarioRepository.buscarTodos().stream()
                .collect(Collectors.toMap(Usuario::getId, Usuario::getNome));

        this.minhasOfertasCache = ofertaRepository.getOfertas().stream()
                .filter(o -> o.getIdVendedor() == vendedorUsuario.getId())
                .collect(Collectors.toList());

        List<Object[]> dadosParaTabela = new ArrayList<>();
        for (Oferta oferta : this.minhasOfertasCache) {
            String nomeComprador = mapaNomesCompradores.getOrDefault(oferta.getIdComprador(), "N/A");
            dadosParaTabela.add(new Object[]{
                    oferta.getIdcItem(),
                    nomeComprador,
                    oferta.getValorOfertado(),
                    oferta.getDataOferta()
            });
        }

        view.atualizarTabela(dadosParaTabela);
    }

    private void recusarOferta() {
        Oferta ofertaSelecionada = getOfertaSelecionada();
        if (ofertaSelecionada != null) {
            ofertaRepository.removeOferta(ofertaSelecionada);
            view.exibirMensagem("Oferta recusada.");
        }
    }

    private Oferta getOfertaSelecionada() {
        int selectedRow = view.getTabelaOfertas().getSelectedRow();
        if (selectedRow < 0) {
            view.exibirMensagem("Por favor, selecione uma oferta.");
            return null;
        }
        return this.minhasOfertasCache.get(selectedRow);
    }

    @Override
    public void update(String tipoNotificacao, Object dados) {
        carregarOfertas();
    }
}