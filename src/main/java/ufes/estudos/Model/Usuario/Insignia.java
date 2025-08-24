package ufes.estudos.Model.Usuario;

import java.time.LocalDate;

public class Insignia {
    private String nome;

    private String descricao;

    private int id;

    private LocalDate dataConquista;

    public Insignia(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Insignia(){
    }

    public LocalDate getDataConquista() {
        return dataConquista;
    }

    public void setDataConquista(LocalDate dataConquista) {
        this.dataConquista = dataConquista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
