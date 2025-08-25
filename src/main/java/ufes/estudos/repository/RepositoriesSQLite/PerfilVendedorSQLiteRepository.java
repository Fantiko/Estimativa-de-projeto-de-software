package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.Model.Usuario.NivelReputacao;
import ufes.estudos.Model.Usuario.PerfilVendedor;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.repository.RepositoriesIntefaces.PerfilVendedorRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class PerfilVendedorSQLiteRepository implements PerfilVendedorRepository {

    private final SQLiteConnectionManager connectionManager;
    public PerfilVendedorSQLiteRepository(SQLiteConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<PerfilVendedor> adicionar(PerfilVendedor perfil) {
        String sql = "INSERT INTO perfilVendedor (usuarioId) VALUES (?)";
        try (Connection con = SQLiteConnectionManager.getConnection();
             var stmt = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, perfil.getId());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty(); // Inserção falhou
            }

            try (var generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    perfil.setId(generatedKeys.getInt(1));
                    return Optional.of(perfil);
                } else {
                    return Optional.empty(); // Falha ao obter o ID gerado
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao adicionar perfil vendedor: " + e.getMessage());
            return Optional.empty();

        }
    }

    @Override
    public void atualizar(PerfilVendedor perfil) {
        String sql = "UPDATE perfilVendedor SET nivelReputacao = ?, totalEstrelas = ?, vendasConcluidas = ?, denunciasRecebidas = ?, beneficioClimaticoContribuido = ? WHERE id = ?";
        try (Connection con = SQLiteConnectionManager.getConnection();
             var stmt = con.prepareStatement(sql)) {

            stmt.setString(1, perfil.getNivelReputacao().name());
            stmt.setDouble(2, perfil.getTotalEstrelas());
            stmt.setInt(3, perfil.getVendasConcluidas());
            stmt.setInt(4, perfil.getDenunciasRecebidas());
            stmt.setDouble(5, perfil.getBeneficioClimaticoContribuido());
            stmt.setInt(6, perfil.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Atualização falhou, nenhuma linha afetada.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar perfil vendedor: " + e.getMessage());
        }

    }

    @Override
    public Optional<PerfilVendedor> buscarPorUsuarioId(Usuario usuario) {
        String sql = "SELECT * FROM perfilVendedor WHERE usuarioId = ?";
        try (Connection con = connectionManager.getConnection();
             var stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId());
            var rs = stmt.executeQuery();

            if (rs.next()) {
                PerfilVendedor perfil = new PerfilVendedor(usuario);
                perfil.setTotalEstrelas(rs.getInt("totalEstrelas"));
                perfil.setVendasConcluidas(rs.getInt("vendasConcluidas"));
                perfil.setDenunciasRecebidas(rs.getInt("denunciasRecebidas"));
                perfil.setBeneficioClimaticoContribuido(rs.getDouble("beneficioClimaticoContribuido"));
                perfil.setNivelReputacao(NivelReputacao.valueOf(rs.getString("nivelReputacao")));
                perfil.setInsignias(buscarInsignias(perfil.getId()));

                return Optional.of(perfil);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar perfil vendedor por usuário ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Insignia> buscarInsignias(int perfilVendedorId) {
        String sql = "SELECT \n" +
                "    i.id AS insigniaId,\n" +
                "    i.nome AS nomeInsignia,\n" +
                "    i.descricao,\n" +
                "    pvi.dataConquista\n" +
                "FROM \n" +
                "    perfilVendedor AS pv\n" +
                "JOIN \n" +
                "    perfilVendedorInsignias AS pvi ON pv.id = pvi.perfilVendedorId\n" +
                "JOIN \n" +
                "    insignias AS i ON pvi.insigniaId = i.id\n" +
                "WHERE \n" +
                "    pv.id = ?;";

        try (Connection con = connectionManager.getConnection();
             var stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, perfilVendedorId);
            var rs = stmt.executeQuery();

            List<Insignia> insignias = new java.util.ArrayList<>();
            while (rs.next()) {
                Insignia insignia = new Insignia();
                insignia.setId(rs.getInt("id"));
                insignia.setNome(rs.getString("nome"));
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
}
