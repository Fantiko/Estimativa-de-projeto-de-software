package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PerfilVendedorDAO {

    public PerfilVendedorDAO(SQLiteConnectionManager connectionManager) {
    }

    // O CÓDIGO COM O SQL VAI AQUI
    public void update(PerfilVendedor perfil) {
        String sql = "UPDATE perfilVendedor SET nivelReputacao = ?, totalEstrelas = ?, " +
                "vendasConcluidas = ?, beneficioClimaticoContribuido = ? WHERE usuario_id = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, perfil.getNivelReputacao().name());
            stmt.setDouble(2, perfil.getTotalEstrelas());
            stmt.setInt(3, perfil.getVendasConcluidas());
            stmt.setDouble(4, perfil.getBeneficioClimaticoContribuido());
            stmt.setInt(5, perfil.getId()); // ID do usuário na cláusula WHERE

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                // Isso pode acontecer se o perfil ainda não existir, o ideal seria um insert.
                // Por ora, uma mensagem de erro é suficiente.
                System.err.println("Aviso: Tentativa de atualização do perfil do vendedor falhou (ID do usuário: " + perfil.getId() + ").");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar perfil de vendedor: " + e.getMessage());
        }
    }

    // ... (outros métodos do DAO como insert, getById, etc.)
}