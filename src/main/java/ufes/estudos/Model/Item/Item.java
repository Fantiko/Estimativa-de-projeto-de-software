package ufes.estudos.Model.Item;

public class Item {
    private final String identificadorCircular;
    private final String tipoPeca;
    private final String subcategoria;
    private final String tamanho;
    private final String corPredominante;
    private final Material material;
    private final Defeito defeito;
    private final String estadoConservacao;
    private final double massaEstimada;
    private final double precoBase;
    private final String nomeVendedor;
    private final double gwpBase;       // CAMPO ADICIONADO
    private final double gwpAvoided;

    public Item(String identificadorCircular, String tipoPeca, String subcategoria, String tamanho,
                String corPredominante, Material material, Defeito defeito, String estadoConservacao,
                double massaEstimada, double precoBase, String nomeVendedor, double gwpBase, double gwpAvoided) { // PARÂMETRO ADICIONADO
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
        this.nomeVendedor = nomeVendedor; // ATRIBUIÇÃO ADICIONADA
        this.gwpBase = gwpBase;           // ATRIBUIÇÃO ADICIONADA
        this.gwpAvoided = gwpAvoided;
    }

    // Getters para todos os campos
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
    public String getNomeVendedor() { return nomeVendedor; }
    public double getGwpBase() { return gwpBase; }         // GETTER ADICIONADO
    public double getGwpAvoided() { return gwpAvoided; }   // GETTER ADICIONADO
}