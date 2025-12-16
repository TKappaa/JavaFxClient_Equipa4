package lp.JavaFxClient.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lp.JavaFxClient.Model.UtilizadorDTO;
import lp.JavaFxClient.Session.Session;

public class AdminController {

    @FXML private BorderPane rootPane;
    @FXML private Label welcomeLabel;

    
    @FXML private Button btnUtilizadores;
    @FXML private Button btnEventos;
    @FXML private Button btnInscricoes;
    @FXML private Button btnLogout;

    
    private static final String STYLE_NORMAL =
            "-fx-background-radius: 10;" +
            "-fx-font-weight: bold;" +
            "-fx-background-color: #ffffff;" +
            "-fx-border-color: #d6dbe3;" +
            "-fx-border-radius: 10;";

    private static final String STYLE_ACTIVE =
            "-fx-background-radius: 10;" +
            "-fx-font-weight: bold;" +
            "-fx-background-color: #4a90e2;" +
            "-fx-text-fill: white;" +
            "-fx-border-color: #4a90e2;" +
            "-fx-border-radius: 10;";

    public void initialize() {
        UtilizadorDTO u = Session.getCurrentUser();
        welcomeLabel.setText("Bem-vindo, " + (u != null ? u.getNomeUtilizador() : ""));

        
        onUtilizadores();
    }

    @FXML
    public void onUtilizadores() {
        setActive(btnUtilizadores);
        trocarCentro("/admin-utilizadores-pane.fxml");
    }

    @FXML
    public void onEventos() {
        setActive(btnEventos);
        trocarCentro("/admin-eventos-pane.fxml");
    }

    @FXML
    public void onInscricoes() {
        setActive(btnInscricoes);
        trocarCentro("/admin-inscricoes-pane.fxml"); // garante que tens este fxml
    }

    @FXML
    public void onLogout() {
        try {
            Session.clear();
            Parent root = FXMLLoader.load(getClass().getResource("/main-view.fxml"));
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexus - Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setActive(Button active) {
        
        btnUtilizadores.setStyle(STYLE_NORMAL);
        btnEventos.setStyle(STYLE_NORMAL);
        btnInscricoes.setStyle(STYLE_NORMAL);

        
        if (active != null) active.setStyle(STYLE_ACTIVE);
    }

    private void trocarCentro(String fxml) {
        try {
            var url = getClass().getResource(fxml);
            if (url == null) throw new RuntimeException("FXML não encontrado: " + fxml);

            Parent pane = FXMLLoader.load(url);
            rootPane.setCenter(pane);

        } catch (Exception e) {
            e.printStackTrace();

            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("Falha a abrir " + fxml);
            a.setContentText(e.toString());
            a.showAndWait();
        }
    }
}
