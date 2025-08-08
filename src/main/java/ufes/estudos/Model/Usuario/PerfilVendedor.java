package ufes.estudos.Model.Usuario;

import ufes.estudos.Model.Item.Item;

import java.util.Date;
import java.util.List;

public class PerfilVendedor extends Usuario{
    private NivelReputacao nivelReputacao;
    private int totalEstrelas;

    private int contagemVendasConcluidas;
    private int numeroDenunciasRecebidas;

    private List<Item> catalogo;

    private double beneficioClimaticoContribuido;

    /*TODO:
    * Lista de insignias
    * Selos visuais de temporada
    * taxonomia de curadoria associadas
    * */

    public PerfilVendedor(String nome, String senha, String email, Date dataCriacao, boolean administrador, NivelReputacao nivelReputacao, int totalEstrelas, int contagemVendasConcluidas, int numeroDenunciasRecebidas) {
        super(nome, senha, email, dataCriacao, administrador);
        this.nivelReputacao = nivelReputacao;
        this.totalEstrelas = totalEstrelas;
        this.contagemVendasConcluidas = contagemVendasConcluidas;
        this.numeroDenunciasRecebidas = numeroDenunciasRecebidas;
    }

    public void adicionarCatalogo(){}
    public void removerCatalogo(){}

    public NivelReputacao getNivelReputacao() {
        return nivelReputacao;
    }

    public void setNivelReputacao(NivelReputacao nivelReputacao) {
        this.nivelReputacao = nivelReputacao;
    }

    public int getTotalEstrelas() {
        return totalEstrelas;
    }

    public void setTotalEstrelas(int totalEstrelas) {
        this.totalEstrelas = totalEstrelas;
    }

    public int getContagemVendasConcluidas() {
        return contagemVendasConcluidas;
    }

    public void setContagemVendasConcluidas(int contagemVendasConcluidas) {
        this.contagemVendasConcluidas = contagemVendasConcluidas;
    }

    public int getNumeroDenunciasRecebidas() {
        return numeroDenunciasRecebidas;
    }

    public void setNumeroDenunciasRecebidas(int numeroDenunciasRecebidas) {
        this.numeroDenunciasRecebidas = numeroDenunciasRecebidas;
    }

    public List<Item> getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(List<Item> catalogo) {
        this.catalogo = catalogo;
    }

    public double getBeneficioClimaticoContribuido() {
        return beneficioClimaticoContribuido;
    }

    public void setBeneficioClimaticoContribuido(double beneficioClimaticoContribuido) {
        this.beneficioClimaticoContribuido = beneficioClimaticoContribuido;
    }
}
