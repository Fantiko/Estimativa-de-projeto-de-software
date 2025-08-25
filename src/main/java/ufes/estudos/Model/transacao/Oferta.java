package ufes.estudos.Model.transacao;

import java.time.LocalDateTime;

public class Oferta {
    private final String idcItem;
    private final int idComprador;
    private final int idVendedor;
    private final double valorOfertado;
    private final LocalDateTime dataOferta;

    public Oferta(String idcItem, int idComprador, int idVendedor, double valorOfertado) {
        this.idcItem = idcItem;
        this.idComprador = idComprador;
        this.idVendedor = idVendedor;
        this.valorOfertado = valorOfertado;
        this.dataOferta = LocalDateTime.now();
    }

    public String getIdcItem() { return idcItem; }
    public int getIdComprador() { return idComprador; }
    public int getIdVendedor() { return idVendedor; }
    public double getValorOfertado() { return valorOfertado; }
    public LocalDateTime getDataOferta() { return dataOferta; }
}