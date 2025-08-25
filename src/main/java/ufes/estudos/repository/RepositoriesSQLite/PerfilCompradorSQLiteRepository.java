package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.Model.Usuario.NivelReputacao;
import ufes.estudos.Model.Usuario.PerfilComprador;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.repository.RepositoriesIntefaces.InsigniasRepository;
import ufes.estudos.repository.RepositoriesIntefaces.PerfilCompradorRepository;

import java.util.List;
import java.util.Optional;

public class PerfilCompradorSQLiteRepository implements PerfilCompradorRepository{
    @Override
    public void adicionar(PerfilComprador perfil) {
        String sql = "INSERT INTO perfilComprador (usuarioId) VALUES (?)";
        try (var con = SQLiteConnectionManager.getConnection();
             var stmt = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, perfil.getId());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Inserção falhou, nenhuma linha afetada.");
            }

            try (var generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    perfil.setId(generatedKeys.getInt(1));
                } else {
                    throw new RuntimeException("Falha ao obter o ID gerado.");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao adicionar perfil comprador: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(PerfilComprador perfil) {
        //TODO
    }

    @Override
    public Optional<PerfilComprador> buscarPorId(int id) {
        //TODO
        return Optional.empty();
    }

    @Override
    public Optional<PerfilComprador> buscarPorUsuarioId(Usuario usuario) {
        String sql = "SELECT * FROM perfilComprador WHERE usuarioId = ?";

        try (var con = SQLiteConnectionManager.getConnection();
             var stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, usuario.getId());

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PerfilComprador perfil = new PerfilComprador(usuario);
                    perfil.setId(rs.getInt("id"));
                    perfil.setNivelReputacao(NivelReputacao.valueOf(rs.getString("nivelReputacao")));
                    perfil.setTotalEstrelas(rs.getInt("totalEstrelas"));
                    perfil.setComprasFinalizadas(rs.getInt("comprasFinalizadas"));
                    perfil.setCO2Evitado(rs.getDouble("CO2Evitado"));
                    perfil.setSeloVerificado(rs.getBoolean("seloVerificado"));
                    perfil.setEstatisticaDenunciasProcedentes(rs.getDouble("estatisticaDenunciasProcedentes"));
                    perfil.setInsigniasPermanentes(buscarInsignias(perfil.getId()));

                    // Defina outros atributos conforme necessário
                    return Optional.of(perfil);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar perfil comprador por usuário: " + e.getMessage());
        }

        return Optional.empty();
    }

    public List<Insignia> buscarInsignias(int perfilCompradorId) {
        String sql = "SELECT \n" +
                "    i.id AS insigniaId,\n" +
                "    i.nome AS nomeInsignia,\n" +
                "    i.descricao,\n" +
                "    pci.dataConquista\n" +
                "FROM \n" +
                "    perfilComprador AS pc\n" +
                "JOIN \n" +
                "    perfilCompradorInsignias AS pci ON pc.id = pci.perfilCompradorId\n" +
                "JOIN \n" +
                "    insignias AS i ON pci.insigniaId = i.id\n" +
                "WHERE \n" +
                "    pc.id = ?;";
        try (var con = SQLiteConnectionManager.getConnection()){
            var stmt = con.prepareStatement(sql);
            stmt.setInt(1, perfilCompradorId);
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

}
