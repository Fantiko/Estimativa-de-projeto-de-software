package ufes.estudos.Model.Usuario;

import ufes.estudos.Model.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class PerfilVendedor extends Usuario{
    private NivelReputacao nivelReputacao;
    private int totalEstrelas;
    private int vendasConcluidas;
    private int denunciasRecebidas;

    private List<Insignia> insignias = new ArrayList<>();

    private double beneficioClimaticoContribuido;

    private List<Item> catalogo;

    private String taxCuradoriaAssociada;// TODO FAZER ESSE TRECO AQUI


    public PerfilVendedor(Usuario usuario) {
        super(usuario);
        this.nivelReputacao = NivelReputacao.bronze;
        this.totalEstrelas = 0;
        this.vendasConcluidas = 0;
        this.denunciasRecebidas = 0;
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

    public int getVendasConcluidas() {
        return vendasConcluidas;
    }

    public void setVendasConcluidas(int vendasConcluidas) {
        this.vendasConcluidas = vendasConcluidas;
    }

    public int getDenunciasRecebidas() {
        return denunciasRecebidas;
    }

    public void setDenunciasRecebidas(int numeroDenunciasRecebidas) {
        this.denunciasRecebidas = denunciasRecebidas;
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

    public List<Insignia> getInsignias() {
        return insignias;
    }

    public void setInsignias(List<Insignia> insignias) {
        this.insignias = insignias;
    }

}
