package ufes.estudos.repository.RepositoriesSQLite;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import ufes.estudos.Model.Usuario.Usuario;
import ufes.estudos.repository.RepositoriesIntefaces.UsuarioRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioSQLiteRepository implements UsuarioRepository {

    private final SQLiteConnectionManager connManager;

    public UsuarioSQLiteRepository(SQLiteConnectionManager connManager) {
        this.connManager = connManager;
    }

    @Override
    public Optional<Usuario> adicionar(Usuario usuario) {
        String sql = "INSERT INTO usuarios(nomeCompleto, email, telefone, dataCriacao, usuario, senha, vendedor, comprador, admin) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, String.valueOf(usuario.getDataCriacao()));
            stmt.setString(5, usuario.getUsuario());
            stmt.setString(6, usuario.getSenha());
            stmt.setInt(7, usuario.isVendedor() ? 1:0);
            stmt.setInt(8, usuario.isComprador()? 1:0);
            stmt.setInt(9, usuario.isAdmin()? 1:0);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                // A inserção falhou
                return Optional.empty();
            }

            // Obtém o ID gerado automaticamente
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Define o ID no objeto usuario
                    usuario.setId(generatedKeys.getInt(1));
                    // Retorna o usuário com o ID definido
                    return Optional.of(usuario);
                } else {
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar usuário: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nomeCompleto = ?, email = ?, telefone = ?, dataCriacao = ?, usuario = ?, " +
                "senha = ?, vendedor = ?, comprador = ?, admin = ? WHERE id = ?";

        try (Connection conn = connManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, String.valueOf(usuario.getDataCriacao()));
            stmt.setString(5, usuario.getUsuario());
            stmt.setString(6, usuario.getSenha());
            stmt.setInt(7, usuario.isVendedor() ? 1 : 0);
            stmt.setInt(8, usuario.isComprador()? 1 : 0);
            stmt.setInt(9, usuario.isAdmin()? 1 : 0);
            stmt.setInt(10, usuario.getId()); // O ID na cláusula WHERE

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @Override
    public void remover(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = connManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao remover usuário: " + e.getMessage());
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = connManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(criarUsuarioDoResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorUsuario(String usuario) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try (Connection conn = connManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(criarUsuarioDoResultSet(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> buscarTodos() {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = connManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(criarUsuarioDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os usuários: " + e.getMessage());
        }
        return usuarios;
    }

    // Método auxiliar para construir um objeto Usuario a partir de um ResultSet
    private Usuario criarUsuarioDoResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nomeCompleto"));
        usuario.setEmail(rs.getString("email"));
        usuario.setTelefone(rs.getString("telefone"));
        usuario.setDataCriacao(LocalDateTime.parse(rs.getString("dataCriacao")));
        usuario.setUsuario(rs.getString("usuario"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setVendedor(rs.getInt("vendedor") == 1);
        usuario.setComprador(rs.getInt("comprador") == 1);
        usuario.setAdmin(rs.getInt("admin") == 1);
        return usuario;
    }
}