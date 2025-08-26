package ufes.estudos.Presenter;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Defeito;
import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Item.Material;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Model.eventos.EventoTimeline;
import ufes.estudos.Model.eventos.TipoEvento;
import ufes.estudos.Views.IAdicionarAnuncioView;
import ufes.estudos.repository.RepositoriesIntefaces.AnuncioRepository;
import ufes.estudos.repository.PerfilRepository;
import ufes.estudos.repository.RepositoriesIntefaces.DefeitoRepository;
import ufes.estudos.repository.RepositoriesIntefaces.MaterialRepository;
import ufes.estudos.repository.RepositoriesSQLite.AnuncioSQLiteRepository;
import ufes.estudos.repository.RepositoriesSQLite.DefeitoSQLiteRepository;
import ufes.estudos.repository.RepositoriesSQLite.MaterialSQLiteRepository;
import ufes.estudos.repository.TimelineRepository;
import ufes.estudos.service.IdService;
import ufes.estudos.service.ReputacaoService;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Stream;

public class AdicionarAnuncioPresenter {
    private final IAdicionarAnuncioView view;
    private final AnuncioRepository anuncioRepository;
    private final Usuario usuario;
    private final IdService idService;

    // Repositórios para buscar dados do banco
    private final MaterialRepository materialRepository;
    private final DefeitoRepository defeitoRepository;

    // Listas para guardar os dados carregados do banco
    private List<Material> materiaisDoBanco;
    private List<Defeito> defeitosDoBanco;

    public AdicionarAnuncioPresenter(IAdicionarAnuncioView view, Usuario usuario) {
        this.view = view;
        this.usuario = usuario;
        this.idService = new IdService();
        this.anuncioRepository = new AnuncioSQLiteRepository(new SQLiteConnectionManager());

        // Inicializa os repositórios
        SQLiteConnectionManager connectionManager = new SQLiteConnectionManager();
        this.materialRepository = new MaterialSQLiteRepository(connectionManager);
        this.defeitoRepository = new DefeitoSQLiteRepository(connectionManager);

        // Carrega as opções dos menus a partir do banco
        carregarOpcoesDoBanco();

        this.view.setSalvarListener(this::salvarAnuncio);
        this.view.setCancelarListener(e -> this.view.fechar());
    }

    private void carregarOpcoesDoBanco() {
        // Carrega Materiais
        this.materiaisDoBanco = materialRepository.buscarTodos();
        String[] nomesMateriais = Stream.concat(Stream.of(""), this.materiaisDoBanco.stream().map(Material::getNome))
                .toArray(String[]::new);
        view.setComposicaoOptions(nomesMateriais);

        // Carrega Defeitos
        this.defeitosDoBanco = defeitoRepository.buscarTodos();
        String[] nomesDefeitos = Stream.concat(Stream.of(""), this.defeitosDoBanco.stream().map(Defeito::getDefeito))
                .toArray(String[]::new);
        view.setDefeitosOptions(nomesDefeitos);
    }

    private void salvarAnuncio(ActionEvent e) {
        if (view.getTipoPeca().isEmpty() || view.getSubcategoria().isBlank() || view.getTamanho().isBlank() ||
                view.getCor().isBlank() || view.getComposicao().isEmpty() || view.getMassa().isBlank() ||
                view.getEstado().isEmpty() || view.getDefeito().isEmpty() || view.getPreco().isBlank()) {
            view.exibirMensagem("Todos os campos, exceto ID-C, são obrigatórios.");
            return;
        }

        try {
            double massa = Double.parseDouble(view.getMassa().replace(',', '.'));
            double preco = Double.parseDouble(view.getPreco().replace(',', '.'));
            String nomeMaterialSelecionado = view.getComposicao();
            String nomeDefeitoSelecionado = view.getDefeito();

            Material materialSelecionado = this.materiaisDoBanco.stream()
                    .filter(m -> m.getNome().equals(nomeMaterialSelecionado)).findFirst().orElseThrow();
            Defeito defeitoSelecionado = this.defeitosDoBanco.stream()
                    .filter(d -> d.getDefeito().equals(nomeDefeitoSelecionado)).findFirst().orElse(new Defeito("", 0.0));

            double ef = materialSelecionado.getFatorEmissao();
            double gwpBase = massa * ef;
            double gwpAvoided = gwpBase * 0.95;
            double percentualDefeito = defeitoSelecionado.getPercentual();

            String idc = view.getIdc();
            if (idc.isBlank()) {
                // --- CORREÇÃO AQUI ---
                // O loop continua ENQUANTO o item for encontrado (ou seja, o ID não for único)
                do {
                    idc = idService.generate();
                } while (anuncioRepository.findByIdc(idc).isPresent()); // Checa se o objeto é nulo ou não
            }

            Item novoItem = new Item(
                    idc, view.getTipoPeca(), view.getSubcategoria(), view.getTamanho(),
                    view.getCor(), materialSelecionado, defeitoSelecionado, view.getEstado(), massa, preco,
                    usuario.getId(), gwpBase, gwpAvoided
            );

            anuncioRepository.addAnuncio(novoItem);

            double mci = 1 - defeitoSelecionado.getPercentual();
            String detalhes = "Item publicado pelo vendedor " + usuario.getNome() + ". Ciclo: " + novoItem.getCiclo();
            TimelineRepository.getInstance().addEvento(new EventoTimeline(novoItem.getIdentificadorCircular(), TipoEvento.PUBLICADO, novoItem.getGwpAvoided(), mci, detalhes));
            ReputacaoService.getInstance().processarCadastroItemCompleto(PerfilRepository.getInstance().getVendedor(usuario.getNome()));

            view.exibirMensagem("Anúncio salvo com sucesso!\nID-C: " + novoItem.getIdentificadorCircular());
            view.fechar();

        } catch (Exception ex) {
            view.exibirMensagem("Erro ao salvar o anúncio. Verifique os dados inseridos.");
            ex.printStackTrace();
        }
    }

}