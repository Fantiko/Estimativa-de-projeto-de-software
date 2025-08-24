package ufes.estudos.Model.Usuario;

import java.time.LocalDateTime;

// Essa classe é a "ficha" que representa um pedido
public class SolicitacaoPerfil {
    private final String nomeUsuario;
    private final String perfilSolicitado; // "Vendedor" ou "Comprador"
    private final LocalDateTime dataSolicitacao;

    public SolicitacaoPerfil(String nomeUsuario, String perfilSolicitado) {
        this.nomeUsuario = nomeUsuario;
        this.perfilSolicitado = perfilSolicitado;
        this.dataSolicitacao = LocalDateTime.now();
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getPerfilSolicitado() {
        return perfilSolicitado;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }
}