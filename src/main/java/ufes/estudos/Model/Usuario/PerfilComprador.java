package ufes.estudos.Model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class PerfilComprador extends Usuario {

    private int nivelReputacao;
    private int totalEstrelas;

    private int comprasFinalizadas;

    private List<String> insigniasPermanentes = new ArrayList<String>();

    private boolean seloVerificado;

    private double CO2Evitado;
    private double estatisticaDenunciasProcedentes;


    public PerfilComprador(Usuario usuario) {
        super(usuario);
        this.totalEstrelas = 0;
        this.nivelReputacao = 0;
        this.comprasFinalizadas = 0;
    }

    public int getTotalEstrelas() {
        return totalEstrelas;
    }

    public void setTotalEstrelas(int totalEstrelas) {
        this.totalEstrelas = totalEstrelas;
    }

    public int getNivel() {
        return nivelReputacao;
    }

    public void setNivel(int nivel) {
        this.nivelReputacao = nivel;
    }

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

    public List<String> getInsigniasPermanentes() {
        return insigniasPermanentes;
    }

    public void setInsigniasPermanentes(List<String> insigniasPermanentes) {
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


