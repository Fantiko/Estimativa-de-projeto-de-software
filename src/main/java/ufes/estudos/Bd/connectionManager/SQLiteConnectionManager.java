package ufes.estudos.Bd.connectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnectionManager implements ConnectionManager{

    public static final  String URL_BD = "jdbc:sqlite:Bd/loja.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_BD);
    }
}
