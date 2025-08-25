package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {
    private final SQLiteConnectionManager connectionManager;

    public MaterialDAO(SQLiteConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void insert(Material material) {
        String sql = "INSERT INTO materiais (nome, fatorEmissao) VALUES (?, ?)";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, material.getNome());
            stmt.setDouble(2, material.getFatorEmissao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir material: " + e.getMessage());
        }
    }

    public Material getById(int id) {
        String sql = "SELECT * FROM materiais WHERE id = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Material(rs.getString("nome"), rs.getDouble("fatorEmissao"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar material por ID: " + e.getMessage());
        }
        return null;
    }

    public Material getByName(String nome) {
        String sql = "SELECT * FROM materiais WHERE nome = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Material(rs.getString("nome"), rs.getDouble("fatorEmissao"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar material por nome: " + e.getMessage());
        }
        return null;
    }

    public List<Material> getAll() {
        String sql = "SELECT nome, fatorEmissao FROM materiais";
        List<Material> materiais = new ArrayList<>();
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                materiais.add(new Material(rs.getString("nome"), rs.getDouble("fatorEmissao")));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os materiais: " + e.getMessage());
        }
        return materiais;
    }
}