package lp.JavaFxClient.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lp.JavaFxClient.Model.EventoDTO;
import lp.JavaFxClient.services.ApiService;

import java.util.List;

public class AdminEventosController {

    @FXML private TableView<EventoDTO> eventosTable;

    @FXML private TableColumn<EventoDTO, Integer> colId;
    @FXML private TableColumn<EventoDTO, String> colTitulo;
    @FXML private TableColumn<EventoDTO, String> colLocal;
    @FXML private TableColumn<EventoDTO, Integer> colCapacidade;
    @FXML private TableColumn<EventoDTO, String> colInicio;
    @FXML private TableColumn<EventoDTO, String> colFim;
    @FXML private TableColumn<EventoDTO, String> colEstado;

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
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        eventosTable.setItems(data);

        carregarEventos();
    }

    private void carregarEventos() {
        try {
            String json = api.get("/api/eventos");
            System.out.println("JSON EVENTOS = " + json);

            List<EventoDTO> lista = mapper.readValue(json, new TypeReference<List<EventoDTO>>() {});
            data.setAll(lista);

            System.out.println("TOTAL EVENTOS = " + lista.size());

            if (lista.isEmpty()) {
                alert("Aviso", "A API devolveu 0 eventos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            alert("Erro", "Falha ao carregar eventos:\n" + e.getMessage());
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
