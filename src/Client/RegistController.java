package Client;

import Gemeinsam.BenutzerRegisterDaten;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistController implements WindowProperty, Initializable
{
    public TextField textBenutzername;
    public PasswordField pwTextPasswort;
    public PasswordField pwTextPasswortWiederholen;
    public Label labelRegistrieren;
    public Label labelPasswortWIederholen;
    public Button buttonClose;
    private ClientProxy cp;

    public ClientProxy getCp() {
        return cp;
    }

    public void setCp(ClientProxy cp) {
        this.cp = cp;
    }

    public void registrieren(ActionEvent mouseEvent)
    {
        if(textBenutzername.getText() != null && pwTextPasswort.getText() != null)
        {
            System.out.println(pwTextPasswort.getText().hashCode());
            cp.senden(new BenutzerRegisterDaten(textBenutzername.getText(), pwTextPasswort.getText().hashCode()));

        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }
}
