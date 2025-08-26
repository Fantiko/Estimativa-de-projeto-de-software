package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.Insignia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsigniasDAO {

    public InsigniasDAO(SQLiteConnectionManager connectionManager) {
    }

    public void insert(String nome, String descricao) {
        String sql = "INSERT INTO insignias(nome, descricao) VALUES(?, ?)";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir insignia: " + e.getMessage());
        }
    }

    public List<Insignia> getAll() {
        List<Insignia> insignias = new ArrayList<>();
        String sql = "SELECT id, nome, descricao FROM insignias";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Insignia insignia = new Insignia(rs.getString("nome"), rs.getString("descricao"));
                insignia.setId(rs.getInt("id"));
                insignias.add(insignia);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as insignias: " + e.getMessage());
        }
        return insignias;
    }
}