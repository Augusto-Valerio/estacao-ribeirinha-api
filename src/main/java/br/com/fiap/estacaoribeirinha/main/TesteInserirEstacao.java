package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.EstacaoBo;
import br.com.fiap.estacaoribeirinha.entities.Estacao;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteInserirEstacao {

    // String
    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Estacao estacao = new Estacao();
        EstacaoBo estacaoBo = new EstacaoBo();

        estacao.setNome(texto("Informe o nome da estação"));

        estacao.setComunidade(texto("Informe o nome da comunidade"));

        estacao.setMunicipio(texto("Informe o município"));

        estacao.setUf(texto("Informe a UF"));

        estacao.setStatus(texto("Informe o status: ATIVA, MANUTENCAO ou INATIVA"));

        int idEstacao = estacaoBo.inserirBo(estacao);

        System.out.println("ID da estação cadastrada: " + idEstacao);

        JOptionPane.showMessageDialog(
                null,
                "Estação cadastrada com sucesso!\n" +
                        "ID gerado: " + idEstacao
        );
    }
}
