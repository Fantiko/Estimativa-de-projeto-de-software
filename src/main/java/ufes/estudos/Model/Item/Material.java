package ufes.estudos.Model.Item;

public class Material {
    private final String nome;
    private double fatorEmissao; //em Kg

    private int id;

    public Material(String nome, double fatorEmissao) {
        this.nome = nome;
        this.fatorEmissao = fatorEmissao;
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

    public double getFatorEmissao() {
        return fatorEmissao;
    }

    public void setFatorEmissao(double fatorEmissao) {
        this.fatorEmissao = fatorEmissao;
    }
}
