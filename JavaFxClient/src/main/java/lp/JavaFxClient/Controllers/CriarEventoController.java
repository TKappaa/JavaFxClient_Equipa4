package lp.JavaFxClient.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lp.JavaFxClient.services.ApiService;

import java.util.HashMap;
import java.util.Map;

public class CriarEventoController {

    @FXML private TextField tituloField;
    @FXML private TextField localField;
    @FXML private TextField capacidadeField;
    @FXML private DatePicker inicioDate; // Simplificado, idealmente usa-se LocalDateTime
    @FXML private DatePicker fimDate;

    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void onGuardar(ActionEvent event) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("titulo", tituloField.getText());
            payload.put("local", localField.getText());
            payload.put("capacidade", Integer.parseInt(capacidadeField.getText()));
            // Nota: O formato da data deve bater certo com o que o backend espera (ex: "2024-01-01T10:00:00")
            payload.put("dataInicio", inicioDate.getValue().toString() + "T09:00:00"); 
            payload.put("dataFim", fimDate.getValue().toString() + "T18:00:00");

            String json = mapper.writeValueAsString(payload);
            
            api.post("/api/eventos", json);

            Alert a = new Alert(Alert.AlertType.INFORMATION, "Evento criado com sucesso!");
            a.showAndWait();
            
            // Limpar campos
            tituloField.clear();
            localField.clear();
            capacidadeField.clear();

        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao criar: " + e.getMessage());
            a.show();
        }
    }
}