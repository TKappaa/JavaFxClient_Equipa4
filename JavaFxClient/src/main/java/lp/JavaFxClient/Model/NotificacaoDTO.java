package lp.JavaFxClient.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificacaoDTO {

    private Integer id;
    private Integer participanteId;
    private String mensagem;
    private boolean lida;
    private String dataCriacao;

    public NotificacaoDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getParticipanteId() { return participanteId; }
    public void setParticipanteId(Integer participanteId) {
        this.participanteId = participanteId;
    }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public boolean isLida() { return lida; }
    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public String getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
