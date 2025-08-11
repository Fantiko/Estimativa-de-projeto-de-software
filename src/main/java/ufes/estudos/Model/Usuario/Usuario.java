package ufes.estudos.Model.Usuario;

import java.time.LocalDateTime;
import java.util.Date;

public class Usuario {
    private String nome;
    private String razaoSocial;
    private String email;
    private String telefone;
    private LocalDateTime dataCriacao;  //automático

    private String usuario;
    private String senha;

    private boolean vendedor;
    private boolean comprador;

    private boolean admin = false;

    public Usuario(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
        this.dataCriacao = LocalDateTime.now();
    }

    public Usuario(String nome, String senha, String email, Date dataCriacao, boolean administrador) {
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

    public boolean isVendedor() {
        return vendedor;
    }

    public void setVendedor(boolean vendedor) {
        this.vendedor = vendedor;
    }

    public boolean isComprador() {
        return comprador;
    }

    public void setComprador(boolean comprador) {
        this.comprador = comprador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDataCriacao() {
        return dataCriacao.toString();
    }

}
