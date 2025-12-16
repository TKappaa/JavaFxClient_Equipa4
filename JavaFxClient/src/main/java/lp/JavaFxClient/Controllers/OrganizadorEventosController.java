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

public class OrganizadorEventosController {

    @FXML private TableView<EventoDTO> eventosTable;
    @FXML private TableColumn<EventoDTO, String> colTitulo;
    @FXML private TableColumn<EventoDTO, String> colLocal;
    @FXML private TableColumn<EventoDTO, String> colInicio;
    @FXML private TableColumn<EventoDTO, String> colFim;
    @FXML private TableColumn<EventoDTO, String> colEstado;

    private final ObservableList<EventoDTO> data = FXCollections.observableArrayList();
    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    public void initialize() {
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colLocal.setCellValueFactory(new PropertyValueFactory<>("local"));
        colInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        eventosTable.setItems(data);
        carregarEventos();
    }

    private void carregarEventos() {
        try {
            String json = api.get("/api/eventos"); 
            List<EventoDTO> lista = mapper.readValue(json, new TypeReference<List<EventoDTO>>() {});
            data.setAll(lista);
        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao carregar eventos: " + e.getMessage());
            a.show();
        }
    }
}