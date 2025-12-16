package lp.JavaFxClient.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InscricaoDTO {

    private Integer id;
    private Integer eventoId;
    private Integer participanteId;

    private String eventoTitulo;
    private String participanteNome;
    private String estadoInscricao;
    private String dataInscricao;

    public InscricaoDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getEventoId() { return eventoId; }
    public void setEventoId(Integer eventoId) { this.eventoId = eventoId; }

    public Integer getParticipanteId() { return participanteId; }
    public void setParticipanteId(Integer participanteId) { this.participanteId = participanteId; }

    public String getEventoTitulo() { return eventoTitulo; }
    public void setEventoTitulo(String eventoTitulo) { this.eventoTitulo = eventoTitulo; }

    public String getParticipanteNome() { return participanteNome; }
    public void setParticipanteNome(String participanteNome) { this.participanteNome = participanteNome; }

    public String getEstadoInscricao() { return estadoInscricao; }
    public void setEstadoInscricao(String estadoInscricao) { this.estadoInscricao = estadoInscricao; }

    public String getDataInscricao() { return dataInscricao; }
    public void setDataInscricao(String dataInscricao) { this.dataInscricao = dataInscricao; }
}
