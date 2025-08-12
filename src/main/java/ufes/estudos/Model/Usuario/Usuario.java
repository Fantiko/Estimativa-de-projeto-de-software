package ufes.estudos.Model.Usuario;

import java.time.LocalDateTime;

public class Usuario {
    private String nomeCompleto;
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

    // CONSTRUTOR CRIADO PARA COPIAR OUTRO USUÁRIO
    public Usuario(Usuario usuario) {
        this.nomeCompleto = usuario.nomeCompleto;
        this.email = usuario.email;
        this.senha = usuario.senha;
        this.dataCriacao = usuario.dataCriacao;
        this.admin = usuario.admin;
    }

    public Usuario(String usuario, String senha, String telefone, String email, String nomeCompleto) {
        this.senha = senha;
        this.usuario = usuario;
        this.dataCriacao = LocalDateTime.now();
        this.telefone = telefone;
        this.email = email;
        this.razaoSocial = razaoSocial;
        this.nomeCompleto = nomeCompleto;
    }

    public String getNome() {
        return nomeCompleto;
    }

    public void setNome(String nomeCompleto) {
        this.nomeCompleto = this.nomeCompleto;
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

}
