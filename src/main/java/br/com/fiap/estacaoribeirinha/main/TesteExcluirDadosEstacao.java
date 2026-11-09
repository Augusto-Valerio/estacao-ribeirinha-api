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

    public static void main(String[] args) {

        try {
            int idEstacao = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID da estação de teste que será excluida"));

            EstacaoBo estacaoBo = new EstacaoBo();
            LeituraBo leituraBo = new LeituraBo();
            AvisoBo avisoBo = new AvisoBo();

            Estacao estacao = estacaoBo.buscarPorIdBo(idEstacao);

            int confirmacao = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja excluir a estacao abaixo e todos os dados vinculados?\n\n" + estacao,
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

            JOptionPane.showMessageDialog(
                    null,
                    "Dados excluidos com sucesso!\n" +
                            "Avisos excluidos: " + avisos.size() + "\n" +
                            "Leituras excluidas: " + leituras.size() + "\n" +
                            "Estação excluida: " + idEstacao
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
                    "Registro não encontrado",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao excluir os dados da estação.",
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
