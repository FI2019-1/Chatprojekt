package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AnmeldeController
{


    public TextField textBenutzername;
    public Button buttonAnmelden;
    public PasswordField pwTextPasswort;

    public void anmelden(ActionEvent actionEvent)
    {
        if(textBenutzername.getText() != null && pwTextPasswort.getText() != null)
        {
            /*
            new BenutzerAnmeldeDaten(textBenutzername.getText(), pwTextPasswort.getText().hashCode());
            Parent rootSignup = FXMLLoader.load(getClass().getResource("client.fxml"));

            Stage primaryStage = (Stage) buttonAnmelden.getScene().getWindow();
            primaryStage.setScene(new Scene(rootSignup, 300, 275));*/
        }
    }

    public void registrieren(MouseEvent mouseEvent)
    {

    }
}
