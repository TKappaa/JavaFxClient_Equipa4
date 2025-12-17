package lp.JavaFxClient.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvaliacaoDTO {

    private Integer id;

    private Integer eventoId;
    private Integer participanteId;

    private Integer pontuacao;
    private String comentario;
    private String dataAvaliacao;

    // campos "extra" para o frontend
    private String eventoTitulo;
    private String participanteNome;

    public AvaliacaoDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventoId() {
        return eventoId;
    }

    public void setEventoId(Integer eventoId) {
        this.eventoId = eventoId;
    }

    public Integer getParticipanteId() {
        return participanteId;
    }

    public void setParticipanteId(Integer participanteId) {
        this.participanteId = participanteId;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(String dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getEventoTitulo() {
        return eventoTitulo;
    }

    public void setEventoTitulo(String eventoTitulo) {
        this.eventoTitulo = eventoTitulo;
    }

    public String getParticipanteNome() {
        return participanteNome;
    }

    public void setParticipanteNome(String participanteNome) {
        this.participanteNome = participanteNome;
    }
}
