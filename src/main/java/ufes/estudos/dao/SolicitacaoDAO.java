package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.SolicitacaoPerfil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoDAO {

    public SolicitacaoDAO(SQLiteConnectionManager connectionManager) {
    }

    public void insert(SolicitacaoPerfil solicitacao) {
        String sql = "INSERT OR IGNORE INTO solicitacoes(nomeUsuario, perfilSolicitado, dataSolicitacao) VALUES(?,?,?)";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, solicitacao.getNomeUsuario());
            stmt.setString(2, solicitacao.getPerfilSolicitado());
            stmt.setString(3, solicitacao.getDataSolicitacao().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir solicitação: " + e.getMessage());
        }
    }

    public void delete(String nomeUsuario, String perfilSolicitado) {
        String sql = "DELETE FROM solicitacoes WHERE nomeUsuario = ? AND perfilSolicitado = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario);
            stmt.setString(2, perfilSolicitado);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar solicitação: " + e.getMessage());
        }
    }

    public List<SolicitacaoPerfil> getAll() {
        List<SolicitacaoPerfil> solicitacoes = new ArrayList<>();
        String sql = "SELECT * FROM solicitacoes";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // O construtor já gera a data atual, o que é suficiente para o fluxo
                solicitacoes.add(new SolicitacaoPerfil(
                        rs.getString("nomeUsuario"),
                        rs.getString("perfilSolicitado")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as solicitações: " + e.getMessage());
        }
        return solicitacoes;
    }
}