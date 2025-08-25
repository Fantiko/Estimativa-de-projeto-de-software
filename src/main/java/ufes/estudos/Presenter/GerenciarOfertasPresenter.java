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
import ufes.estudos.repository.AnuncioRepository;
import ufes.estudos.repository.OfertaRepository;
import ufes.estudos.repository.PerfilRepository;
import ufes.estudos.repository.RepositoriesIntefaces.UsuarioRepository;
import ufes.estudos.repository.RepositoriesSQLite.UsuarioSQLiteRepository;
import ufes.estudos.repository.VendaRepository;
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
    private final PerfilRepository perfilRepository; // Usado para a sessão atual
    private final VendaRepository vendaRepository;

    private List<Oferta> minhasOfertasCache; // Cache para a seleção

    public GerenciarOfertasPresenter(IGerenciarOfertasView view, Usuario vendedor) {
        this.view = view;
        this.vendedorUsuario = vendedor;
        this.ofertaRepository = OfertaRepository.getInstance();
        this.anuncioRepository = AnuncioRepository.getInstance();
        this.perfilRepository = PerfilRepository.getInstance();
        this.vendaRepository = VendaRepository.getInstance();
        this.usuarioRepository = new UsuarioSQLiteRepository(new SQLiteConnectionManager());

        this.ofertaRepository.addObserver(this);

        this.view.setAceitarListener(e -> aceitarOferta());
        this.view.setRecusarListener(e -> recusarOferta());

        carregarOfertas();
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

    // --- MÉTODO COMPLETO E CORRIGIDO ---
    private void aceitarOferta() {
        Oferta ofertaGanhadora = getOfertaSelecionada();
        if (ofertaGanhadora == null) return;

        String idcItemVendido = ofertaGanhadora.getIdcItem();
        Item itemVendido = anuncioRepository.findByIdc(idcItemVendido);

        if (itemVendido == null) {
            view.exibirMensagem("Erro: O item do anúncio não foi encontrado. A oferta será removida.");
            ofertaRepository.removerOfertasPorItem(idcItemVendido); // Limpa ofertas órfãs
            return;
        }

        // 1. OBTÉM OS PERFIS E O NOME DO COMPRADOR
        PerfilVendedor perfilVendedor = perfilRepository.getVendedor(vendedorUsuario.getNome());
        Usuario compradorUsuario = usuarioRepository.buscarPorId(ofertaGanhadora.getIdComprador()).orElse(null);
        if(compradorUsuario == null){
            view.exibirMensagem("Erro: Comprador não encontrado no sistema.");
            return;
        }
        PerfilComprador perfilComprador = perfilRepository.getComprador(compradorUsuario.getNome());

        // 2. REGISTRA A VENDA
        double gwpEvitado = itemVendido.getGwpAvoided();
        Venda novaVenda = new Venda(idcItemVendido, compradorUsuario.getNome(), vendedorUsuario.getNome(), ofertaGanhadora.getValorOfertado(), gwpEvitado);
        vendaRepository.addVenda(novaVenda);

        // 3. ATUALIZA OS INDICADORES
        if(perfilVendedor != null) {
            perfilVendedor.setBeneficioClimaticoContribuido(perfilVendedor.getBeneficioClimaticoContribuido() + gwpEvitado);
        }
        if(perfilComprador != null) {
            perfilComprador.setCO2Evitado(perfilComprador.getCO2Evitado() + gwpEvitado);
            perfilComprador.setComprasFinalizadas(perfilComprador.getComprasFinalizadas() + 1);
        }

        // 4. ATRIBUI REPUTAÇÃO
        ReputacaoService.getInstance().processarVendaConcluida(vendedorUsuario.getNome(), compradorUsuario.getNome());
        Duration duracao = Duration.between(ofertaGanhadora.getDataOferta(), LocalDateTime.now());
        boolean dentroDoPrazo = duracao.toHours() < 24;
        ReputacaoService.getInstance().processarRespostaOferta(perfilVendedor, dentroDoPrazo);

        // 5. LIMPA O MERCADO
        anuncioRepository.deleteAnuncio(idcItemVendido);
        ofertaRepository.removerOfertasPorItem(idcItemVendido);

        // 6. DÁ O FEEDBACK FINAL
        view.exibirMensagem("Venda finalizada para " + compradorUsuario.getNome() + "!\n"
                + String.format("Benefício climático de %.4f kg CO₂ registrado.", gwpEvitado));
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