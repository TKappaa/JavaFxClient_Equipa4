package lp.JavaFxClient.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    public void onRegister(ActionEvent event) {
        
        String nome = nomeField.getText() == null ? "" : nomeField.getText().trim();
        String email = emailField.getText() == null ? "" : emailField.getText().trim();
        String pass = passwordField.getText() == null ? "" : passwordField.getText().trim();

        if (nome.isBlank() || email.isBlank() || pass.isBlank()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Preenche nome, email e password.");
            a.showAndWait();
            return;
        }

        Alert ok = new Alert(Alert.AlertType.INFORMATION, "Registo (fake) OK. Próximo passo: enviar para a API.");
        ok.showAndWait();
    }

    @FXML
    public void onBackToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) nomeField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Connexus - Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro a voltar ao login: " + e.getMessage());
            a.showAndWait();
        }
    }
}
