package ufes.estudos.Model.Usuario;

public class Insignia {
    private String nome;

    private String descricao;

    private int id;

    public Insignia(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Insignia(){
        // Construtor padrão necessário para algumas operações de persistência
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
