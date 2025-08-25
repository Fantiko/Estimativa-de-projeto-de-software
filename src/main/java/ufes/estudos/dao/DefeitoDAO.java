package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Defeito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefeitoDAO {
    private final SQLiteConnectionManager connectionManager;

    public DefeitoDAO(SQLiteConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public List<Defeito> getAll() {
        String sql = "SELECT nome, percentualAbate FROM defeitos";
        List<Defeito> defeitos = new ArrayList<>();

        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                double percentual = rs.getDouble("percentualAbate");
                defeitos.add(new Defeito(nome, percentual));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os defeitos: " + e.getMessage());
        }
        return defeitos;
    }

    // Futuramente, podemos adicionar métodos insert, update, delete se necessário
}