package br.com.fiap.estacaoribeirinha.bo;

import br.com.fiap.estacaoribeirinha.dao.AvisoDAO;
import br.com.fiap.estacaoribeirinha.dao.EstacaoDAO;
import br.com.fiap.estacaoribeirinha.dao.LeituraDAO;
import br.com.fiap.estacaoribeirinha.entities.Estacao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class EstacaoBo {

    // Select
    public ArrayList<Estacao> selecionarBo() throws SQLException, ClassNotFoundException {
        EstacaoDAO estacaoDAO = new EstacaoDAO();

        return estacaoDAO.selecionar();
    }

    // Buscar por ID
    public Estacao buscarPorIdBo(int idEstacao) throws SQLException, ClassNotFoundException {
        EstacaoDAO estacaoDAO = new EstacaoDAO();

        validarId(idEstacao);

        Estacao estacao = estacaoDAO.buscarPorId(idEstacao);

        if (estacao == null) {
            throw new NoSuchElementException("Estação não encontrada.");
        }

        return estacao;
    }

    // Insert
    public int inserirBo(Estacao estacao) throws SQLException, ClassNotFoundException {
        EstacaoDAO estacaoDAO = new EstacaoDAO();

        normalizarEstacao(estacao);
        validarEstacao(estacao);

        return estacaoDAO.inserir(estacao);
    }

    // Update
    public boolean atualizarBo(Estacao estacao) throws SQLException, ClassNotFoundException {

        EstacaoDAO estacaoDAO = new EstacaoDAO();

        if (estacao == null) {
            throw new IllegalArgumentException("Os dados da estação são obrigatórios.");
        }

        validarId(estacao.getIdEstacao());

        normalizarEstacao(estacao);
        validarEstacao(estacao);

        Estacao estacaoCadastrada = estacaoDAO.buscarPorId(estacao.getIdEstacao());

        if (estacaoCadastrada == null) {
            throw new NoSuchElementException("Estação não encontrada.");
        }

        return estacaoDAO.atualizar(estacao);
    }

    // Delete
    public boolean deletarBo(int idEstacao) throws SQLException, ClassNotFoundException {
        EstacaoDAO estacaoDAO = new EstacaoDAO();
        LeituraDAO leituraDAO = new LeituraDAO();
        AvisoDAO avisoDAO = new AvisoDAO();

        validarId(idEstacao);

        Estacao estacao = estacaoDAO.buscarPorId(idEstacao);

        if (estacao == null) {
            throw new NoSuchElementException("Estação não encontrada.");
        }

        boolean possuiLeituras = !leituraDAO.buscarPorIdEstacao(idEstacao).isEmpty();

        boolean possuiAvisos = !avisoDAO.buscarPorIdEstacao(idEstacao).isEmpty();

        if (possuiLeituras || possuiAvisos) {
            throw new IllegalArgumentException("A estação não pode ser excluida enquanto possuir leituras ou avisos vinculados.");
        }

        return estacaoDAO.deletar(idEstacao);
    }

    private void validarId(int idEstacao) {
        if (idEstacao <= 0) {
            throw new IllegalArgumentException("O ID da estação deve ser maior que zero.");
        }
    }

    private void validarEstacao(Estacao estacao) {
        if (estacao == null) {
            throw new IllegalArgumentException("Os dados da estação são obrigatórios.");
        }

        validarNome(estacao.getNome());
        validarComunidade(estacao.getComunidade());
        validarMunicipio(estacao.getMunicipio());
        validarUf(estacao.getUf());
        validarStatus(estacao.getStatus());
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da estação é obrigatório.");
        }

        if (nome.length() > 80) {
            throw new IllegalArgumentException("O nome da estação deve possuir no máximo 80 caracteres.");
        }
    }

    private void validarComunidade(String comunidade) {
        if (comunidade == null || comunidade.isBlank()) {
            throw new IllegalArgumentException("O nome da comunidade e obrigatório.");
        }

        if (comunidade.length() > 100) {
            throw new IllegalArgumentException("A comunidade deve possuir no máximo 100 caracteres.");
        }
    }

    private void validarMunicipio(String municipio) {
        if (municipio == null || municipio.isBlank()) {
            throw new IllegalArgumentException("O municipio e obrigatório.");
        }

        if (municipio.length() > 100) {
            throw new IllegalArgumentException("O municipio deve possuir no máximo 100 caracteres.");
        }
    }

    private void validarUf(String uf) {
        if (uf == null || uf.isBlank()) {
            throw new IllegalArgumentException("A UF e obrigatória.");
        }

        if (!uf.matches("[A-Z]{2}")) {
            throw new IllegalArgumentException("A UF deve possuir exatamente duas letras.");
        }
    }

    private void validarStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("O status da estação é obrigatório.");
        }

        boolean statusValido = status.equals("ATIVA") || status.equals("MANUTENCAO") || status.equals("INATIVA");

        if (!statusValido) {
            throw new IllegalArgumentException("O status deve ser ATIVA, MANUTENCAO ou INATIVA.");
        }
    }

    private void normalizarEstacao(Estacao estacao) {
        if (estacao == null) {
            return;
        }

        if (estacao.getNome() != null) {
            estacao.setNome(estacao.getNome().trim());
        }

        if (estacao.getComunidade() != null) {
            estacao.setComunidade(estacao.getComunidade().trim());
        }

        if (estacao.getMunicipio() != null) {
            estacao.setMunicipio(estacao.getMunicipio().trim());
        }

        if (estacao.getUf() != null) {
            estacao.setUf(estacao.getUf().trim().toUpperCase());
        }

        if (estacao.getStatus() != null) {
            estacao.setStatus(estacao.getStatus().trim().toUpperCase());
        }
    }
}
