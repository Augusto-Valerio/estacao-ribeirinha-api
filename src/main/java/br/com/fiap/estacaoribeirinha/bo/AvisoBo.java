package br.com.fiap.estacaoribeirinha.bo;

import br.com.fiap.estacaoribeirinha.dao.AvisoDAO;
import br.com.fiap.estacaoribeirinha.dao.EstacaoDAO;
import br.com.fiap.estacaoribeirinha.entities.Aviso;
import br.com.fiap.estacaoribeirinha.entities.Estacao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class AvisoBo {

    // Select
    public ArrayList<Aviso> selecionarBo() throws SQLException, ClassNotFoundException {
        AvisoDAO avisoDAO = new AvisoDAO();

        return avisoDAO.selecionar();
    }

    // Buscar por ID
    public Aviso buscarPorIdBo(int idAviso) throws SQLException, ClassNotFoundException {
        AvisoDAO avisoDAO = new AvisoDAO();

        validarIdAviso(idAviso);

        Aviso aviso = avisoDAO.buscarPorId(idAviso);

        if (aviso == null) {
            throw new NoSuchElementException("Aviso não encontrado.");
        }

        return aviso;
    }

    // Buscar avisos da estacao
    public ArrayList<Aviso> buscarPorIdEstacaoBo(int idEstacao) throws SQLException, ClassNotFoundException {
        AvisoDAO avisoDAO = new AvisoDAO();

        validarExistenciaEstacao(idEstacao);

        return avisoDAO.buscarPorIdEstacao(idEstacao);
    }

    // Insert
    public int inserirBo(Aviso aviso) throws SQLException, ClassNotFoundException {
        AvisoDAO avisoDAO = new AvisoDAO();

        if (aviso == null) {
            throw new IllegalArgumentException("Os dados do aviso são obrigatórios.");
        }

        if (aviso.getDataHora() == null) {
            aviso.setDataHora(LocalDateTime.now());
        }

        normalizarAviso(aviso);
        validarAviso(aviso);
        validarExistenciaEstacao(aviso.getIdEstacao());

        return avisoDAO.inserir(aviso);
    }

    // Update
    public boolean atualizarBo(Aviso aviso) throws SQLException, ClassNotFoundException {
        AvisoDAO avisoDAO = new AvisoDAO();

        if (aviso == null) {
            throw new IllegalArgumentException("Os dados do aviso são obrigatórios.");
        }

        validarIdAviso(aviso.getIdAviso());

        normalizarAviso(aviso);
        validarAviso(aviso);

        Aviso avisoCadastrado = avisoDAO.buscarPorId(aviso.getIdAviso());

        if (avisoCadastrado == null) {
            throw new NoSuchElementException("Aviso não encontrado.");
        }

        validarExistenciaEstacao(aviso.getIdEstacao());

        return avisoDAO.atualizar(aviso);
    }

    // Delete
    public boolean deletarBo(int idAviso) throws SQLException, ClassNotFoundException {
        AvisoDAO avisoDAO = new AvisoDAO();

        validarIdAviso(idAviso);

        Aviso aviso = avisoDAO.buscarPorId(idAviso);

        if (aviso == null) {
            throw new NoSuchElementException("Aviso não encontrado.");
        }

        return avisoDAO.deletar(idAviso);
    }

    private void validarIdAviso(int idAviso) {
        if (idAviso <= 0) {
            throw new IllegalArgumentException("O ID do aviso deve ser maior que zero.");
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

    private void validarAviso(Aviso aviso) {
        if (aviso.getIdEstacao() <= 0) {
            throw new IllegalArgumentException("O ID da estação deve ser maior que zero.");
        }

        validarTitulo(aviso.getTitulo());
        validarMensagem(aviso.getMensagem());
        validarNivel(aviso.getNivel());

        if (aviso.getDataHora() == null) {
            throw new IllegalArgumentException("A data e a hora do aviso são obrigatórias.");
        }

        if (aviso.getDataHora().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data do aviso não pode estar no futuro.");
        }

        validarStatus(aviso.getStatus());
    }

    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O titulo do aviso é obrigatório.");
        }

        if (titulo.length() > 100) {
            throw new IllegalArgumentException("O titulo deve possuir no máximo 100 caracteres.");
        }
    }

    private void validarMensagem(String mensagem) {
        if (mensagem == null || mensagem.isBlank()) {
            throw new IllegalArgumentException("A mensagem do aviso é obrigatória.");
        }

        if (mensagem.length() > 500) {
            throw new IllegalArgumentException("A mensagem deve possuir no máximo 500 caracteres.");
        }
    }

    private void validarNivel(String nivel) {
        if (nivel == null || nivel.isBlank()) {
            throw new IllegalArgumentException("O nivel do aviso e obrigatório.");
        }

        boolean nivelValido = nivel.equals("INFORMATIVO") || nivel.equals("ATENCAO") || nivel.equals("CRITICO");

        if (!nivelValido) {
            throw new IllegalArgumentException("O nivel deve ser INFORMATIVO, ATENCAO ou CRITICO.");
        }
    }

    private void validarStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("O status do aviso é obrigatório.");
        }

        boolean statusValido = status.equals("ATIVO") || status.equals("ENCERRADO");

        if (!statusValido) {
            throw new IllegalArgumentException("O status deve ser ATIVO ou ENCERRADO.");
        }
    }

    private void normalizarAviso(Aviso aviso) {
        if (aviso == null) {
            return;
        }

        if (aviso.getTitulo() != null) {
            aviso.setTitulo(aviso.getTitulo().trim());
        }

        if (aviso.getMensagem() != null) {
            aviso.setMensagem(aviso.getMensagem().trim());
        }

        if (aviso.getNivel() != null) {
            aviso.setNivel(aviso.getNivel().trim().toUpperCase());
        }

        if (aviso.getStatus() != null) {
            aviso.setStatus(aviso.getStatus().trim().toUpperCase());
        }
    }
}
