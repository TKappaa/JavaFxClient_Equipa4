package lp.JavaFxClient.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lp.JavaFxClient.Model.AvaliacaoDTO;
import lp.JavaFxClient.Model.InscricaoDTO;
import lp.JavaFxClient.Model.UtilizadorDTO;
import lp.JavaFxClient.Session.Session;
import lp.JavaFxClient.services.ApiService;

import java.util.List;

public class ParticipanteAvaliacoesController {

    // -------- FORMULÁRIO --------
    @FXML private Label lblEvento;
    @FXML private Spinner<Integer> spPontuacao;
    @FXML private TextArea txtComentario;
    @FXML private Button btnAvaliar;

    // -------- TABELA --------
    @FXML private TableView<AvaliacaoDTO> avaliacoesTable;
    @FXML private TableColumn<AvaliacaoDTO, String> colEvento;
    @FXML private TableColumn<AvaliacaoDTO, Integer> colPontuacao;
    @FXML private TableColumn<AvaliacaoDTO, String> colComentario;
    @FXML private TableColumn<AvaliacaoDTO, String> colData;

    private final ObservableList<AvaliacaoDTO> data =
            FXCollections.observableArrayList();

    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    private InscricaoDTO inscricao;

    // ================== INIT ==================
    public void initialize() {

        // evento selecionado a partir das inscrições
        inscricao = Session.getEventoParaAvaliar();

        if (inscricao != null) {
            lblEvento.setText("Evento: ID " + inscricao.getEventoId());
        } else {
            lblEvento.setText("Nenhum evento selecionado.");
            btnAvaliar.setDisable(true);
        }

        spPontuacao.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 5)
        );

        // tabela
        colEvento.setCellValueFactory(c ->
            new javafx.beans.property.SimpleStringProperty(
                "Evento #" + c.getValue().getEventoId()
            )
        );
        colPontuacao.setCellValueFactory(
            new PropertyValueFactory<>("pontuacao")
        );
        colComentario.setCellValueFactory(
            new PropertyValueFactory<>("comentario")
        );
        colData.setCellValueFactory(
            new PropertyValueFactory<>("dataAvaliacao")
        );

        avaliacoesTable.setItems(data);

        carregarAvaliacoes();
    }

    // ================== SUBMETER AVALIAÇÃO ==================
    @FXML
    public void onAvaliar() {

        if (inscricao == null) {
            alert("Erro", "Nenhum evento selecionado para avaliar.");
            return;
        }

        try {
            UtilizadorDTO u = Session.getCurrentUser();

            String body = String.format(
                "{ \"eventoId\": %d, \"participanteId\": %d, \"pontuacao\": %d, \"comentario\": \"%s\" }",
                inscricao.getEventoId(),
                u.getId(),
                spPontuacao.getValue(),
                txtComentario.getText()
            );

            api.post("/api/avaliacoes", body);

            alert("Sucesso", "Avaliação submetida com sucesso.");

            txtComentario.clear();
            Session.clearEventoParaAvaliar();

            carregarAvaliacoes();

        } catch (Exception e) {
            alert("Erro", "Falha ao submeter avaliação:\n" + e.getMessage());
        }
    }

    // ================== CONSULTAR PRÓPRIAS AVALIAÇÕES ==================
    private void carregarAvaliacoes() {
        try {
            UtilizadorDTO u = Session.getCurrentUser();

            String json = api.get("/api/avaliacoes");

            List<AvaliacaoDTO> lista =
                mapper.readValue(json, new TypeReference<List<AvaliacaoDTO>>() {});

            lista.removeIf(a -> !u.getId().equals(a.getParticipanteId()));

            data.setAll(lista);

        } catch (Exception e) {
            alert("Erro", "Falha ao carregar avaliações:\n" + e.getMessage());
        }
    }

    private void alert(String t, String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(t);
        a.setHeaderText(null);
        a.setContentText(m);
        a.showAndWait();
    }
}
