package ufes.estudos.Model.transacao;

import java.time.LocalDateTime;

public class Oferta {
    private final String idcItem;
    private final String nomeComprador;
    private final String nomeVendedor;
    private final double valorOfertado;
    private final LocalDateTime dataOferta;

    public Oferta(String idcItem, String nomeComprador, String nomeVendedor, double valorOfertado) {
        this.idcItem = idcItem;
        this.nomeComprador = nomeComprador;
        this.nomeVendedor = nomeVendedor;
        this.valorOfertado = valorOfertado;
        this.dataOferta = LocalDateTime.now();
    }

    public String getIdcItem() { return idcItem; }
    public String getNomeComprador() { return nomeComprador; }
    public String getNomeVendedor() { return nomeVendedor; }
    public double getValorOfertado() { return valorOfertado; }
    public LocalDateTime getDataOferta() { return dataOferta; }
}