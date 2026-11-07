package br.com.fiap.estacaoribeirinha.entities;

import java.time.LocalDateTime;

public class Leitura {

    private int idLeitura;
    private int idEstacao;
    private double nivelAguaCm;
    private int percentualBateria;
    private LocalDateTime dataHora;
    private String tendencia;

    public Leitura() {
    }

    public Leitura(int idEstacao, double nivelAguaCm, int percentualBateria, LocalDateTime dataHora, String tendencia) {
        this.idEstacao = idEstacao;
        this.nivelAguaCm = nivelAguaCm;
        this.percentualBateria = percentualBateria;
        this.dataHora = dataHora;
        this.tendencia = tendencia;
    }

    public Leitura(int idLeitura, int idEstacao, double nivelAguaCm, int percentualBateria, LocalDateTime dataHora, String tendencia) {
        this.idLeitura = idLeitura;
        this.idEstacao = idEstacao;
        this.nivelAguaCm = nivelAguaCm;
        this.percentualBateria = percentualBateria;
        this.dataHora = dataHora;
        this.tendencia = tendencia;
    }

    public int getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(int idLeitura) {
        this.idLeitura = idLeitura;
    }

    public int getIdEstacao() {
        return idEstacao;
    }

    public void setIdEstacao(int idEstacao) {
        this.idEstacao = idEstacao;
    }

    public double getNivelAguaCm() {
        return nivelAguaCm;
    }

    public void setNivelAguaCm(double nivelAguaCm) {
        this.nivelAguaCm = nivelAguaCm;
    }

    public int getPercentualBateria() {
        return percentualBateria;
    }

    public void setPercentualBateria(int percentualBateria) {
        this.percentualBateria = percentualBateria;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getTendencia() {
        return tendencia;
    }

    public void setTendencia(String tendencia) {
        this.tendencia = tendencia;
    }

    @Override
    public String toString() {
        return "Leitura: " +
                "idLeitura=" + idLeitura +
                "\nidEstacao=" + idEstacao +
                "\nnivelAguaCm=" + nivelAguaCm +
                "\npercentualBateria=" + percentualBateria +
                "\ndataHora=" + dataHora +
                "\ntendencia='" + tendencia + '\'';
    }
}
