package lp.JavaFxClient.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lp.JavaFxClient.Model.UtilizadorDTO;
import lp.JavaFxClient.Session.Session;

public class OrganizadorController {

    @FXML private BorderPane rootPane;
    @FXML private Label welcomeLabel;

    @FXML
    public void initialize() {
        UtilizadorDTO u = Session.getCurrentUser();
        if (welcomeLabel != null) {
            welcomeLabel.setText("Bem-vindo, " + (u != null ? u.getNomeUtilizador() : "Organizador"));
        }
        // Abre os eventos por defeito
        trocarCentro("/organizador-eventos-pane.fxml");
    }

    @FXML
    public void onMeusEventos(ActionEvent event) {
        trocarCentro("/organizador-eventos-pane.fxml");
    }

    @FXML
    public void onCriarEvento(ActionEvent event) {
        trocarCentro("/criar-evento-pane.fxml");
    }

    @FXML
    public void onLogout(ActionEvent event) {
        try {
            Session.clear();
            Parent root = FXMLLoader.load(getClass().getResource("/main-view.fxml"));
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexus - Login");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void trocarCentro(String fxml) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(fxml));
            rootPane.setCenter(pane);
        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir: " + fxml);
            a.show();
        }
    }
}