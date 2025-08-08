package ufes.estudos.Model.Item;

public class Defeito {
    private String defeito;
    private double percentual; // 0.3

    public Defeito(String defeito, double percentual) {
        this.defeito = defeito;
        this.percentual = percentual;
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
