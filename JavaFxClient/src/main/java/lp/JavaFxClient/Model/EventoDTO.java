package lp.JavaFxClient.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventoDTO {

    private Integer id;

    @JsonAlias({"titulo", "tituloEvento"})
    private String titulo;

    @JsonAlias({"local", "localEvento"})
    private String local;

    @JsonAlias({"capacidade", "capacidadeEvento"})
    private Integer capacidade;

    @JsonAlias({"dataInicio", "data_inicio", "inicio"})
    private String dataInicio;

    @JsonAlias({"dataFim", "data_fim", "fim"})
    private String dataFim;

    @JsonAlias({"estado", "estadoEvento"})
    private String estado;

    public EventoDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
