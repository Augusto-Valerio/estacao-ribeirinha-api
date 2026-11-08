package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.EstacaoBo;
import br.com.fiap.estacaoribeirinha.entities.Estacao;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteInserirEstacao {
    public static void main(String[] args) {

        try {
            String nome = JOptionPane.showInputDialog("Informe o nome da estação");

            String comunidade = JOptionPane.showInputDialog("Informe o nome da comunidade");

            String municipio = JOptionPane.showInputDialog("Informe o municipio");

            String uf = JOptionPane.showInputDialog("Informe a UF");

            String status = JOptionPane.showInputDialog("Informe o status: ATIVA, MANUTENCAO ou INATIVA");

            Estacao estacao = new Estacao(nome, comunidade, municipio, uf, status);

            EstacaoBo estacaoBo = new EstacaoBo();
            int idEstacao = estacaoBo.inserirBo(estacao);

            JOptionPane.showMessageDialog(
                    null,
                    "Estação cadastrada com sucesso!\n" +
                            "ID gerado: " + idEstacao
            );

        } catch (IllegalArgumentException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    erro.getMessage(),
                    "Dados invalidos",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao cadastrar a estação no banco de dados.",
                    "Erro de banco",
                    JOptionPane.ERROR_MESSAGE
            );
            erro.printStackTrace();

        } catch (ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    "O driver Oracle não foi encontrado.",
                    "Erro de conexão",
                    JOptionPane.ERROR_MESSAGE
            );
            erro.printStackTrace();
        }
    }
}
