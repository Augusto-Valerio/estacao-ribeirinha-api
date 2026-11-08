package br.com.fiap.estacaoribeirinha.dao;

import br.com.fiap.estacaoribeirinha.conexoes.ConexaoFactory;
import br.com.fiap.estacaoribeirinha.entities.Aviso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AvisoDAO {
    // Insert
    public int inserir(Aviso aviso)
            throws SQLException, ClassNotFoundException {

        String sqlInserir = "INSERT INTO AVISO (ID_ESTACAO, TITULO, MENSAGEM, NIVEL, DATA_HORA, STATUS) VALUES (?, ?, ?, ?, ?, ?)";

        String sqlBuscarId = "SELECT MAX(ID_AVISO) AS ID_AVISO FROM AVISO WHERE ID_ESTACAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sqlInserir)) {
            stmt.setInt(1, aviso.getIdEstacao());
            stmt.setString(2, aviso.getTitulo());
            stmt.setString(3, aviso.getMensagem());
            stmt.setString(4, aviso.getNivel());
            stmt.setTimestamp(5, Timestamp.valueOf(aviso.getDataHora()));
            stmt.setString(6, aviso.getStatus());

            stmt.executeUpdate();

            try (PreparedStatement consulta = conexao.prepareStatement(sqlBuscarId)) {
                consulta.setInt(1, aviso.getIdEstacao());

                try (ResultSet rs = consulta.executeQuery()) {
                    if (rs.next()) {
                        int idAviso = rs.getInt("ID_AVISO");

                        if (idAviso > 0) {
                            return idAviso;
                        }
                    }
                }
            }
        }

        throw new SQLException("Nao foi possivel recuperar o ID do aviso cadastrado.");
    }

    // Select
    public ArrayList<Aviso> selecionar() throws SQLException, ClassNotFoundException {

        ArrayList<Aviso> avisos = new ArrayList<>();

        String sql = "SELECT ID_AVISO, ID_ESTACAO, TITULO, MENSAGEM, NIVEL, DATA_HORA, STATUS FROM AVISO ORDER BY DATA_HORA DESC, ID_AVISO DESC";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                avisos.add(mapearAviso(rs));
            }
        }

        return avisos;
    }

    // Buscar por ID
    public Aviso buscarPorId(int idAviso) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ID_AVISO, ID_ESTACAO, TITULO, MENSAGEM, NIVEL, DATA_HORA, STATUS FROM AVISO WHERE ID_AVISO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idAviso);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAviso(rs);
                }
            }
        }

        return null;
    }

    // Buscar avisos da estacao
    public ArrayList<Aviso> buscarPorIdEstacao(int idEstacao) throws SQLException, ClassNotFoundException {

        ArrayList<Aviso> avisos = new ArrayList<>();

        String sql = "SELECT ID_AVISO, ID_ESTACAO, TITULO, MENSAGEM, NIVEL, DATA_HORA, STATUS " +
                "FROM AVISO " +
                "WHERE ID_ESTACAO = ? " +
                "ORDER BY DATA_HORA DESC, ID_AVISO DESC";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idEstacao);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    avisos.add(mapearAviso(rs));
                }
            }
        }

        return avisos;
    }

    // Update
    public boolean atualizar(Aviso aviso) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE AVISO SET ID_ESTACAO = ?, TITULO = ?, MENSAGEM = ?, NIVEL = ?, DATA_HORA = ?, STATUS = ? WHERE ID_AVISO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, aviso.getIdEstacao());
            stmt.setString(2, aviso.getTitulo());
            stmt.setString(3, aviso.getMensagem());
            stmt.setString(4, aviso.getNivel());
            stmt.setTimestamp(5, Timestamp.valueOf(aviso.getDataHora()));
            stmt.setString(6, aviso.getStatus());
            stmt.setInt(7, aviso.getIdAviso());

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deletar(int idAviso) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM AVISO WHERE ID_AVISO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idAviso);

            return stmt.executeUpdate() > 0;
        }
    }

    private Aviso mapearAviso(ResultSet rs) throws SQLException {

        return new Aviso(
                rs.getInt("ID_AVISO"),
                rs.getInt("ID_ESTACAO"),
                rs.getString("TITULO"),
                rs.getString("MENSAGEM"),
                rs.getString("NIVEL"),
                rs.getTimestamp("DATA_HORA").toLocalDateTime(),
                rs.getString("STATUS")
        );
    }
}
