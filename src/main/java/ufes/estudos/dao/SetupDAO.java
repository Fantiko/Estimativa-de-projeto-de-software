package ufes.estudos.dao;

import ufes.estudos.Bd.connectionManager.SQLiteConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class SetupDAO {

    // Mapas de dados que serão inseridos no banco
    private static final Map<String, Double> FATORES_EMISSAO = Map.of(
            "Algodão", 1.5, "Poliéster", 5.5, "Couro", 17.0, "Metal", 2.0,
            "Plástico de base fóssil", 3.0, "Outros", 2.5
    );

    private static final Map<String, Double> PERCENTUAIS_DEFEITO = Map.ofEntries(
            Map.entry("Rasgo estruturante", 0.30),
            Map.entry("Ausência de botão principal", 0.15),
            Map.entry("Zíper parcialmente funcional", 0.25),
            Map.entry("Mancha permanente", 0.20),
            Map.entry("Desgaste por pilling acentuado", 0.10),
            Map.entry("Sola sem relevo funcional", 0.40),
            Map.entry("Descolamento parcial da entressola", 0.35),
            Map.entry("Arranhões profundos", 0.20),
            Map.entry("Palmilha original ausente", 0.10),
            Map.entry("Odor persistente leve", 0.05),
            Map.entry("Alça reparada", 0.15),
            Map.entry("Fecho defeituoso", 0.20),
            Map.entry("Desbotamento extenso", 0.10),
            Map.entry("Forro rasgado", 0.25),
            Map.entry("Oxidação visível", 0.15),
            Map.entry("Pedra ausente", 0.20),
            Map.entry("Fecho frouxo", 0.10)
    );

    public void criaTabelas() {
        String[] sqls = {
                // Tabela de Usuários
                "CREATE TABLE IF NOT EXISTS usuarios (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nomeCompleto TEXT NOT NULL, " +
                        "email TEXT NOT NULL UNIQUE, " +
                        "telefone TEXT, " +
                        "dataCriacao TEXT NOT NULL, " +
                        "usuario TEXT NOT NULL UNIQUE, " +
                        "senha TEXT NOT NULL, " +
                        "vendedor INTEGER NOT NULL DEFAULT 0, " +
                        "comprador INTEGER NOT NULL DEFAULT 0, " +
                        "admin INTEGER NOT NULL DEFAULT 0" +
                        ");",

                // Tabela de Perfis de Vendedor
                "CREATE TABLE IF NOT EXISTS perfilVendedor (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "usuario_id INTEGER NOT NULL UNIQUE, " +
                        "nivelReputacao TEXT NOT NULL DEFAULT 'bronze', " +
                        "totalEstrelas REAL NOT NULL DEFAULT 0.0, " +
                        "vendasConcluidas INTEGER NOT NULL DEFAULT 0, " +
                        "denunciasRecebidas INTEGER NOT NULL DEFAULT 0, " +
                        "beneficioClimaticoContribuido REAL NOT NULL DEFAULT 0.0, " +
                        "FOREIGN KEY(usuario_id) REFERENCES usuarios(id)" +
                        ");",

                // Tabela de Perfis de Comprador
                "CREATE TABLE IF NOT EXISTS perfilComprador (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "usuarioId INTEGER NOT NULL UNIQUE, " +
                        "nivelReputacao TEXT NOT NULL DEFAULT 'bronze', " +
                        "totalEstrelas REAL NOT NULL DEFAULT 0.0, " +
                        "comprasFinalizadas INTEGER NOT NULL DEFAULT 0, " +
                        "seloVerificado INTEGER NOT NULL DEFAULT 0, " +
                        "co2Evitado REAL NOT NULL DEFAULT 0.0, " +
                        "denunciasProcedentes REAL NOT NULL DEFAULT 0.0, " +
                        "FOREIGN KEY(usuarioId) REFERENCES usuarios(id)" +
                        ");",

                // Tabela de Materiais
                "CREATE TABLE IF NOT EXISTS materiais (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nome TEXT NOT NULL UNIQUE, " +
                        "fatorEmissao REAL" +
                        ");",

                // Tabela de Defeitos
                "CREATE TABLE IF NOT EXISTS defeitos (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "nome TEXT NOT NULL UNIQUE, " +
                        "percentualAbate REAL NOT NULL DEFAULT 0.0" +
                        ");",

                // Tabela de Itens (Anúncios)
                "CREATE TABLE IF NOT EXISTS itens (" +
                        "identificadorCircular TEXT PRIMARY KEY, " +
                        "tipoPeca TEXT, subcategoria TEXT, tamanho TEXT, corPredominante TEXT, " +
                        "estadoConservacao TEXT, massaEstimada REAL, precoBase REAL, " +
                        "nomeVendedor TEXT, gwpBase REAL, gwpAvoided REAL, ciclo INTEGER, " +
                        "vendedorId INTEGER NOT NULL, material_nome TEXT, defeito_nome TEXT, " +
                        "FOREIGN KEY(vendedorId) REFERENCES perfilVendedor(id)" +
                        ");"
                // Adicione outras tabelas como ofertas, vendas, etc., se necessário
        };

        try (Connection conn = SQLiteConnectionManager.getConnection();
             Statement stmt = conn.createStatement()) {
            for (String sql : sqls) {
                stmt.execute(sql);
            }
            System.out.println("Tabelas criadas com sucesso (ou já existentes).");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    public void popularTabelasBase() {
        popularMateriais();
        popularDefeitos();
    }

    private void popularMateriais() {
        String sqlPrefix = "INSERT OR IGNORE INTO materiais (nome, fatorEmissao) VALUES ";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             Statement stmt = conn.createStatement()) {
            for (Map.Entry<String, Double> entry : FATORES_EMISSAO.entrySet()) {
                String sql = sqlPrefix + "('" + entry.getKey() + "', " + entry.getValue() + ");";
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            System.out.println("Tabela 'materiais' populada com sucesso (ou já continha os dados).");
        } catch (SQLException e) {
            System.err.println("Erro ao popular a tabela 'materiais': " + e.getMessage());
        }
    }

    private void popularDefeitos() {
        String sqlPrefix = "INSERT OR IGNORE INTO defeitos (nome, percentualAbate) VALUES ";
        try (Connection conn = SQLiteConnectionManager.getConnection();
             Statement stmt = conn.createStatement()) {
            for (Map.Entry<String, Double> entry : PERCENTUAIS_DEFEITO.entrySet()) {
                String sql = sqlPrefix + "('" + entry.getKey() + "', " + entry.getValue() + ");";
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            System.out.println("Tabela 'defeitos' populada com sucesso (ou já continha os dados).");
        } catch (SQLException e) {
            System.err.println("Erro ao popular a tabela 'defeitos': " + e.getMessage());
        }
    }
}