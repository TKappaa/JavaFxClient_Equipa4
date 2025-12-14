package lp.JavaFxClient.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient.Model.UtilizadorDTO;
import lp.JavaFxClient.services.ApiService;


public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void onLogin(ActionEvent event) {
        String email = emailField.getText() == null ? "" : emailField.getText().trim();
        String pass  = passwordField.getText() == null ? "" : passwordField.getText().trim();

        if (email.isBlank() || pass.isBlank()) {
            alert(Alert.AlertType.WARNING, "Atenção", "Preenche email e password.");
            return;
        }

        try {
            String body = """
                {
                  "email": "%s",
                  "senha": "%s"
                }
                """.formatted(email, pass);

            String json = api.post("/api/utilizadores/login", body);
            UtilizadorDTO user = mapper.readValue(json, UtilizadorDTO.class);

            
            lp.JavaFxClient.Session.Session.setCurrentUser(user);

            abrirMenuPorTipo(user.getTipoUtilizador());

        } catch (Exception e) {
            alert(Alert.AlertType.ERROR, "Login inválido", e.getMessage());
        }
    }


    @FXML
    public void onOpenRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register-view.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Connexus - Registo");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            alert(Alert.AlertType.ERROR, "Erro", "Erro a abrir registo:\n" + e.getMessage());
        }
    }


    private void alert(Alert.AlertType type, String titulo, String mensagem) {
        Alert a = new Alert(type);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensagem);
        a.showAndWait();
    }
    private void abrirMenuPorTipo(String tipo) throws Exception {
        String fxml;
        if (tipo == null) {
            fxml = "/participante-view.fxml";
        } else {
            switch (tipo.toUpperCase()) {
                case "ADMIN" -> fxml = "/admin-view.fxml";
                case "ORGANIZADOR" -> fxml = "/organizador-view.fxml";
                default -> fxml = "/participante-view.fxml";
            }
        }

        var url = getClass().getResource(fxml);
        if (url == null) {
            throw new RuntimeException("FXML não encontrado em resources: " + fxml);
        }

        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
