package ufes.estudos.Model.Usuario;

import java.util.Date;

public class Usuario {
    private String nome;
    private String senha;

    private String email;

    private Date dataCriacao;

    private boolean administrador = false;

    public Usuario(String nome, String senha, String email, Date dataCriacao, boolean administrador) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.administrador = administrador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
