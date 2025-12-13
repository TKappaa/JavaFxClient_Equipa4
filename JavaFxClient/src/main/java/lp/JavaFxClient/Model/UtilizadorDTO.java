package lp.JavaFxClient.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UtilizadorDTO {

    private Integer id;
    private String nomeUtilizador;
    private String email;

    // vem do JSON do backend (ATIVO / INATIVO)
    private String estadoUtilizador;

    // vem do JSON do backend (ADMIN / ORGANIZADOR / PARTICIPANTE)
    private String tipoUtilizador;

    // vem do JSON do backend (para validar password)
    private String senhaUtilizador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeUtilizador() {
        return nomeUtilizador;
    }

    public void setNomeUtilizador(String nomeUtilizador) {
        this.nomeUtilizador = nomeUtilizador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstadoUtilizador() {
        return estadoUtilizador;
    }

    public void setEstadoUtilizador(String estadoUtilizador) {
        this.estadoUtilizador = estadoUtilizador;
    }

    public String getTipoUtilizador() {
        return tipoUtilizador;
    }

    public void setTipoUtilizador(String tipoUtilizador) {
        this.tipoUtilizador = tipoUtilizador;
    }

    public String getSenhaUtilizador() {
        return senhaUtilizador;
    }

    public void setSenhaUtilizador(String senhaUtilizador) {
        this.senhaUtilizador = senhaUtilizador;
    }
}
