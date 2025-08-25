package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Item.Defeito;
import ufes.estudos.Model.Item.Item;
import ufes.estudos.Model.Item.Material;
import ufes.estudos.repository.RepositoriesIntefaces.ItemRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemSQLiteRepository implements ItemRepository {
    @Override
    public void adicionar(Item item) {
        String sql = "INSERT INTO itens (\n" +
                "    identificadorCircular, \n" +
                "    tipoPeca, \n" +
                "    subcategoria, \n" +
                "    tamanho, \n" +
                "    corPredominante, \n" +
                "    estadoConservacao, \n" +
                "    massaEstimada, \n" +
                "    precoBase, \n" +
                "    ciclo,\n" +
                "    gwpBase,\n" +
                "    gwpAvoided,\n" +
                "    vendedorId, \n" +
                "    materialId\n" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        String sqlDefeito = "INSERT INTO itemDefeitos (\n" +
                "    itemId, \n" +
                "    defeitoId, \n" +
                "    percentual\n" +
                ") VALUES (?, ?, ?);";

        try (var con = SQLiteConnectionManager.getConnection()) {
            try (var stmt = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, item.getIdentificadorCircular());
                stmt.setString(2, item.getTipoPeca());
                stmt.setString(3, item.getSubcategoria());
                stmt.setString(4, item.getTamanho());
                stmt.setString(5, item.getCorPredominante());
                stmt.setString(6, item.getEstadoConservacao());
                stmt.setDouble(7, item.getMassaEstimada());
                stmt.setDouble(8, item.getPrecoBase());
                stmt.setInt(9, item.getCiclo());
                stmt.setDouble(10, item.getGwpBase());
                stmt.setDouble(11, item.getGwpAvoided());
                stmt.setInt(12, item.getIdVendedor());
                stmt.setInt(13, item.getMaterial().getId());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Inserção falhou, nenhuma linha afetada.");
                }

                try (var generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {

                        int itemId = generatedKeys.getInt(1);
                        // Inserir defeitos associados ao item

                        Defeito defeito = item.getDefeito();

                        try (var defectStmt = con.prepareStatement(sqlDefeito)) {
                            defectStmt.setInt(1, itemId);
                            defectStmt.setInt(2, defeito.getId());
                            defectStmt.setDouble(3, defeito.getPercentual());
                            defectStmt.executeUpdate();
                        }catch (SQLException e) {
                            System.err.println("Erro ao inserir defeito do item: " + e.getMessage());
                            throw new SQLException("Falha ao inserir defeito do item.");
                        }

                    } else {
                        throw new SQLException("Falha ao obter o ID gerado.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar item: " + e.getMessage());
        }

    }

    @Override
    public void atualizar(Item item) {

    }

    @Override
    public void remover(String identificadorCircular) {

    }

    @Override
    public Optional<Item> buscarPorId(String identificadorCircular) {
        return Optional.empty();
    }

    @Override
    public List<Item> buscarPorVendedorId(int vendedorId) {
        String sql = "SELECT * FROM itens WHERE vendedorId = ?";

        try (var con = SQLiteConnectionManager.getConnection();
             var stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, vendedorId);
            var rs = stmt.executeQuery();
            List<Item> itens = buscarItensDoResultSet(rs);
            // Aqui você deve mapear o ResultSet para uma lista de itens
            // Exemplo:
            // List<Item> itens = new ArrayList<>();
            // while (rs.next()) {
            //     Item item = new Item(rs.getString("identificadorCircular"), ...);
            //     itens.add(item);
            // }
            // return itens;

        } catch (SQLException e) {
            System.err.println("Erro ao buscar itens por vendedor: " + e.getMessage());
        }

        return null; // Retorne a lista de itens mapeada
    }

    @Override
    public List<Item> buscarTodos() {
        return null;
    }

    @Override
    public int contarItensPorVendedor(int vendedorId) {
        return 0;
    }

    private List<Item> buscarItensDoResultSet(java.sql.ResultSet rs) throws SQLException {
         List<Item> itens = new ArrayList<>();
         while (rs.next()) {
             Item item = new Item(rs.getString("identificadorCircular"));

            item.setTipoPeca(rs.getString("tipoPeca"));
            item.setSubcategoria(rs.getString("subcategoria"));
            item.setTamanho(rs.getString("tamanho"));
            item.setCorPredominante(rs.getString("corPredominante"));


            item.setEstadoConservacao(rs.getString("estadoConservacao"));
            item.setMassaEstimada(rs.getDouble("massaEstimada"));
            item.setPrecoBase(rs.getDouble("precoBase"));
            item.setIdVendedor(rs.getInt("vendedorId"));
            item.setGwpBase(rs.getDouble("gwpBase"));
            item.setGwpAvoided(rs.getDouble("gwpAvoided"));
            item.setCiclo(rs.getInt("ciclo"));
            item.setDefeito(buscarDefeito(rs.getString("identificadorCircular")));
            item.setMaterial(buscarMaterial(rs.getInt("materialId")));

             itens.add(item);
         }
         return itens;
    }

    private Defeito buscarDefeito(String identificadorCircular){
        String sql = "SELECT\n" +
                "    d.id AS defeito_id,\n" +
                "    d.nome AS defeito_nome,\n" +
                "    d.descricao,\n" +
                "    id.percentual\n" +
                "FROM\n" +
                "    itens AS i\n" +
                "JOIN\n" +
                "    itemDefeitos AS id ON i.identificadorCircular = id.itemId\n" +
                "JOIN\n" +
                "    defeitos AS d ON id.defeitoId = d.id\n" +
                "WHERE\n" +
                "    i.identificadorCircular = ?;";

        try (var con = SQLiteConnectionManager.getConnection();
             var stmt = con.prepareStatement(sql)) {

            stmt.setString(1, identificadorCircular);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                Defeito defeito = new Defeito();
                defeito.setId(rs.getInt("id"));
                defeito.setDefeito(rs.getString("nome"));
                defeito.setDescricao(rs.getString("descricao"));
                defeito.setPercentual(rs.getDouble("percentual"));
                return defeito;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar defeito: " + e.getMessage());
        }

        return null;
    }

    private Material buscarMaterial(int materialId) {
        String sql = "SELECT * FROM materiais WHERE id = ?";

        try (var con = SQLiteConnectionManager.getConnection();
             var stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, materialId);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                Material material = new Material(rs.getString("nome"),
                        rs.getDouble("fatoEmissao"));
                material.setId(rs.getInt("id"));

                return material;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar material: " + e.getMessage());
        }

        return null;
    }








}


