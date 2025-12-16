package lp.JavaFxClient.Controllers;

import lp.JavaFxClient.Session.Session;
import lp.JavaFxClient.Model.UtilizadorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
// import javafx.scene.control.ComboBox; // Não precisamos mais disto
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import lp.JavaFxClient.services.ApiService;

import java.util.HashMap;
import java.util.Map;

public class CriarEventoController {

    @FXML private TextField tituloField;
    @FXML private TextField localField;
    @FXML private TextField capacidadeField;
    @FXML private DatePicker inicioDate;
    @FXML private DatePicker fimDate;
    
    // ALTERAÇÃO 1: Voltamos a usar TextField
    @FXML private TextField categoriaField; 
    @FXML private TextArea descricaoArea;

    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    // ALTERAÇÃO 2: Removemos o método initialize() pois não há lista para carregar

    @FXML
    public void onGuardar(ActionEvent event) {
        try {
            UtilizadorDTO organizador = Session.getCurrentUser();
            
            if (organizador == null) {
                showError("Erro: Nenhum organizador logado.");
                return;
            }

            Map<String, Object> payload = new HashMap<>();
            payload.put("titulo", tituloField.getText());
            payload.put("local", localField.getText());
            
            // ALTERAÇÃO 3: Lemos o texto diretamente
            payload.put("categoria", categoriaField.getText()); 
            payload.put("descricao", descricaoArea.getText()); 

            try {
                payload.put("capacidade", Integer.parseInt(capacidadeField.getText()));
            } catch (NumberFormatException e) {
                showError("A capacidade deve ser um número.");
                return;
            }
            
            if (inicioDate.getValue() != null)
                payload.put("dataInicio", inicioDate.getValue().toString() + "T09:00:00");
            
            if (fimDate.getValue() != null)
                payload.put("dataFim", fimDate.getValue().toString() + "T18:00:00");

            String json = mapper.writeValueAsString(payload);
            String url = "/api/eventos?organizadorId=" + organizador.getId();
            
            api.post(url, json);

            Alert a = new Alert(Alert.AlertType.INFORMATION, "Evento criado com sucesso!");
            a.showAndWait();
            
            limparFormulario();

        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao criar: " + e.getMessage());
            a.show();
        }
    }
    
    private void limparFormulario() {
        tituloField.clear();
        localField.clear();
        capacidadeField.clear();
        descricaoArea.clear();
        categoriaField.clear(); // Limpar o campo de texto
        inicioDate.setValue(null);
        fimDate.setValue(null);
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.show();
    }
}