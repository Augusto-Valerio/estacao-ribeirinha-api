package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.EstacaoBo;
import br.com.fiap.estacaoribeirinha.entities.Estacao;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteBuscarEstacaoPorId {

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        EstacaoBo estacaoBo = new EstacaoBo();

        int idEstacao = inteiro("Informe o ID da estação");

        Estacao estacao = estacaoBo.buscarPorIdBo(idEstacao);

        System.out.println(estacao);

        JOptionPane.showMessageDialog(
                null,
                "Estação encontrada:\n\n" + estacao
        );
    }
}
