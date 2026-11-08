package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.conexoes.ConexaoFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {

        try (Connection conexao = new ConexaoFactory().conexao()) {

            if (conexao != null && !conexao.isClosed()) {
                System.out.println("Conectado ao banco de dados Oracle com sucesso!");
            }

        } catch (ClassNotFoundException erro) {
            System.out.println("O driver do banco de dados Oracle não foi encontrado.");
            erro.printStackTrace();

        } catch (SQLException erro) {
            System.out.println("Não foi possivel conectar ao banco de dados Oracle.");
            erro.printStackTrace();

        } catch (IllegalStateException erro) {
            System.out.println(erro.getMessage());
        }
    }
}

