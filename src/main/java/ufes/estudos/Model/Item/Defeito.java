package ufes.estudos.Model.Item;

public class Defeito {
    private String defeito;
    private double percentual;
    private String descricao;

    private int id;
    public Defeito(String defeito, double percentual) {
        this.defeito = defeito;
        this.percentual = percentual;
    }

    public Defeito(){

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDefeito() {
        return defeito;
    }

    public void setDefeito(String defeito) {
        this.defeito = defeito;
    }

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        this.percentual = percentual;
    }


}
