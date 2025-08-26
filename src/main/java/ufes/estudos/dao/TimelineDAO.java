package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.eventos.EventoTimeline;
import ufes.estudos.Model.eventos.TipoEvento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimelineDAO {

    public TimelineDAO(SQLiteConnectionManager connectionManager) {
    }

    public void insert(EventoTimeline evento) {
        String sql = "INSERT INTO timeline(idcItem, tipoEvento, dataEvento, gwpAvoided, mci, detalhes) VALUES(?,?,?,?,?,?)";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, evento.getIdcItem());
            stmt.setString(2, evento.getTipoEvento().name()); // Converte enum para String
            stmt.setString(3, evento.getDataEvento().toString());
            stmt.setDouble(4, evento.getGwpAvoidedNoMomento());
            stmt.setDouble(5, evento.getMciNoMomento());
            stmt.setString(6, evento.getDetalhes());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir evento na timeline: " + e.getMessage());
        }
    }

    public List<EventoTimeline> getByItemId(String idcItem) {
        List<EventoTimeline> eventos = new ArrayList<>();
        String sql = "SELECT * FROM timeline WHERE idcItem = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idcItem);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    eventos.add(new EventoTimeline(
                            rs.getString("idcItem"),
                            TipoEvento.valueOf(rs.getString("tipoEvento")), // Converte String para enum
                            rs.getDouble("gwpAvoided"),
                            rs.getDouble("mci"),
                            rs.getString("detalhes")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar timeline por item: " + e.getMessage());
        }
        return eventos;
    }
}