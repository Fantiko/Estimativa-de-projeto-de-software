package ufes.estudos.Model.transacao;

import java.time.LocalDateTime;

public class Venda {
    private final String idcItem;
    private final String nomeComprador;
    private final String nomeVendedor;
    private final double valorFinal;
    private final double gwpEvitado;
    private final LocalDateTime dataVenda;

    public Venda(String idcItem, String nomeComprador, String nomeVendedor, double valorFinal, double gwpEvitado) {
        this.idcItem = idcItem;
        this.nomeComprador = nomeComprador;
        this.nomeVendedor = nomeVendedor;
        this.valorFinal = valorFinal;
        this.gwpEvitado = gwpEvitado;
        this.dataVenda = LocalDateTime.now();
    }
    // Adicione getters se precisar acessar esses dados futuramente
}