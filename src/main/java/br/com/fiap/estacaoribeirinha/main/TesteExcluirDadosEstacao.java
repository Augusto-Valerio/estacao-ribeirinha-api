package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.AvisoBo;
import br.com.fiap.estacaoribeirinha.bo.EstacaoBo;
import br.com.fiap.estacaoribeirinha.bo.LeituraBo;
import br.com.fiap.estacaoribeirinha.entities.Aviso;
import br.com.fiap.estacaoribeirinha.entities.Estacao;
import br.com.fiap.estacaoribeirinha.entities.Leitura;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TesteExcluirDadosEstacao {

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        EstacaoBo estacaoBo = new EstacaoBo();
        LeituraBo leituraBo = new LeituraBo();
        AvisoBo avisoBo = new AvisoBo();

        int idEstacao = inteiro("Informe o ID da estação de teste que será excluida");

        Estacao estacao = estacaoBo.buscarPorIdBo(idEstacao);

        int confirmacao = JOptionPane.showConfirmDialog(
                null,
                "Deseja excluir a estação abaixo e todos os dados vinculados?\n\n" + estacao,
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    null,
                    "Exclusão cancelada."
            );
            return;
        }

        ArrayList<Aviso> avisos = avisoBo.buscarPorIdEstacaoBo(idEstacao);

        for (Aviso aviso : avisos) {
            avisoBo.deletarBo(aviso.getIdAviso());
        }

        ArrayList<Leitura> leituras = leituraBo.buscarPorIdEstacaoBo(idEstacao);

        for (Leitura leitura : leituras) {
            leituraBo.deletarBo(leitura.getIdLeitura());
        }

        estacaoBo.deletarBo(idEstacao);

        System.out.println("Estação excluida: " + idEstacao);

        System.out.println("Avisos excluidos: " + avisos.size());

        System.out.println("Leituras excluidas: " + leituras.size());

        JOptionPane.showMessageDialog(
                null,
                "Dados excluidos com sucesso!\n" +
                        "Avisos excluidos: " +
                        avisos.size() + "\n" +
                        "Leituras excluidas: " +
                        leituras.size() + "\n" +
                        "Estacao excluida: " +
                        idEstacao
        );
    }
}
