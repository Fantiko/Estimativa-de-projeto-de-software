package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.PerfilComprador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PerfilCompradorDAO {

    public PerfilCompradorDAO(SQLiteConnectionManager connectionManager) {
    }

    public void update(PerfilComprador perfil) {
        String sql = "UPDATE perfilComprador SET nivelReputacao = ?, totalEstrelas = ?, " +
                "comprasFinalizadas = ?, co2Evitado = ? WHERE usuarioId = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, perfil.getNivelReputacao().name());
            stmt.setDouble(2, perfil.getTotalEstrelas());
            stmt.setInt(3, perfil.getComprasFinalizadas());
            stmt.setDouble(4, perfil.getCO2Evitado());
            stmt.setInt(5, perfil.getId()); // ID do usuário
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar perfil de comprador: " + e.getMessage());
        }
    }
    // Outros métodos do DAO (insert, get, etc.)
}