package br.com.fiap.estacaoribeirinha.dao;

import br.com.fiap.estacaoribeirinha.conexoes.ConexaoFactory;
import br.com.fiap.estacaoribeirinha.entities.Leitura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class LeituraDAO {

    // Insert
    public int inserir(Leitura leitura) throws SQLException, ClassNotFoundException {

        String sqlInserir = "INSERT INTO LEITURA (ID_ESTACAO, NIVEL_AGUA_CM, PERCENTUAL_BATERIA, DATA_HORA, TENDENCIA) VALUES (?, ?, ?, ?, ?)";

        String sqlBuscarId = "SELECT MAX(ID_LEITURA) AS ID_LEITURA FROM LEITURA WHERE ID_ESTACAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sqlInserir)) {
            stmt.setInt(1, leitura.getIdEstacao());
            stmt.setDouble(2, leitura.getNivelAguaCm());
            stmt.setInt(3, leitura.getPercentualBateria());
            stmt.setTimestamp(4, Timestamp.valueOf(leitura.getDataHora()));
            stmt.setString(5, leitura.getTendencia());

            stmt.executeUpdate();

            try (PreparedStatement consulta = conexao.prepareStatement(sqlBuscarId)) {
                consulta.setInt(1, leitura.getIdEstacao());

                try (ResultSet rs = consulta.executeQuery()) {
                    if (rs.next()) {
                        int idLeitura = rs.getInt("ID_LEITURA");

                        if (idLeitura > 0) {
                            return idLeitura;
                        }
                    }
                }
            }
        }

        throw new SQLException("Não foi possivel recuperar o ID da leitura cadastrada.");
    }

    // Select
    public ArrayList<Leitura> selecionar() throws SQLException, ClassNotFoundException {

        ArrayList<Leitura> leituras = new ArrayList<>();

        String sql = "SELECT ID_LEITURA, ID_ESTACAO, NIVEL_AGUA_CM, PERCENTUAL_BATERIA, DATA_HORA, TENDENCIA FROM LEITURA ORDER BY DATA_HORA DESC, ID_LEITURA DESC";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                leituras.add(mapearLeitura(rs));
            }
        }

        return leituras;
    }

    // Buscar por ID
    public Leitura buscarPorId(int idLeitura) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ID_LEITURA, ID_ESTACAO, NIVEL_AGUA_CM, PERCENTUAL_BATERIA, DATA_HORA, TENDENCIA FROM LEITURA WHERE ID_LEITURA = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idLeitura);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearLeitura(rs);
                }
            }
        }

        return null;
    }

    // Buscar leituras da estacao
    public ArrayList<Leitura> buscarPorIdEstacao(int idEstacao) throws SQLException, ClassNotFoundException {

        ArrayList<Leitura> leituras = new ArrayList<>();

        String sql = "SELECT ID_LEITURA, ID_ESTACAO, NIVEL_AGUA_CM, PERCENTUAL_BATERIA, DATA_HORA, TENDENCIA FROM LEITURA WHERE ID_ESTACAO = ? ORDER BY DATA_HORA DESC, ID_LEITURA DESC";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idEstacao);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    leituras.add(mapearLeitura(rs));
                }
            }
        }

        return leituras;
    }

    // Buscar a ultima leitura da estacao
    public Leitura buscarUltimaPorIdEstacao(int idEstacao) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ID_LEITURA, ID_ESTACAO, NIVEL_AGUA_CM, PERCENTUAL_BATERIA, DATA_HORA, TENDENCIA " +
                "FROM (" +
                "    SELECT ID_LEITURA, ID_ESTACAO, NIVEL_AGUA_CM, " +
                "    PERCENTUAL_BATERIA, DATA_HORA, TENDENCIA " +
                "    FROM LEITURA " +
                "    WHERE ID_ESTACAO = ? " +
                "    ORDER BY DATA_HORA DESC, ID_LEITURA DESC" +
                ") WHERE ROWNUM = 1";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idEstacao);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearLeitura(rs);
                }
            }
        }

        return null;
    }

    // Update
    public boolean atualizar(Leitura leitura) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE LEITURA SET ID_ESTACAO = ?, NIVEL_AGUA_CM = ?, PERCENTUAL_BATERIA = ?, DATA_HORA = ?, TENDENCIA = ? WHERE ID_LEITURA = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, leitura.getIdEstacao());
            stmt.setDouble(2, leitura.getNivelAguaCm());
            stmt.setInt(3, leitura.getPercentualBateria());
            stmt.setTimestamp(4, Timestamp.valueOf(leitura.getDataHora()));
            stmt.setString(5, leitura.getTendencia());
            stmt.setInt(6, leitura.getIdLeitura());

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deletar(int idLeitura) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM LEITURA WHERE ID_LEITURA = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idLeitura);

            return stmt.executeUpdate() > 0;
        }
    }

    private Leitura mapearLeitura(ResultSet rs) throws SQLException {

        return new Leitura(
                rs.getInt("ID_LEITURA"),
                rs.getInt("ID_ESTACAO"),
                rs.getDouble("NIVEL_AGUA_CM"),
                rs.getInt("PERCENTUAL_BATERIA"),
                rs.getTimestamp("DATA_HORA").toLocalDateTime(),
                rs.getString("TENDENCIA")
        );
    }
}
