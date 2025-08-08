package ufes.estudos.Model.Usuario;

import java.util.Date;

public class PerfilComprador extends Usuario {
    private int estrelasReputacao;
    private int nivel;

    private int comprasFinalizadas;

    private double CO2Evitado;
    private double estatisticasDenuncias;


    /*TODO:
    * Insigneas permanentes relativas a ofertas e compras
    * selo verificador confiavel -> boolean????
    * */

    public PerfilComprador(String nome, String senha, String email, Date dataCriacao, boolean administrador, int estrelasReputacao, int nivel, int comprasFinalizadas) {
        super(nome, senha, email, dataCriacao, administrador);
        this.estrelasReputacao = estrelasReputacao;
        this.nivel = nivel;
        this.comprasFinalizadas = comprasFinalizadas;
    }

    public int getEstrelasReputacao() {
        return estrelasReputacao;
    }

    public void setEstrelasReputacao(int estrelasReputacao) {
        this.estrelasReputacao = estrelasReputacao;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
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

    public double getEstatisticasDenuncias() {
        return estatisticasDenuncias;
    }

    public void setEstatisticasDenuncias(double estatisticasDenuncias) {
        this.estatisticasDenuncias = estatisticasDenuncias;
    }
}
