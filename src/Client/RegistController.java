package Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistController implements WindowProperty
{
    public TextField textBenutzername;
    public PasswordField pwTextPasswort;
    public PasswordField pwTextPasswortWiederholen;
    public Label labelRegistrieren;

    public void registrieren(ActionEvent actionEvent) {
    }

    public void exitApplication(ActionEvent actionEvent)
    {
        Platform.exit();
    }

    public void openSignIn(MouseEvent mouseEvent) throws IOException {
        Parent rootSignIn = FXMLLoader.load(getClass().getResource("Anmeldung.fxml"));

        Stage primaryStage = (Stage)labelRegistrieren.getScene().getWindow();
        Scene sceneSignIn = new Scene(rootSignIn, 375, 403);
        sceneSignIn.getStylesheets().add((getClass().getResource("AnmeldungUI.css").toExternalForm()));
        primaryStage.setScene(sceneSignIn);

        DragDrop(rootSignIn, primaryStage);
    }
}
