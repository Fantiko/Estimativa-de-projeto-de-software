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
        String sql = "INSERT INTO perfilVendedor (usuario_id) VALUES (?)";
        try (Connection con = connectionManager.getConnection();
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

    }

    @Override
    public Optional<PerfilVendedor> buscarPorId(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<PerfilVendedor> buscarPorUsuarioId(Usuario usuario) {
        String sql = "SELECT * FROM perfilVendedor WHERE usuario_id = ?";
        try (Connection con = connectionManager.getConnection();
             var stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId());
            var rs = stmt.executeQuery();

            if (rs.next()) {
                PerfilVendedor perfil = new PerfilVendedor(usuario);
                perfil.setTotalEstrelas(rs.getInt("total_estrelas"));
                perfil.setVendasConcluidas(rs.getInt("vendas_concluidas"));
                perfil.setDenunciasRecebidas(rs.getInt("denuncias_recebidas"));
                perfil.setBeneficioClimaticoContribuido(rs.getDouble("beneficio_climatico_contribuido"));
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
    public void adicionarInsignia(int perfilVendedorId, int insigniaId) {


    }

    @Override
    public void removerInsignia(int perfilVendedorId, int insigniaId) {

    }

    @Override
    public List<Insignia> buscarInsignias(int perfilVendedorId) {
        String sql = "SELECT * FROM insignias WHERE perfil_vendedor_id = ?";

        try (Connection con = connectionManager.getConnection();
             var stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, perfilVendedorId);
            var rs = stmt.executeQuery();

            List<Insignia> insignias = new java.util.ArrayList<>();
            while (rs.next()) {
                Insignia insignia = new Insignia();
                insignia.setId(rs.getInt("id"));
                insignia.setNome(rs.getString("nome"));
                insignias.add(insignia);
            }
            return insignias;

        } catch (Exception e) {
            System.err.println("Erro ao buscar insignias: " + e.getMessage());

        }

        return null;
    }
}
