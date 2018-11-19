package br.com.sagap.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao implements Serializable{
    public Connection conex;
    public Statement sentenca;

    public Conexao() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/sagap";
        String user = "root";
        String password = "root";
        conex = DriverManager.getConnection(url, user, password);
        sentenca = (Statement) conex.createStatement();
    }
}