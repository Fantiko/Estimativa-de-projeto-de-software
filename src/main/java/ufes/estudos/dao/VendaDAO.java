package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.transacao.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    public VendaDAO(SQLiteConnectionManager connectionManager) {
    }

    public void insert(Venda venda) {
        String sql = "INSERT INTO vendas(idcItem, nomeComprador, nomeVendedor, valorFinal, gwpEvitado, dataVenda) VALUES(?,?,?,?,?,?)";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, venda.getIdcItem());
            stmt.setString(2, venda.getNomeComprador());
            stmt.setString(3, venda.getNomeVendedor());
            stmt.setDouble(4, venda.getValorFinal());
            stmt.setDouble(5, venda.getGwpEvitado());
            stmt.setString(6, venda.getDataVenda().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir venda: " + e.getMessage());
        }
    }

    public List<Venda> getAll() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM vendas";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vendas.add(new Venda(
                        rs.getString("idcItem"),
                        rs.getString("nomeComprador"),
                        rs.getString("nomeVendedor"),
                        rs.getDouble("valorFinal"),
                        rs.getDouble("gwpEvitado")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as vendas: " + e.getMessage());
        }
        return vendas;
    }
}