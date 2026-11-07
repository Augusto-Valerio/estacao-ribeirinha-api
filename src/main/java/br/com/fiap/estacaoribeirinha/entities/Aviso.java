package br.com.fiap.estacaoribeirinha.entities;

import java.time.LocalDateTime;

public class Aviso {

    private int idAviso;
    private int idEstacao;
    private String titulo;
    private String mensagem;
    private String nivel;
    private LocalDateTime dataHora;
    private String status;

    public Aviso() {
    }

    public Aviso(int idEstacao, String titulo, String mensagem, String nivel, LocalDateTime dataHora, String status) {
        this.idEstacao = idEstacao;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.nivel = nivel;
        this.dataHora = dataHora;
        this.status = status;
    }

    public Aviso(int idAviso, int idEstacao, String titulo, String mensagem, String nivel, LocalDateTime dataHora, String status) {
        this.idAviso = idAviso;
        this.idEstacao = idEstacao;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.nivel = nivel;
        this.dataHora = dataHora;
        this.status = status;
    }

    public int getIdAviso() {
        return idAviso;
    }

    public void setIdAviso(int idAviso) {
        this.idAviso = idAviso;
    }

    public int getIdEstacao() {
        return idEstacao;
    }

    public void setIdEstacao(int idEstacao) {
        this.idEstacao = idEstacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Aviso: " +
                "idAviso=" + idAviso +
                "\nidEstacao=" + idEstacao +
                "\ntitulo='" + titulo + '\'' +
                "\nmensagem='" + mensagem + '\'' +
                "\nnivel='" + nivel + '\'' +
                "\ndataHora=" + dataHora +
                "\nstatus='" + status + '\'';
    }
}
