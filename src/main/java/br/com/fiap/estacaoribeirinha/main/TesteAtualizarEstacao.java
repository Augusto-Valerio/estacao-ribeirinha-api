package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.EstacaoBo;
import br.com.fiap.estacaoribeirinha.entities.Estacao;

import javax.swing.*;
import java.sql.SQLException;

public class TesteAtualizarEstacao {

    // String
    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Estacao estacao = new Estacao();
        EstacaoBo estacaoBo = new EstacaoBo();

        estacao.setIdEstacao(inteiro("Informe o ID da estação"));

        estacao.setNome(texto("Informe o novo nome da estação"));

        estacao.setComunidade(texto("Informe o novo nome da comunidade"));

        estacao.setMunicipio(texto("Informe o novo municipio"));

        estacao.setUf(texto("Informe a nova UF"));

        estacao.setStatus(texto("Informe o novo status: ATIVA, MANUTENCAO ou INATIVA"));

        estacaoBo.atualizarBo(estacao);

        Estacao estacaoAtualizada = estacaoBo.buscarPorIdBo(estacao.getIdEstacao());

        System.out.println(estacaoAtualizada);

        JOptionPane.showMessageDialog(
                null,
                "Estação atualizada com sucesso!\n\n" +
                        estacaoAtualizada
        );
    }
}
