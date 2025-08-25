package ufes.estudos.Model.Item;

public class Item {
    private final String identificadorCircular;
    private String tipoPeca;
    private String subcategoria;
    private String tamanho;
    private String corPredominante;
    private Material material;
    private Defeito defeito;
    private String estadoConservacao;
    private double massaEstimada;
    private double precoBase;
    private int idVendedor; //mudou
    private double gwpBase;       // CAMPO ADICIONADO
    private double gwpAvoided;
    private int ciclo;

    public Item(String identificadorCircular, String tipoPeca, String subcategoria, String tamanho,
                String corPredominante, Material material, Defeito defeito, String estadoConservacao,
                double massaEstimada, double precoBase, int idVendedor, double gwpBase, double gwpAvoided) { // PARÂMETRO ADICIONADO
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
        this.idVendedor = idVendedor; // ATRIBUIÇÃO ADICIONADA
        this.gwpBase = gwpBase;           // ATRIBUIÇÃO ADICIONADA
        this.gwpAvoided = gwpAvoided;
        this.ciclo = 1;
    }

    public Item(String identificadorCircular){

        this.identificadorCircular = identificadorCircular;
    }

    public void setTipoPeca(String tipoPeca) {
        this.tipoPeca = tipoPeca;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public void setCorPredominante(String corPredominante) {
        this.corPredominante = corPredominante;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setDefeito(Defeito defeito) {
        this.defeito = defeito;
    }

    public void setEstadoConservacao(String estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public void setMassaEstimada(double massaEstimada) {
        this.massaEstimada = massaEstimada;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public void setGwpBase(double gwpBase) {
        this.gwpBase = gwpBase;
    }

    public void setGwpAvoided(double gwpAvoided) {
        this.gwpAvoided = gwpAvoided;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
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
    public int getIdVendedor() { return idVendedor; }
    public double getGwpBase() { return gwpBase; }         // GETTER ADICIONADO
    public double getGwpAvoided() { return gwpAvoided; }   // GETTER ADICIONADO
    public int getCiclo() { return ciclo; }
    public void incrementarCiclo() { this.ciclo++; }
}