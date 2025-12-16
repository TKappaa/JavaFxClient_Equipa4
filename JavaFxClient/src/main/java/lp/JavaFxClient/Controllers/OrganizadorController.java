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
        // CORREÇÃO: Alterado para -view.fxml
        trocarCentro("/organizador-eventos-view.fxml");
    }

    @FXML
    public void onMeusEventos(ActionEvent event) {
        // CORREÇÃO: Alterado para -view.fxml
        trocarCentro("/organizador-eventos-view.fxml");
    }

    @FXML
    public void onCriarEvento(ActionEvent event) {
        // CORREÇÃO: Alterado para -view.fxml
        trocarCentro("/criar-evento-view.fxml");
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
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro no logout: " + e.getMessage());
            a.show();
        }
    }

    private void trocarCentro(String fxml) {
        try {
            var url = getClass().getResource(fxml);
            if (url == null) {
                System.err.println("ERRO: Ficheiro não encontrado: " + fxml);
                return;
            }
            Parent pane = FXMLLoader.load(url);
            rootPane.setCenter(pane);
        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Erro ao abrir " + fxml + ":\n" + e.getMessage());
            a.show();
        }
    }
}