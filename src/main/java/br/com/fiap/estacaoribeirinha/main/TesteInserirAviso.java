package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.AvisoBo;
import br.com.fiap.estacaoribeirinha.entities.Aviso;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteInserirAviso {
    public static void main(String[] args) {

        try {
            int idEstacao = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID da estação"));

            String titulo = JOptionPane.showInputDialog("Informe o titulo do aviso");

            String mensagem = JOptionPane.showInputDialog("Informe a mensagem do aviso");

            String nivel = JOptionPane.showInputDialog("Informe o nivel: INFORMATIVO, ATENCAO ou CRITICO");

            String status = JOptionPane.showInputDialog("Informe o status: ATIVO ou ENCERRADO");

            Aviso aviso = new Aviso(idEstacao, titulo, mensagem, nivel, null, status);

            AvisoBo avisoBo = new AvisoBo();
            int idAviso = avisoBo.inserirBo(aviso);

            JOptionPane.showMessageDialog(
                    null,
                    "Aviso cadastrado com sucesso!\n" +
                            "ID gerado: " + idAviso
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
                    "Erro ao cadastrar o aviso no banco de dados.",
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
