package lp.JavaFxClient.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lp.JavaFxClient.Model.EventoDTO;
import lp.JavaFxClient.Model.UtilizadorDTO;
import lp.JavaFxClient.Session.Session;
import lp.JavaFxClient.services.ApiService;

import java.util.List;

public class ParticipanteEventosController {

    @FXML private TableView<EventoDTO> eventosTable;

    @FXML private TableColumn<EventoDTO, Integer> colId;
    @FXML private TableColumn<EventoDTO, String> colTitulo;
    @FXML private TableColumn<EventoDTO, String> colLocal;
    @FXML private TableColumn<EventoDTO, Integer> colCapacidade;
    @FXML private TableColumn<EventoDTO, String> colInicio;
    @FXML private TableColumn<EventoDTO, String> colFim;

    private final ObservableList<EventoDTO> data = FXCollections.observableArrayList();
    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
        colCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));

        eventosTable.setItems(data);
        carregarEventosAtivos();
    }


    private void carregarEventosAtivos() {
        try {
            String json = api.get("/api/eventos?estado=ATIVO");
            List<EventoDTO> lista =
                    mapper.readValue(json, new TypeReference<List<EventoDTO>>() {});
            data.setAll(lista);

            if (lista.isEmpty()) {
                alert("Info", "Não existem eventos ativos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            alert("Erro", "Falha ao carregar eventos ativos:\n" + e.getMessage());
        }
    }
    
    @FXML
    public void onInscrever() {
        EventoDTO evento = eventosTable.getSelectionModel().getSelectedItem();

        if (evento == null) {
            alert("Aviso", "Seleciona um evento primeiro.");
            return;
        }

        UtilizadorDTO u = Session.getCurrentUser();

        try {
            String body = String.format(
                "{ \"eventoId\": %d, \"participanteId\": %d }",
                evento.getId(),
                u.getId()
            );

            String resp = api.post("/api/inscricoes", body);
            alert("Sucesso", resp);

        } catch (Exception e) {
            alert("Erro", e.getMessage());
        }
    }


    private void alert(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
