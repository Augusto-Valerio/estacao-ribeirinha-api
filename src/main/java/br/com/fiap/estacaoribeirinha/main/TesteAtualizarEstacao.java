package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.EstacaoBo;
import br.com.fiap.estacaoribeirinha.entities.Estacao;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class TesteAtualizarEstacao {

    public static void main(String[] args) {

        try {
            int idEstacao = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID da estação"));

            EstacaoBo estacaoBo = new EstacaoBo();

            Estacao estacao = estacaoBo.buscarPorIdBo(idEstacao);

            String nome = JOptionPane.showInputDialog(
                    null,
                    "Informe o nome da estação",
                    estacao.getNome()
            );

            String comunidade = JOptionPane.showInputDialog(
                    null,
                    "Informe a comunidade",
                    estacao.getComunidade()
            );

            String municipio = JOptionPane.showInputDialog(
                    null,
                    "Informe o municipio",
                    estacao.getMunicipio()
            );

            String uf = JOptionPane.showInputDialog(
                    null,
                    "Informe a UF",
                    estacao.getUf()
            );

            String status = JOptionPane.showInputDialog(
                    null,
                    "Informe o status: ATIVA, MANUTENCAO ou INATIVA",
                    estacao.getStatus()
            );

            estacao.setNome(nome);
            estacao.setComunidade(comunidade);
            estacao.setMunicipio(municipio);
            estacao.setUf(uf);
            estacao.setStatus(status);

            estacaoBo.atualizarBo(estacao);

            Estacao estacaoAtualizada = estacaoBo.buscarPorIdBo(idEstacao);

            JOptionPane.showMessageDialog(
                    null,
                    "Estação atualizada com sucesso!\n\n" +
                            estacaoAtualizada
            );

        } catch (IllegalArgumentException erro) {
            JOptionPane.showMessageDialog(
                    null,
                    erro.getMessage(),
                    "Dados invalidos",
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
                    "Erro ao atualizar a estação.",
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
