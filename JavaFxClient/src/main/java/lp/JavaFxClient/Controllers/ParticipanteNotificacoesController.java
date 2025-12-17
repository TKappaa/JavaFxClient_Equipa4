package lp.JavaFxClient.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lp.JavaFxClient.Model.NotificacaoDTO;
import lp.JavaFxClient.Model.UtilizadorDTO;
import lp.JavaFxClient.Session.Session;
import lp.JavaFxClient.services.ApiService;

import java.util.List;

public class ParticipanteNotificacoesController {

    @FXML private TableView<NotificacaoDTO> notificacoesTable;

    @FXML private TableColumn<NotificacaoDTO, String> colMensagem;
    @FXML private TableColumn<NotificacaoDTO, String> colData;
    @FXML private TableColumn<NotificacaoDTO, String> colEstado;

    private final ObservableList<NotificacaoDTO> data =
            FXCollections.observableArrayList();

    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    public void initialize() {

        colMensagem.setCellValueFactory(
            new PropertyValueFactory<>("mensagem")
        );
        colData.setCellValueFactory(
            new PropertyValueFactory<>("dataCriacao")
        );

        colEstado.setCellValueFactory(c ->
            new javafx.beans.property.SimpleStringProperty(
                c.getValue().isLida() ? "Lida" : "Não lida"
            )
        );

        notificacoesTable.setItems(data);

        carregarNotificacoes();
    }

    // ================== CONSULTAR ==================
    private void carregarNotificacoes() {
        try {
            UtilizadorDTO u = Session.getCurrentUser();

            String json = api.get(	
                "/api/notificacoes?participanteId=" + u.getId()
            );

            List<NotificacaoDTO> lista =
                mapper.readValue(json,
                    new com.fasterxml.jackson.core.type.TypeReference<>() {});

            data.setAll(lista);

        } catch (Exception e) {
            alert("Erro", "Falha ao carregar notificações:\n" + e.getMessage());
        }
    }

    // ================== MARCAR COMO LIDA ==================
    @FXML
    public void onMarcarTodasLidas() {
        try {
            UtilizadorDTO u = Session.getCurrentUser();

            api.put(
                "/api/notificacoes/ler-todas?participanteId=" + u.getId(),
                ""
            );

            alert("Sucesso", "Todas as notificações foram marcadas como lidas.");
            carregarNotificacoes();

        } catch (Exception e) {
            alert("Erro", "Falha ao marcar notificações como lidas:\n" + e.getMessage());
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
