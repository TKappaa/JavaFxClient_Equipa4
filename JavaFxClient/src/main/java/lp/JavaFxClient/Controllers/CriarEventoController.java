package lp.JavaFxClient.Controllers;

import lp.JavaFxClient.Session.Session;
import lp.JavaFxClient.Model.UtilizadorDTO;
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
    @FXML private DatePicker inicioDate;
    @FXML private DatePicker fimDate;

    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void onGuardar(ActionEvent event) {
        try {
            // 1. Obter o organizador logado
            UtilizadorDTO organizador = Session.getCurrentUser();
            
            if (organizador == null) {
                showError("Erro: Nenhum organizador logado.");
                return;
            }

            // 2. Criar o JSON do evento (igual ao que já tinhas)
            Map<String, Object> payload = new HashMap<>();
            payload.put("titulo", tituloField.getText());
            payload.put("local", localField.getText());
            
            // Validar se capacidade é número
            try {
                payload.put("capacidade", Integer.parseInt(capacidadeField.getText()));
            } catch (NumberFormatException e) {
                showError("A capacidade deve ser um número.");
                return;
            }
            
            // Nota: Confirma se o formato da data é este ou se precisas de horas
            if (inicioDate.getValue() != null)
                payload.put("dataInicio", inicioDate.getValue().toString() + "T09:00:00");
            
            if (fimDate.getValue() != null)
                payload.put("dataFim", fimDate.getValue().toString() + "T18:00:00");

            String json = mapper.writeValueAsString(payload);

            // 3. ALTERAÇÃO PRINCIPAL: Adicionar o ?organizadorId=... no fim do URL
            // O backend pede este parâmetro explicitamente
            String url = "/api/eventos?organizadorId=" + organizador.getId();
            
            api.post(url, json);

            Alert a = new Alert(Alert.AlertType.INFORMATION, "Evento criado com sucesso!");
            a.showAndWait();
            
            // Limpar campos...
            tituloField.clear();
            localField.clear();
            capacidadeField.clear();

        } catch (Exception e) {
            e.printStackTrace();
            // Mostra o erro real que vem da API
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao criar: " + e.getMessage());
            a.show();
        }
    }
    
    // Pequeno método auxiliar para mensagens de erro, se não tiveres
    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.show();
    }
}