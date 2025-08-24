package ufes.estudos.Presenter;

import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.transacao.Oferta; // Mantive seu pacote 'transacao'
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Model.transacao.Venda; // Mantive seu pacote 'transacao'
import ufes.estudos.Views.IGerenciarOfertasView;
import ufes.estudos.observer.Observer;
import ufes.estudos.repository.AnuncioRepository;
import ufes.estudos.repository.OfertaRepository;
import ufes.estudos.repository.PerfilRepository;
import ufes.estudos.repository.VendaRepository;
import ufes.estudos.service.ReputacaoService;

import java.time.Duration; // <<< IMPORT ADICIONADO
import java.time.LocalDateTime; // <<< IMPORT ADICIONADO
import java.util.stream.Collectors;
import java.util.List;

public class GerenciarOfertasPresenter implements Observer {
    private final IGerenciarOfertasView view;
    private final Usuario vendedorUsuario;
    private final OfertaRepository ofertaRepository;
    private final AnuncioRepository anuncioRepository;
    private final PerfilRepository perfilRepository;
    private final VendaRepository vendaRepository;


    public GerenciarOfertasPresenter(IGerenciarOfertasView view, Usuario vendedor) {
        this.view = view;
        this.vendedorUsuario = vendedor;
        this.ofertaRepository = OfertaRepository.getInstance();
        this.anuncioRepository = AnuncioRepository.getInstance();
        this.perfilRepository = PerfilRepository.getInstance();
        this.vendaRepository = VendaRepository.getInstance();

        this.ofertaRepository.addObserver(this);

        this.view.setAceitarListener(e -> aceitarOferta());
        this.view.setRecusarListener(e -> recusarOferta());

        carregarOfertas();
    }

    private void carregarOfertas() {
        List<Oferta> todasOfertas = ofertaRepository.getOfertas();
        List<Oferta> minhasOfertas = todasOfertas.stream()
                .filter(o -> o.getNomeVendedor().equals(vendedorUsuario.getNome()))
                .collect(Collectors.toList());
        view.atualizarTabela(minhasOfertas);
    }

    private void recusarOferta() {
        int selectedRow = view.getTabelaOfertas().getSelectedRow();
        if (selectedRow < 0) {
            view.exibirMensagem("Selecione uma oferta para recusar.");
            return;
        }
        Oferta ofertaSelecionada = getOfertaSelecionada(selectedRow);
        if (ofertaSelecionada != null) {
            // A lógica de pontuação por resposta rápida também se aplica à recusa
            Duration duracao = Duration.between(ofertaSelecionada.getDataOferta(), LocalDateTime.now());
            boolean dentroDoPrazo = duracao.toHours() < 24;
            ReputacaoService.getInstance().processarRespostaOferta(perfilRepository.getVendedor(vendedorUsuario.getNome()), dentroDoPrazo);

            ofertaRepository.removeOferta(ofertaSelecionada);
            view.exibirMensagem("Oferta recusada.");
        }
    }

    private void aceitarOferta() {
        int selectedRow = view.getTabelaOfertas().getSelectedRow();
        if (selectedRow < 0) {
            view.exibirMensagem("Selecione uma oferta para aceitar.");
            return;
        }
        Oferta ofertaGanhadora = getOfertaSelecionada(selectedRow);
        if (ofertaGanhadora == null) return;

        String idcItemVendido = ofertaGanhadora.getIdcItem();
        Item itemVendido = anuncioRepository.findByIdc(idcItemVendido);
        if (itemVendido == null) {
            view.exibirMensagem("Erro: O item do anúncio não foi encontrado.");
            ofertaRepository.removerOfertasPorItem(idcItemVendido); // Limpa ofertas órfãs
            return;
        }

        double gwpEvitado = itemVendido.getGwpAvoided();
        PerfilVendedor perfilVendedor = perfilRepository.getVendedor(ofertaGanhadora.getNomeVendedor());
        PerfilComprador perfilComprador = perfilRepository.getComprador(ofertaGanhadora.getNomeComprador());

        if(perfilVendedor != null) {
            perfilVendedor.setBeneficioClimaticoContribuido(perfilVendedor.getBeneficioClimaticoContribuido() + gwpEvitado);
        }
        if(perfilComprador != null) {
            perfilComprador.setCO2Evitado(perfilComprador.getCO2Evitado() + gwpEvitado);
            perfilComprador.setComprasFinalizadas(perfilComprador.getComprasFinalizadas() + 1);
        }

        Venda novaVenda = new Venda(idcItemVendido, ofertaGanhadora.getNomeComprador(), ofertaGanhadora.getNomeVendedor(), ofertaGanhadora.getValorOfertado(), gwpEvitado);
        vendaRepository.addVenda(novaVenda);

        // Atribui pontos pela venda concluída
        ReputacaoService.getInstance().processarVendaConcluida(ofertaGanhadora.getNomeVendedor(), ofertaGanhadora.getNomeComprador());

        // Atribui pontos pela resposta rápida
        Duration duracao = Duration.between(ofertaGanhadora.getDataOferta(), LocalDateTime.now());
        boolean dentroDoPrazo = duracao.toHours() < 24;
        ReputacaoService.getInstance().processarRespostaOferta(perfilVendedor, dentroDoPrazo);

        anuncioRepository.deleteAnuncio(idcItemVendido);
        ofertaRepository.removerOfertasPorItem(idcItemVendido);

        view.exibirMensagem("Venda finalizada para " + ofertaGanhadora.getNomeComprador() + "!\n"
                + String.format("Benefício climático de %.4f kg CO₂ registrado.", gwpEvitado));
    }

    private Oferta getOfertaSelecionada(int selectedRow) {
        String idc = (String) view.getTabelaOfertas().getValueAt(selectedRow, 0);
        String comprador = (String) view.getTabelaOfertas().getValueAt(selectedRow, 1);

        return ofertaRepository.getOfertas().stream()
                .filter(o -> o.getIdcItem().equals(idc) && o.getNomeComprador().equals(comprador))
                .findFirst().orElse(null);
    }

    @Override
    public void update(String tipoNotificacao, Object dados) {
        carregarOfertas();
    }
}