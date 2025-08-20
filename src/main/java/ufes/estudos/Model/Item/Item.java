package ufes.estudos.Model.Item;

public class Item {
    private final String identificadorCircular;
    private final String tipoPeca;
    private final String subcategoria;
    private final String tamanho;
    private final String corPredominante;
    private final Material material;
    private final Defeito defeito;
    private final String estadoConservacao; // Novo
    private final double massaEstimada;     // Novo
    private final double precoBase;         // Novo

    public Item(String identificadorCircular, String tipoPeca, String subcategoria, String tamanho,
                String corPredominante, Material material, Defeito defeito, String estadoConservacao,
                double massaEstimada, double precoBase) {
        this.identificadorCircular = identificadorCircular;
        this.tipoPeca = tipoPeca;
        this.subcategoria = subcategoria;
        this.tamanho = tamanho;
        this.corPredominante = corPredominante;
        this.material = material;
        this.defeito = defeito;
        this.estadoConservacao = estadoConservacao;
        this.massaEstimada = massaEstimada;
        this.precoBase = precoBase;
    }

    // Getters para todos os campos (incluindo os novos)
    public String getIdentificadorCircular() { return identificadorCircular; }
    public String getTipoPeca() { return tipoPeca; }
    public String getSubcategoria() { return subcategoria; }
    public String getTamanho() { return tamanho; }
    public String getCorPredominante() { return corPredominante; }
    public Material getMaterial() { return material; }
    public Defeito getDefeito() { return defeito; }
    public String getEstadoConservacao() { return estadoConservacao; }
    public double getMassaEstimada() { return massaEstimada; }
    public double getPrecoBase() { return precoBase; }
}