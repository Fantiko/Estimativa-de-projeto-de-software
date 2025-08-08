package ufes.estudos.Model.Item;

public class Material {
    private final String nome;
    private double fatorEmissao; //em Kg

    public Material(String nome, double fatorEmissao) {
        this.nome = nome;
        this.fatorEmissao = fatorEmissao;
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
