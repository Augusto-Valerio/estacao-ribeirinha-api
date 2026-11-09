package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.LeituraBo;
import br.com.fiap.estacaoribeirinha.entities.Leitura;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteInserirLeitura {

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    // Decimal
    static double decimal(String mensagem) {
        return Double.parseDouble(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Leitura leitura = new Leitura();
        LeituraBo leituraBo = new LeituraBo();

        leitura.setIdEstacao(inteiro("Informe o ID da estação"));

        leitura.setNivelAguaCm(decimal("Informe o nível da água em centimetros"));

        leitura.setPercentualBateria(inteiro("Informe o percentual da bateria"));

        int idLeitura = leituraBo.inserirBo(leitura);

        Leitura leituraCadastrada = leituraBo.buscarPorIdBo(idLeitura);

        System.out.println("ID da leitura cadastrada: " + idLeitura);

        System.out.println("Tendencia calculada: " + leituraCadastrada.getTendencia());

        JOptionPane.showMessageDialog(
                null,
                "Leitura cadastrada com sucesso!\n" +
                        "ID gerado: " + idLeitura + "\n" +
                        "Tendencia calculada: " +
                        leituraCadastrada.getTendencia()
        );
    }
}
