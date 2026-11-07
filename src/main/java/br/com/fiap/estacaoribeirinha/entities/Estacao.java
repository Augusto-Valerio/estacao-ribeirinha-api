package br.com.fiap.estacaoribeirinha.entities;

public class Estacao {

    private int idEstacao;
    private String nome;
    private String comunidade;
    private String municipio;
    private String uf;
    private String status;

    public Estacao() {
    }

    public Estacao(String nome, String comunidade, String municipio, String uf, String status) {
        this.nome = nome;
        this.comunidade = comunidade;
        this.municipio = municipio;
        this.uf = uf;
        this.status = status;
    }

    public Estacao(int idEstacao, String nome, String comunidade, String municipio, String uf, String status) {
        this.idEstacao = idEstacao;
        this.nome = nome;
        this.comunidade = comunidade;
        this.municipio = municipio;
        this.uf = uf;
        this.status = status;
    }

    public int getIdEstacao() {
        return idEstacao;
    }

    public void setIdEstacao(int idEstacao) {
        this.idEstacao = idEstacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComunidade() {
        return comunidade;
    }

    public void setComunidade(String comunidade) {
        this.comunidade = comunidade;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Estacao: " +
                "\nidEstacao=" + idEstacao +
                "\nnome='" + nome + '\'' +
                "\ncomunidade='" + comunidade + '\'' +
                "\nmunicipio='" + municipio + '\'' +
                "\nuf='" + uf + '\'' +
                "\nstatus='" + status + '\'';
    }
}
