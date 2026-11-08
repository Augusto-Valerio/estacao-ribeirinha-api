package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.LeituraBo;
import br.com.fiap.estacaoribeirinha.entities.Leitura;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteInserirLeitura {
    public static void main(String[] args) {
        try {
            int idEstacao = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID da estação"));

            double nivelAgua = Double.parseDouble(JOptionPane.showInputDialog("Informe o nivel da agua em centimetros"));

            int percentualBateria = Integer.parseInt(JOptionPane.showInputDialog("Informe o percentual da bateria"));

            Leitura leitura = new Leitura(idEstacao, nivelAgua, percentualBateria, null, null);

            LeituraBo leituraBo = new LeituraBo();
            int idLeitura = leituraBo.inserirBo(leitura);

            Leitura leituraCadastrada = leituraBo.buscarPorIdBo(idLeitura);

            JOptionPane.showMessageDialog(
                    null,
                    "Leitura cadastrada com sucesso!\n" +
                            "ID gerado: " + idLeitura + "\n" +
                            "Tendencia calculada: " +
                            leituraCadastrada.getTendencia()
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
                    "Erro ao cadastrar a leitura no banco de dados.",
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
