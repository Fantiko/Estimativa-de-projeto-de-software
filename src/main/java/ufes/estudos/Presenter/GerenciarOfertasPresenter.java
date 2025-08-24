package ufes.estudos.Presenter;

import ufes.estudos.Model.Item.Item; // Importante
import ufes.estudos.Model.transacao.Oferta;
import ufes.estudos.Model.Usuario.PerfilComprador; // Importante
import ufes.estudos.Model.Usuario.PerfilVendedor; // Importante
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.Model.transacao.Venda; // Importante
import ufes.estudos.Views.IGerenciarOfertasView;
import ufes.estudos.observer.Observer;
import ufes.estudos.repository.AnuncioRepository;
import ufes.estudos.repository.OfertaRepository;
import ufes.estudos.repository.PerfilRepository; // Importante
import ufes.estudos.repository.VendaRepository; // Importante

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
            return;
        }

        // 1. CALCULAR O GWP EVITADO FINAL
        // Neste momento, a estimativa se torna o valor definitivo
        double gwpEvitado = itemVendido.getGwpAvoided();

        // 2. ATUALIZAR OS PERFIS
        PerfilVendedor perfilVendedor = perfilRepository.getVendedor(ofertaGanhadora.getNomeVendedor());
        PerfilComprador perfilComprador = perfilRepository.getComprador(ofertaGanhadora.getNomeComprador());

        if(perfilVendedor != null) {
            perfilVendedor.setBeneficioClimaticoContribuido(perfilVendedor.getBeneficioClimaticoContribuido() + gwpEvitado);
        }
        if(perfilComprador != null) {
            perfilComprador.setCO2Evitado(perfilComprador.getCO2Evitado() + gwpEvitado);
            perfilComprador.setComprasFinalizadas(perfilComprador.getComprasFinalizadas() + 1);
        }

        // 3. REGISTRAR A VENDA
        Venda novaVenda = new Venda(idcItemVendido, ofertaGanhadora.getNomeComprador(), ofertaGanhadora.getNomeVendedor(), ofertaGanhadora.getValorOfertado(), gwpEvitado);
        vendaRepository.addVenda(novaVenda);

        // 4. LIMPAR O MERCADO
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