package lp.JavaFxClient.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient.services.ApiService;

import java.util.HashMap;
import java.util.Map;

public class RegisterController {

    @FXML private TextField nomeField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void onRegister(ActionEvent event) {
        String nome = nomeField.getText() == null ? "" : nomeField.getText().trim();
        String email = emailField.getText() == null ? "" : emailField.getText().trim();
        String senha = passwordField.getText() == null ? "" : passwordField.getText().trim();

        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            showError("Preenche nome, email e password.");
            return;
        }

        try {
            // payload igual ao UtilizadorCreateDTO do backend
            Map<String, String> payload = new HashMap<>();
            payload.put("nomeUtilizador", nome);
            payload.put("email", email);
            payload.put("senhaUtilizador", senha);

            String body = mapper.writeValueAsString(payload);

            // cria utilizador no backend (o backend define ATIVO + PARTICIPANTE + id auto)
            api.post("/api/utilizadores", body);

            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setTitle("Conta criada");
            ok.setHeaderText(null);
            ok.setContentText("Conta criada com sucesso! Já podes fazer login.");
            ok.showAndWait();

            // voltar ao login
            voltarAoLogin();

        } catch (Exception e) {
            // Aqui normalmente vem a mensagem do backend (ex: "Email já existe")
            String msg = e.getMessage() == null ? "Erro no registo." : e.getMessage();

            // tenta melhorar a mensagem para o utilizador
            if (msg.toLowerCase().contains("email") && msg.toLowerCase().contains("existe")) {
                showError("Esse email já está registado. Usa outro email.");
            } else {
                showError("Erro no registo: " + msg);
            }
        }
    }

    @FXML
    public void onBackToLogin(ActionEvent event) {
        try {
            voltarAoLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void voltarAoLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) emailField.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Connexus - Login");
        stage.show();
    }

    private void showError(String msg) {
        if (errorLabel != null) {
            errorLabel.setText(msg);
            errorLabel.setVisible(true);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, msg);
            a.showAndWait();
        }
    }
}
