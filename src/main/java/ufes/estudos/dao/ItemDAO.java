package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Defeito;
import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Item.Material;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private final SQLiteConnectionManager connectionManager;

    public ItemDAO(SQLiteConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void insert(Item item) {
        String sql = "INSERT INTO itens (identificadorCircular, tipoPeca, subcategoria, tamanho, corPredominante, " +
                "estadoConservacao, massaEstimada, precoBase, idVendedor, material_nome, defeito_nome, gwpBase, gwpAvoided, ciclo) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getIdentificadorCircular());
            stmt.setString(2, item.getTipoPeca());
            stmt.setString(3, item.getSubcategoria());
            stmt.setString(4, item.getTamanho());
            stmt.setString(5, item.getCorPredominante());
            stmt.setString(6, item.getEstadoConservacao());
            stmt.setDouble(7, item.getMassaEstimada());
            stmt.setDouble(8, item.getPrecoBase());
            stmt.setInt(9, item.getIdVendedor());
            stmt.setString(10, item.getMaterial().getNome());
            stmt.setString(11, item.getDefeito().getDefeito());
            stmt.setDouble(12, item.getGwpBase());
            stmt.setDouble(13, item.getGwpAvoided());
            stmt.setInt(14, item.getCiclo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir item: " + e.getMessage());
        }
    }

    public Item getByIdc(String idc) {
        String sql = "SELECT * FROM itens WHERE identificadorCircular = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idc);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar item por IDC: " + e.getMessage());
        }
        return null;
    }

    public List<Item> getAll() {
        List<Item> itens = new ArrayList<>();
        String sql = "SELECT * FROM itens";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                itens.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os itens: " + e.getMessage());
        }
        return itens;
    }

    public void delete(String idc) {
        String sql = "DELETE FROM itens WHERE identificadorCircular = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idc);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar item: " + e.getMessage());
        }
    }

    private Item fromResultSet(ResultSet rs) throws SQLException {
        // Para simplificar, recriamos Material e Defeito com dados limitados.
        // Uma versão mais avançada buscaria os objetos completos de seus respectivos DAOs.
        Material material = new Material(rs.getString("material_nome"), 0);
        Defeito defeito = new Defeito(rs.getString("defeito_nome"), 0);

        Item item = new Item(
                rs.getString("identificadorCircular"),
                rs.getString("tipoPeca"),
                rs.getString("subcategoria"),
                rs.getString("tamanho"),
                rs.getString("corPredominante"),
                material,
                defeito,
                rs.getString("estadoConservacao"),
                rs.getDouble("massaEstimada"),
                rs.getDouble("precoBase"),
                rs.getInt("idVendedor"),
                rs.getDouble("gwpBase"),
                rs.getDouble("gwpAvoided")
        );
        item.setCiclo(rs.getInt("ciclo"));
        return item;
    }

    public void update(Item item) {
        String sql = "UPDATE itens SET tipoPeca = ?, subcategoria = ?, tamanho = ?, corPredominante = ?, " +
                "estadoConservacao = ?, massaEstimada = ?, precoBase = ?, material_nome = ?, defeito_nome = ? " +
                "WHERE identificadorCircular = ?";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getTipoPeca());
            stmt.setString(2, item.getSubcategoria());
            stmt.setString(3, item.getTamanho());
            stmt.setString(4, item.getCorPredominante());
            stmt.setString(5, item.getEstadoConservacao());
            stmt.setDouble(6, item.getMassaEstimada());
            stmt.setDouble(7, item.getPrecoBase());
            stmt.setString(8, item.getMaterial().getNome());
            stmt.setString(9, item.getDefeito().getDefeito());
            stmt.setString(10, item.getIdentificadorCircular()); // Cláusula WHERE
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item: " + e.getMessage());
        }
    }

}