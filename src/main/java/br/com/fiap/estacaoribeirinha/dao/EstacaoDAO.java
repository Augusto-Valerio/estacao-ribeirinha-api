package br.com.fiap.estacaoribeirinha.dao;

import br.com.fiap.estacaoribeirinha.conexoes.ConexaoFactory;
import br.com.fiap.estacaoribeirinha.entities.Estacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EstacaoDAO {
    // Insert
    public int inserir(Estacao estacao)
            throws SQLException, ClassNotFoundException {

        String sqlInserir = "INSERT INTO ESTACAO (NOME, COMUNIDADE, MUNICIPIO, UF, STATUS) VALUES (?, ?, ?, ?, ?)";

        String sqlBuscarId = "SELECT ID_ESTACAO FROM ESTACAO WHERE NOME = ? AND COMUNIDADE = ? AND MUNICIPIO = ? AND UF = ?";

        try (
                Connection conexao = new ConexaoFactory().conexao();
                PreparedStatement stmt = conexao.prepareStatement(sqlInserir)
        ) {
            stmt.setString(1, estacao.getNome());
            stmt.setString(2, estacao.getComunidade());
            stmt.setString(3, estacao.getMunicipio());
            stmt.setString(4, estacao.getUf());
            stmt.setString(5, estacao.getStatus());

            stmt.executeUpdate();

            try (PreparedStatement consulta = conexao.prepareStatement(sqlBuscarId)) {
                consulta.setString(1, estacao.getNome());
                consulta.setString(2, estacao.getComunidade());
                consulta.setString(3, estacao.getMunicipio());
                consulta.setString(4, estacao.getUf());

                try (ResultSet rs = consulta.executeQuery()) {
                    if (rs.next()) {
                        int idEstacao = rs.getInt("ID_ESTACAO");

                        if (idEstacao > 0) {
                            return idEstacao;
                        }
                    }
                }
            }
        }

        throw new SQLException("Não foi possivel recuperar o ID da estação cadastrada.");
    }

    // Select
    public ArrayList<Estacao> selecionar() throws SQLException, ClassNotFoundException {

        ArrayList<Estacao> estacoes = new ArrayList<>();

        String sql = "SELECT ID_ESTACAO, NOME, COMUNIDADE, MUNICIPIO, UF, STATUS FROM ESTACAO ORDER BY ID_ESTACAO";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                estacoes.add(mapearEstacao(rs));
            }
        }

        return estacoes;
    }

    // Buscar por ID
    public Estacao buscarPorId(int idEstacao) throws SQLException, ClassNotFoundException {

        String sql = "SELECT ID_ESTACAO, NOME, COMUNIDADE, MUNICIPIO, UF, STATUS  FROM ESTACAO  WHERE ID_ESTACAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idEstacao);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearEstacao(rs);
                }
            }
        }

        return null;
    }

    // Update
    public boolean atualizar(Estacao estacao) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE ESTACAO SET NOME = ?, COMUNIDADE = ?, MUNICIPIO = ?, UF = ?, STATUS = ? WHERE ID_ESTACAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, estacao.getNome());
            stmt.setString(2, estacao.getComunidade());
            stmt.setString(3, estacao.getMunicipio());
            stmt.setString(4, estacao.getUf());
            stmt.setString(5, estacao.getStatus());
            stmt.setInt(6, estacao.getIdEstacao());

            return stmt.executeUpdate() > 0;
        }
    }

    // Delete
    public boolean deletar(int idEstacao) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM ESTACAO WHERE ID_ESTACAO = ?";

        try (Connection conexao = new ConexaoFactory().conexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idEstacao);

            return stmt.executeUpdate() > 0;
        }
    }

    private Estacao mapearEstacao(ResultSet rs) throws SQLException {

        return new Estacao(
                rs.getInt("ID_ESTACAO"),
                rs.getString("NOME"),
                rs.getString("COMUNIDADE"),
                rs.getString("MUNICIPIO"),
                rs.getString("UF"),
                rs.getString("STATUS")
        );
    }
}
