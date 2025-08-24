package ufes.estudos.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class PerfilComprador extends Usuario {

    
    private NivelReputacao nivelReputacao;
    private double totalEstrelas;
    

    private int comprasFinalizadas;


    private List<Insignia> insigniasPermanentes = new ArrayList<>();
    private boolean seloVerificado;
    private double CO2Evitado;
    private double estatisticaDenunciasProcedentes;

    public PerfilComprador(Usuario usuario) {
        super(usuario);
        this.nivelReputacao = NivelReputacao.bronze; // Inicializa como Bronze
        this.totalEstrelas = 0.0; // Inicializa com 0.0 estrelas
        this.comprasFinalizadas = 0;
    }

    public PerfilComprador(){

    }

    public double getTotalEstrelas() {
        return totalEstrelas;
    }

    public void setTotalEstrelas(double totalEstrelas) {
        this.totalEstrelas = totalEstrelas;
    }

    public NivelReputacao getNivelReputacao() {
        return nivelReputacao;
    }

    public void setNivelReputacao(NivelReputacao nivelReputacao) {
        this.nivelReputacao = nivelReputacao;
    }
    // --- FIM DA CORREÇÃO ---


    public int getComprasFinalizadas() {
        return comprasFinalizadas;
    }

    public void setComprasFinalizadas(int comprasFinalizadas) {
        this.comprasFinalizadas = comprasFinalizadas;
    }

    public double getCO2Evitado() {
        return CO2Evitado;
    }

    public void setCO2Evitado(double CO2Evitado) {
        this.CO2Evitado = CO2Evitado;
    }

    public List<Insignia> getInsigniasPermanentes() {
        return insigniasPermanentes;
    }

    public void setInsigniasPermanentes(List<Insignia> insigniasPermanentes) {
        this.insigniasPermanentes = insigniasPermanentes;
    }

    public double getEstatisticaDenunciasProcedentes() {
        return estatisticaDenunciasProcedentes;
    }

    public void setEstatisticaDenunciasProcedentes(double estatisticaDenunciasProcedentes) {
        this.estatisticaDenunciasProcedentes = estatisticaDenunciasProcedentes;
    }

    public boolean isVerificado() {
        return seloVerificado;
    }

    public void setSeloVerificado(boolean seloVerificado) {
        this.seloVerificado = seloVerificado;
    }

}