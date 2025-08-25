package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.repository.RepositoriesIntefaces.InsigniasRepository;

import java.util.List;

public class InsigniaVendedorSQLiteRepository implements InsigniasRepository {
    @Override
    public void adicionarInsignia(int perfilId, int insigniaId) {
        String sql = "INSERT INTO perfilVendedorInsignias (perfilVendedorId, insigniaId) VALUES (?, ?)";
        try (var con = SQLiteConnectionManager.getConnection()) {
            var stmt = con.prepareStatement(sql);
            stmt.setInt(1, perfilId);
            stmt.setInt(2, insigniaId);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erro ao adicionar insignia: " + e.getMessage());
        }
    }

    @Override
    public String buscarDescricao(int idInsignea) {
        String sql = "SELECT descricao FROM insignias WHERE id = ?";
        try (var con = SQLiteConnectionManager.getConnection()) {
            var stmt = con.prepareStatement(sql);
            stmt.setInt(1, idInsignea);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("descricao");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar descrição da insignia: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Insignia> buscarInsignias(int perfilId) {
        String sql = "SELECT i.id AS insigniaId, i.nome AS nomeInsignia, i.descricao, pvi.dataConquista " +
                     "FROM perfilVendedorInsignias pvi " +
                     "JOIN insignias i ON pvi.insigniaId = i.id " +
                     "WHERE pvi.perfilVendedorId = ?";

        try (var con = SQLiteConnectionManager.getConnection()) {
            var stmt = con.prepareStatement(sql);
            stmt.setInt(1, perfilId);
            var rs = stmt.executeQuery();

            List<Insignia> insignias = new java.util.ArrayList<>();
            while (rs.next()) {
                Insignia insignia = new Insignia();
                insignia.setId(rs.getInt("insigniaId"));
                insignia.setNome(rs.getString("nomeInsignia"));
                insignia.setDescricao(rs.getString("descricao"));
                insignia.setDataConquista(rs.getDate("dataConquista").toLocalDate());
                insignias.add(insignia);
            }
            return insignias;

        } catch (Exception e) {
            System.err.println("Erro ao buscar insignias: " + e.getMessage());
        }


        return null;
    }

    @Override
    public int contarInsignias() {
        return 0;
    }

    @Override
    public List<Insignia> listarTodasInsignias() {
        return null;
    }

    @Override
    public void removerInsignia(int perfilId, int insigniaId) {
        String sql = "DELETE FROM perfilVendedorInsignias WHERE perfilVendedorId = ? AND insigniaId = ?";
        try (var con = SQLiteConnectionManager.getConnection()) {
            var stmt = con.prepareStatement(sql);
            stmt.setInt(1, perfilId);
            stmt.setInt(2, insigniaId);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erro ao remover insignia: " + e.getMessage());
        }
    }
}
