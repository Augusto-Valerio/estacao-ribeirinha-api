package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.dao.AvisoDAO;
import br.com.fiap.estacaoribeirinha.dao.EstacaoDAO;
import br.com.fiap.estacaoribeirinha.dao.LeituraDAO;
import br.com.fiap.estacaoribeirinha.entities.Aviso;
import br.com.fiap.estacaoribeirinha.entities.Estacao;
import br.com.fiap.estacaoribeirinha.entities.Leitura;

import java.sql.SQLException;
import java.util.ArrayList;

public class TesteConsultarDadosEstacao {
    public static void main(String[] args) {
        try {
            EstacaoDAO estacaoDAO = new EstacaoDAO();
            LeituraDAO leituraDAO = new LeituraDAO();
            AvisoDAO avisoDAO = new AvisoDAO();

            ArrayList<Estacao> estacaos = estacaoDAO.selecionar();

            if (estacaos.isEmpty()) {
                System.out.println("Nenhuma estação encontrada");
                return;
            }

            Estacao estacao = estacaos.getFirst();

            System.out.println("Estação consultada:");
            System.out.println(estacao);

            ArrayList<Leitura> leituras = leituraDAO.buscarPorIdEstacao(estacao.getIdEstacao());

            System.out.println();
            System.out.println("Quantidade de leituras: " + leituras.size());

            for (Leitura leitura : leituras) {
                System.out.println();
                System.out.println(leitura);
            }

            Leitura ultimaLeitura = leituraDAO.buscarUltimaPorIdEstacao(estacao.getIdEstacao());
            System.out.println();
            System.out.println("Ultima leitura:");
            System.out.println(ultimaLeitura);

            ArrayList<Aviso> avisos = avisoDAO.buscarPorIdEstacao(estacao.getIdEstacao());

            System.out.println();
            System.out.println("Quantidade de avisos: " + avisos.size());

            for (Aviso aviso : avisos) {
                System.out.println();
                System.out.println(aviso);
            }

        } catch (ClassNotFoundException erro) {
            System.out.println("O driver Oracle não foi encontrado.");
            erro.printStackTrace();

        } catch (SQLException erro) {
            System.out.println("Erro ao consultar os dados da estação");
            erro.printStackTrace();

        } catch (IllegalStateException erro) {
            System.out.println(erro.getMessage());
        }
    }
}
