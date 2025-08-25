package ufes.estudos.service;

import ufes.estudos.Model.Usuario.NivelReputacao;
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.repository.PerfilRepository;

public class ReputacaoService {
    private static ReputacaoService instance;

    private ReputacaoService() {}

    public static ReputacaoService getInstance() {
        if (instance == null) {
            instance = new ReputacaoService();
        }
        return instance;
    }
    public void processarCadastroItemCompleto(PerfilVendedor vendedor) {
        System.out.println("Processando +0.05 estrela para vendedor: " + vendedor.getNome());
        adicionarEstrelasVendedor(vendedor, 0.05);
    }

    public void processarOfertaEnviada(PerfilComprador comprador) {
        System.out.println("Processando +0.05 estrela para comprador: " + comprador.getNome());
        adicionarEstrelasComprador(comprador, 0.05);
    }

    public void processarRespostaOferta(PerfilVendedor vendedor, boolean dentroDoPrazo) {
        if (dentroDoPrazo) {
            System.out.println("Processando +0.05 estrela para vendedor por resposta rápida: " + vendedor.getNome());
            adicionarEstrelasVendedor(vendedor, 0.05);
        }
    }

    public void processarVendaConcluida(String nomeVendedor, String nomeComprador) {
        PerfilRepository perfilRepository = PerfilRepository.getInstance();
        PerfilVendedor vendedor = perfilRepository.getVendedor(nomeVendedor);
        PerfilComprador comprador = perfilRepository.getComprador(nomeComprador);

        if (vendedor != null) {
            System.out.println("Processando +0.5 estrela para vendedor por venda: " + vendedor.getNome());
            adicionarEstrelasVendedor(vendedor, 0.5);
        }
        if (comprador != null) {
            System.out.println("Processando +0.5 estrela para comprador por compra: " + comprador.getNome());
            adicionarEstrelasComprador(comprador, 0.5);
        }
    }

    // --- LÓGICA CENTRAL DE PONTUAÇÃO ---
    private void adicionarEstrelasVendedor(PerfilVendedor vendedor, double quantidade) {
        if (vendedor.getNivelReputacao() == NivelReputacao.ouro && vendedor.getTotalEstrelas() >= 5.0) {
            return; // Já está no máximo
        }
        vendedor.setTotalEstrelas(vendedor.getTotalEstrelas() + quantidade);
        promoverVendedorSeNecessario(vendedor);
    }

    private void adicionarEstrelasComprador(PerfilComprador comprador, double quantidade) {
        if (comprador.getNivelReputacao() == NivelReputacao.ouro && comprador.getTotalEstrelas() >= 5.0) {
            return; // Já está no máximo
        }
        comprador.setTotalEstrelas(comprador.getTotalEstrelas() + quantidade);
        promoverCompradorSeNecessario(comprador);
    }

    // --- LÓGICA DE EVOLUÇÃO DE NÍVEL ---
    private void promoverVendedorSeNecessario(PerfilVendedor vendedor) {
        if (vendedor.getTotalEstrelas() >= 5.0) {
            if (vendedor.getNivelReputacao() == NivelReputacao.bronze) {
                vendedor.setNivelReputacao(NivelReputacao.prata);
                vendedor.setTotalEstrelas(vendedor.getTotalEstrelas() - 5.0); // Zera as estrelas do nível
            } else if (vendedor.getNivelReputacao() == NivelReputacao.prata) {
                vendedor.setNivelReputacao(NivelReputacao.ouro);
                vendedor.setTotalEstrelas(vendedor.getTotalEstrelas() - 5.0);
            }
        }
    }

    private void promoverCompradorSeNecessario(PerfilComprador comprador) {
        if (comprador.getTotalEstrelas() >= 5.0) {
            if (comprador.getNivelReputacao() == NivelReputacao.bronze) {
                comprador.setNivelReputacao(NivelReputacao.prata);
                comprador.setTotalEstrelas(comprador.getTotalEstrelas() - 5.0);
            } else if (comprador.getNivelReputacao() == NivelReputacao.prata) {
                comprador.setNivelReputacao(NivelReputacao.ouro);
                comprador.setTotalEstrelas(comprador.getTotalEstrelas() - 5.0);
            }
        }
    }
}