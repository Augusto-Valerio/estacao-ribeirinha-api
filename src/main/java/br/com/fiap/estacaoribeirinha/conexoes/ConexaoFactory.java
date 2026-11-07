package br.com.fiap.estacaoribeirinha.conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";

    private static final String USUARIO = System.getenv("ORACLE_USUARIO");

    private static final String SENHA = System.getenv("ORACLE_SENHA");

    // Metodo de conexao com o banco de dados
    public Connection conexao() throws ClassNotFoundException, SQLException {

        validarCredenciais();

        // Driver Oracle
        Class.forName("oracle.jdbc.driver.OracleDriver");

        // Retornar conexao
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    private void validarCredenciais() {
        if (USUARIO == null || USUARIO.isBlank()) {
            throw new IllegalStateException("A variável ORACLE_USUARIO não foi configurada.");
        }

        if (SENHA == null || SENHA.isBlank()) {
            throw new IllegalStateException("A variável ORACLE_SENHA não foi configurada.");
        }
    }
}
