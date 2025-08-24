package ufes.estudos.repository;

import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import java.util.HashMap;
import java.util.Map;

public class PerfilRepository {
    private static PerfilRepository instance;
    private final Map<String, PerfilComprador> compradores;
    private final Map<String, PerfilVendedor> vendedores;

    private PerfilRepository() {
        this.compradores = new HashMap<>();
        this.vendedores = new HashMap<>();
    }

    public static PerfilRepository getInstance() {
        if (instance == null) {
            instance = new PerfilRepository();
        }
        return instance;
    }

    public void addComprador(PerfilComprador comprador) {
        compradores.put(comprador.getNome(), comprador);
    }

    public void addVendedor(PerfilVendedor vendedor) {
        vendedores.put(vendedor.getNome(), vendedor);
    }

    public PerfilComprador getComprador(String nome) {
        return compradores.get(nome);
    }

    public PerfilVendedor getVendedor(String nome) {
        return vendedores.get(nome);
    }
}