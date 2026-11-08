package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.dao.EstacaoDAO;
import br.com.fiap.estacaoribeirinha.entities.Estacao;

import java.sql.SQLException;
import java.util.ArrayList;

public class TesteSelecionarEstacoes {

    public static void main(String[] args) {
        try {
            EstacaoDAO estacaoDAO = new EstacaoDAO();

            ArrayList<Estacao> estacoes = estacaoDAO.selecionar();

            System.out.println("Quantidade de estavoes encontradas: " + estacoes.size());

            for (Estacao estacao : estacoes) {
                System.out.println();
                System.out.println(estacao);
            }

        } catch (ClassNotFoundException erro) {
            System.out.println("O driver Oravle não foi encontrado");
            erro.printStackTrace();

        } catch (SQLException erro) {
            System.out.println("Erro ao consultar as estacoes.");
            erro.printStackTrace();

        } catch (IllegalArgumentException erro) {
            System.out.println(erro.getMessage());
        }
    }
}
