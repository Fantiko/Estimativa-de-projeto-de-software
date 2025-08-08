package ufes.estudos.Model.Item;

public class Item {
    private final String identificadorCircular; // criar uma classe so para criar esse trem
    private final String tipoPeca;
    private final String subcategoria;
    private final String tamanho;
    private final String corPredominante;
    private final Material material;

    private final Defeito defeito;

    public Item(String identificadorCircular, String tipoPeca, String subcategoria, String tamanho, String corPredominante, Material material, Defeito defeito) {
        this.identificadorCircular = identificadorCircular;
        this.tipoPeca = tipoPeca;
        this.subcategoria = subcategoria;
        this.tamanho = tamanho;
        this.corPredominante = corPredominante;
        this.material = material;
        this.defeito = defeito;
    }

    public String getIdentificadorCircular() {
        return identificadorCircular;
    }

    public String getTipoPeca() {
        return tipoPeca;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public String getTamanho() {
        return tamanho;
    }

    public String getCorPredominante() {
        return corPredominante;
    }

    public Material getMaterial() {
        return material;
    }

    public String getEstadoConservacao() {
        String estadoConservacao = "Usado";
        return estadoConservacao;
    }

    public Defeito getDefeito() {
        return defeito;
    }
}
