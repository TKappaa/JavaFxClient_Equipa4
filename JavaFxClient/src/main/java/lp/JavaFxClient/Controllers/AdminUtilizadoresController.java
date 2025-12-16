package lp.JavaFxClient.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lp.JavaFxClient.Model.UtilizadorDTO;
import lp.JavaFxClient.Session.Session;
import lp.JavaFxClient.services.ApiService;

import java.util.List;

public class AdminUtilizadoresController {

    @FXML private TableView<UtilizadorDTO> usersTable;
    @FXML private TableColumn<UtilizadorDTO, Integer> colId;
    @FXML private TableColumn<UtilizadorDTO, String> colNome;
    @FXML private TableColumn<UtilizadorDTO, String> colEmail;
    @FXML private TableColumn<UtilizadorDTO, String> colEstado;
    @FXML private TableColumn<UtilizadorDTO, String> colTipo;

    @FXML private ComboBox<String> estadoCombo;
    @FXML private ComboBox<String> tipoCombo;
    @FXML private Label statusLabel;

    private final ObservableList<UtilizadorDTO> data = FXCollections.observableArrayList();
    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeUtilizador"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoUtilizador"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoUtilizador"));

        usersTable.setItems(data);

        estadoCombo.setItems(FXCollections.observableArrayList("ATIVO", "INATIVO"));
        tipoCombo.setItems(FXCollections.observableArrayList("ADMIN", "ORGANIZADOR", "PARTICIPANTE"));

        usersTable.getSelectionModel().selectedItemProperty().addListener((obs, o, u) -> {
            if (u != null) {
                estadoCombo.setValue(u.getEstadoUtilizador());
                tipoCombo.setValue(u.getTipoUtilizador());
                statusLabel.setText("");
            }
        });

        carregarUtilizadores();
    }

    private void carregarUtilizadores() {
        try {
            statusLabel.setText("");

            String json = api.get("/api/utilizadores");
            List<UtilizadorDTO> lista =
                    mapper.readValue(json, new TypeReference<List<UtilizadorDTO>>() {});
            data.setAll(lista);

            if (!lista.isEmpty()) {
                usersTable.getSelectionModel().selectFirst();
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            statusLabel.setText("Erro ao carregar utilizadores: " + e.getMessage());
        }
    }

    @FXML
    public void onGuardarAlteracoes(ActionEvent event) {

        UtilizadorDTO selecionado = usersTable.getSelectionModel().getSelectedItem();
        UtilizadorDTO adminAtual = Session.getCurrentUser();

        if (selecionado == null) {
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            statusLabel.setText("Seleciona um utilizador.");
            return;
        }

        
        if ("ADMIN".equalsIgnoreCase(selecionado.getTipoUtilizador())
                && adminAtual != null
                && !selecionado.getId().equals(adminAtual.getId())) {
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            statusLabel.setText("Não podes alterar outro ADMIN.");
            return;
        }

        String novoEstado = estadoCombo.getValue();
        String novoTipo = tipoCombo.getValue();

        if (novoEstado == null || novoTipo == null) {
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            statusLabel.setText("Escolhe estado e tipo.");
            return;
        }

        try {
            var payload = new java.util.HashMap<String, String>();
            payload.put("estadoUtilizador", novoEstado);
            payload.put("tipoUtilizador", novoTipo);

            String body = mapper.writeValueAsString(payload);

            api.put("/api/utilizadores/" + selecionado.getId() + "/admin-update", body);

            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            statusLabel.setText("Utilizador atualizado com sucesso.");

            carregarUtilizadores();

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            statusLabel.setText("Erro ao guardar: " + e.getMessage());
        }
    }
}
