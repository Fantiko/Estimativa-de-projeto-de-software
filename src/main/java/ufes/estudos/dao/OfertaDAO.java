package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.transacao.Oferta;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OfertaDAO {

    public OfertaDAO(SQLiteConnectionManager connectionManager) {
    }

    public void insert(Oferta oferta) {
        String sql = "INSERT INTO ofertas(idcItem, idComprador, idVendedor, valorOfertado, dataOferta) VALUES(?,?,?,?,?)";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, oferta.getIdcItem());
            stmt.setInt(2, oferta.getIdComprador());
            stmt.setInt(3, oferta.getIdVendedor());
            stmt.setDouble(4, oferta.getValorOfertado());
            stmt.setString(5, oferta.getDataOferta().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir oferta: " + e.getMessage());
        }
    }

    public void delete(Oferta oferta) {
        String sql = "DELETE FROM ofertas WHERE idcItem = ? AND idComprador = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, oferta.getIdcItem());
            stmt.setInt(2, oferta.getIdComprador());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar oferta: " + e.getMessage());
        }
    }

    public void deleteByItem(String idcItem) {
        String sql = "DELETE FROM ofertas WHERE idcItem = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idcItem);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar ofertas por item: " + e.getMessage());
        }
    }

    public List<Oferta> getAll() {
        List<Oferta> ofertas = new ArrayList<>();
        String sql = "SELECT * FROM ofertas";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ofertas.add(new Oferta(
                        rs.getString("idcItem"),
                        rs.getInt("idComprador"),
                        rs.getInt("idVendedor"),
                        rs.getDouble("valorOfertado")
                        // A data é gerada no construtor, não precisa ser lida aqui para simplificar
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as ofertas: " + e.getMessage());
        }
        return ofertas;
    }
}