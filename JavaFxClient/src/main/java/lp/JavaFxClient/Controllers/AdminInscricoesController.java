package lp.JavaFxClient.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lp.JavaFxClient.Model.InscricaoDTO;
import lp.JavaFxClient.services.ApiService;

import java.util.List;

public class AdminInscricoesController {

    @FXML private TableView<InscricaoDTO> inscricoesTable;

    @FXML private TableColumn<InscricaoDTO, Integer> colId;
    @FXML private TableColumn<InscricaoDTO, Integer> colEvento;
    @FXML private TableColumn<InscricaoDTO, Integer> colParticipante;
    @FXML private TableColumn<InscricaoDTO, String> colEstado;

    private final ObservableList<InscricaoDTO> data = FXCollections.observableArrayList();
    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    public void initialize() {
        
    	colId.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
    	colEvento.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("eventoTitulo"));
    	colParticipante.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("participanteNome"));
    	colEstado.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("estadoInscricao"));


        inscricoesTable.setItems(data);

        carregarInscricoes(); 
    }

    private void carregarInscricoes() {
        try {
            String json = api.get("/api/inscricoes");

            List<InscricaoDTO> lista =
                    mapper.readValue(json, new TypeReference<List<InscricaoDTO>>() {});

            data.setAll(lista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }

