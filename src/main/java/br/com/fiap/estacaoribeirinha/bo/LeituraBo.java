package br.com.fiap.estacaoribeirinha.bo;

import br.com.fiap.estacaoribeirinha.dao.EstacaoDAO;
import br.com.fiap.estacaoribeirinha.dao.LeituraDAO;
import br.com.fiap.estacaoribeirinha.entities.Estacao;
import br.com.fiap.estacaoribeirinha.entities.Leitura;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class LeituraBo {
    private static final double LIMITE_TENDENCIA_CM = 2.0;

    // Select
    public ArrayList<Leitura> selecionarBo() throws SQLException, ClassNotFoundException {
        LeituraDAO leituraDAO = new LeituraDAO();

        return leituraDAO.selecionar();
    }

    // Buscar por ID
    public Leitura buscarPorIdBo(int idLeitura) throws SQLException, ClassNotFoundException {
        LeituraDAO leituraDAO = new LeituraDAO();

        validarIdLeitura(idLeitura);

        Leitura leitura = leituraDAO.buscarPorId(idLeitura);

        if (leitura == null) {
            throw new NoSuchElementException("Leitura não encontrada.");
        }

        return leitura;
    }

    // Buscar leituras da estacao
    public ArrayList<Leitura> buscarPorIdEstacaoBo(int idEstacao) throws SQLException, ClassNotFoundException {
        LeituraDAO leituraDAO = new LeituraDAO();

        validarExistenciaEstacao(idEstacao);

        return leituraDAO.buscarPorIdEstacao(idEstacao);
    }

    // Buscar ultima leitura da estacao
    public Leitura buscarUltimaPorIdEstacaoBo(int idEstacao) throws SQLException, ClassNotFoundException {
        LeituraDAO leituraDAO = new LeituraDAO();

        validarExistenciaEstacao(idEstacao);

        Leitura leitura = leituraDAO.buscarUltimaPorIdEstacao(idEstacao);

        if (leitura == null) {
            throw new NoSuchElementException("A estação ainda não possui leituras.");
        }

        return leitura;
    }

    // Insert
    public int inserirBo(Leitura leitura) throws SQLException, ClassNotFoundException {
        LeituraDAO leituraDAO = new LeituraDAO();

        if (leitura == null) {
            throw new IllegalArgumentException("Os dados da leitura são obrigatórios.");
        }

        if (leitura.getDataHora() == null) {
            leitura.setDataHora(LocalDateTime.now());
        }

        validarDadosBasicos(leitura);
        validarExistenciaEstacao(leitura.getIdEstacao());

        Leitura ultimaLeitura = leituraDAO.buscarUltimaPorIdEstacao(leitura.getIdEstacao());

        leitura.setTendencia(calcularTendencia(leitura.getNivelAguaCm(), ultimaLeitura));

        return leituraDAO.inserir(leitura);
    }

    // Update
    public boolean atualizarBo(Leitura leitura) throws SQLException, ClassNotFoundException {
        LeituraDAO leituraDAO = new LeituraDAO();

        if (leitura == null) {
            throw new IllegalArgumentException("Os dados da leitura são obrigatórios.");
        }

        validarIdLeitura(leitura.getIdLeitura());

        normalizarLeitura(leitura);
        validarDadosBasicos(leitura);
        validarTendencia(leitura.getTendencia());

        Leitura leituraCadastrada = leituraDAO.buscarPorId(leitura.getIdLeitura());

        if (leituraCadastrada == null) {
            throw new NoSuchElementException("Leitura não encontrada.");
        }

        validarExistenciaEstacao(leitura.getIdEstacao());

        return leituraDAO.atualizar(leitura);
    }

    // Delete
    public boolean deletarBo(int idLeitura) throws SQLException, ClassNotFoundException {
        LeituraDAO leituraDAO = new LeituraDAO();

        validarIdLeitura(idLeitura);

        Leitura leitura = leituraDAO.buscarPorId(idLeitura);

        if (leitura == null) {
            throw new NoSuchElementException("Leitura não encontrada.");
        }

        return leituraDAO.deletar(idLeitura);
    }

    private void validarIdLeitura(int idLeitura) {
        if (idLeitura <= 0) {
            throw new IllegalArgumentException("O ID da leitura deve ser maior que zero.");
        }
    }

    private void validarExistenciaEstacao(int idEstacao) throws SQLException, ClassNotFoundException {

        if (idEstacao <= 0) {
            throw new IllegalArgumentException("O ID da estação deve ser maior que zero.");
        }

        EstacaoDAO estacaoDAO = new EstacaoDAO();
        Estacao estacao = estacaoDAO.buscarPorId(idEstacao);

        if (estacao == null) {
            throw new NoSuchElementException("A estação informada não foi encontrada.");
        }
    }

    private void validarDadosBasicos(Leitura leitura) {
        if (leitura.getIdEstacao() <= 0) {throw new IllegalArgumentException("O ID da estação deve ser maior que zero.");
        }

        double nivelAgua = leitura.getNivelAguaCm();

        if (Double.isNaN(nivelAgua) || Double.isInfinite(nivelAgua) || nivelAgua < 0) {

            throw new IllegalArgumentException("O nivel da água deve ser um valor maior ou igual a zero.");
        }

        if (leitura.getPercentualBateria() < 0 || leitura.getPercentualBateria() > 100) {

            throw new IllegalArgumentException("O percentual da bateria deve estar entre 0 e 100.");
        }

        if (leitura.getDataHora() == null) {
            throw new IllegalArgumentException("A data e a hora da leitura são obrigatórias.");
        }

        if (leitura.getDataHora().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data da leitura não pode estar no futuro.");
        }
    }

    private void validarTendencia(String tendencia) {
        if (tendencia == null || tendencia.isBlank()) {
            throw new IllegalArgumentException("A tendencia da leitura e obrigatória.");
        }

        boolean tendenciaValida = tendencia.equals("SUBINDO") || tendencia.equals("ESTAVEL") || tendencia.equals("DESCENDO");

        if (!tendenciaValida) {
            throw new IllegalArgumentException("A tendencia deve ser SUBINDO, ESTAVEL ou DESCENDO.");
        }
    }

    private String calcularTendencia(double nivelAtual, Leitura ultimaLeitura) {
        if (ultimaLeitura == null) {
            return "ESTAVEL";
        }

        double diferenca = nivelAtual - ultimaLeitura.getNivelAguaCm();

        if (diferenca > LIMITE_TENDENCIA_CM) {
            return "SUBINDO";
        }

        if (diferenca < -LIMITE_TENDENCIA_CM) {
            return "DESCENDO";
        }

        return "ESTAVEL";
    }

    private void normalizarLeitura(Leitura leitura) {
        if (leitura == null) {
            return;
        }

        if (leitura.getTendencia() != null) {
            leitura.setTendencia(leitura.getTendencia().trim().toUpperCase());
        }
    }
}
