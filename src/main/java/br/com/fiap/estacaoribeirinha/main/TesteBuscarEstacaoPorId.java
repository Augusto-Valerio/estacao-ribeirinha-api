package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.EstacaoBo;
import br.com.fiap.estacaoribeirinha.entities.Estacao;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class TesteBuscarEstacaoPorId {

    public static void main(String[] args) {

        try {
            int idEstacao = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID da estação"));

            EstacaoBo estacaoBo = new EstacaoBo();
            Estacao estacao = estacaoBo.buscarPorIdBo(idEstacao);

            JOptionPane.showMessageDialog(
                    null,
                    "Estação encontrada:\n\n" + estacao
            );

        } catch (IllegalArgumentException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    erro.getMessage(),
                    "Dados inválidos",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (NoSuchElementException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    erro.getMessage(),
                    "Estação não encontrada",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao consultar a estação.",
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
