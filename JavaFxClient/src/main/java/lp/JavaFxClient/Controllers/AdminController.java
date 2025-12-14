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

public class AdminController {

    @FXML private BorderPane rootPane;
    @FXML private Label welcomeLabel;

    @FXML
    public void initialize() {
        UtilizadorDTO u = Session.getCurrentUser();
        if (welcomeLabel != null) {
            welcomeLabel.setText("Bem-vindo, " + (u != null ? u.getNomeUtilizador() : ""));
        }

        // abre por defeito a gestão de utilizadores
        trocarCentro("/admin-utilizadores-pane.fxml");
    }

    @FXML
    public void onUtilizadores(ActionEvent event) {
        trocarCentro("/admin-utilizadores-pane.fxml");
    }

    @FXML
    public void onEventos(ActionEvent event) {
        trocarCentro("/admin-eventos-pane.fxml");
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
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("Falha no logout");
            a.setContentText(e.toString());
            a.showAndWait();
        }
    }

    private void trocarCentro(String fxml) {
        try {
            var url = getClass().getResource(fxml);
            System.out.println("DEBUG resource " + fxml + " -> " + url);

            if (url == null) {
                throw new RuntimeException("FXML não encontrado no classpath: " + fxml);
            }

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
