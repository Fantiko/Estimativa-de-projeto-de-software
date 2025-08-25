package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.Insignia;
import ufes.estudos.repository.RepositoriesIntefaces.InsigniasRepository;

import java.util.List;

public class InsigniaCompradorSQLiteRepository implements InsigniasRepository {
    @Override
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

    @Override
    public void adicionarInsignia(int perfilId, int insigniaId) {
        //todo
    }

    @Override
    public String buscarDescricao(int idInsignea) {
        //todo
        return null;
    }

    @Override
    public int contarInsignias() {
        //todo
        return 0;
    }

    @Override
    public List<Insignia> listarTodasInsignias() {
        //todo
        return null;
    }

    @Override
    public void removerInsignia(int perfilId, int insigniaId) {

    }


}
