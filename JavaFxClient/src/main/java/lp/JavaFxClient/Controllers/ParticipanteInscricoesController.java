package lp.JavaFxClient.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import lp.JavaFxClient.Model.InscricaoDTO;
import lp.JavaFxClient.Model.UtilizadorDTO;
import lp.JavaFxClient.Session.Session;
import lp.JavaFxClient.services.ApiService;

import java.util.List;

public class ParticipanteInscricoesController {

    @FXML private TableView<InscricaoDTO> inscricoesTable;

    @FXML private TableColumn<InscricaoDTO, String> colEvento;
    @FXML private TableColumn<InscricaoDTO, String> colEstado;
    @FXML private TableColumn<InscricaoDTO, String> colData;

    private final ObservableList<InscricaoDTO> data = FXCollections.observableArrayList();
    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    public void initialize() {
        colEvento.setCellValueFactory(new PropertyValueFactory<>("eventoTitulo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoInscricao"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataInscricao"));

        inscricoesTable.setItems(data);
        carregarInscricoes();
    }
    
    private void carregarInscricoes() {
        try {
            UtilizadorDTO u = Session.getCurrentUser();

            String json = api.get("/api/inscricoes");
            List<InscricaoDTO> lista =
                mapper.readValue(json, new TypeReference<List<InscricaoDTO>>() {});

            // filtra só as inscrições do participante logado
            lista.removeIf(i -> !u.getId().equals(i.getParticipanteId()));

            data.setAll(lista);

            if (lista.isEmpty()) {
                alert("Info", "Não estás inscrito em nenhum evento.");
            }

        } catch (Exception e) {
            alert("Erro", "Falha ao carregar inscrições:\n" + e.getMessage());
        }
    }

    @FXML
    public void onCancelar() {
        InscricaoDTO i = inscricoesTable.getSelectionModel().getSelectedItem();

        if (i == null) {
            alert("Aviso", "Seleciona uma inscrição primeiro.");
            return;
        }

        if (!"CONFIRMADA".equalsIgnoreCase(i.getEstadoInscricao())) {
            alert("Aviso", "Só podes cancelar inscrições confirmadas.");
            return;
        }

        try {
            UtilizadorDTO u = Session.getCurrentUser();

            String path = "/api/inscricoes/" + i.getId()
                        + "/cancelar?participanteId=" + u.getId();

            String resp = api.put(path, "");
            alert("Sucesso", resp);

            carregarInscricoes();

        } catch (Exception e) {
            alert("Erro", "Falha ao cancelar:\n" + e.getMessage());
        }
    }
    @FXML
    public void onAvaliar() {
        InscricaoDTO sel = inscricoesTable.getSelectionModel().getSelectedItem();

        if (sel == null) {
            alert("Aviso", "Seleciona uma inscrição primeiro.");
            return;
        }

        if (!"CONFIRMADA".equalsIgnoreCase(sel.getEstadoInscricao())) {
            alert("Aviso", "Só podes avaliar inscrições confirmadas.");
            return;
        }

        Session.setEventoParaAvaliar(sel);

        try {
            Parent pane = FXMLLoader.load(
                getClass().getResource("/participante-avaliacoes-pane.fxml")
            );

            BorderPane root =
                (BorderPane) inscricoesTable.getScene().getRoot();

            root.setCenter(pane);

        } catch (Exception e) {
            alert("Erro", e.getMessage());
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
