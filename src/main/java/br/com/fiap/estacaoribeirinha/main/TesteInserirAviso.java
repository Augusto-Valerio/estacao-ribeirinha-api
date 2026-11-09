package br.com.fiap.estacaoribeirinha.main;

import br.com.fiap.estacaoribeirinha.bo.AvisoBo;
import br.com.fiap.estacaoribeirinha.entities.Aviso;

import javax.swing.JOptionPane;
import java.sql.SQLException;

public class TesteInserirAviso {

    // String
    static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    // Inteiro
    static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Aviso aviso = new Aviso();
        AvisoBo avisoBo = new AvisoBo();

        aviso.setIdEstacao(inteiro("Informe o ID da estação"));

        aviso.setTitulo(texto("Informe o titulo do aviso"));

        aviso.setMensagem(texto("Informe a mensagem do aviso"));

        aviso.setNivel(texto("Informe o nivel: INFORMATIVO, ATENCAO ou CRITICO"));

        aviso.setStatus(texto("Informe o status: ATIVO ou ENCERRADO"));

        int idAviso = avisoBo.inserirBo(aviso);

        System.out.println("ID do aviso cadastrado: " + idAviso);

        JOptionPane.showMessageDialog(
                null,
                "Aviso cadastrado com sucesso!\n" +
                        "ID gerado: " + idAviso
        );
    }
}
