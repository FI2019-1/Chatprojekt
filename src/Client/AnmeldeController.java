package Client;

import Gemeinsam.Benutzer;
import Gemeinsam.BenutzerAnmeldeDaten;
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
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AnmeldeController implements Initializable
{
    public TextField textBenutzername;
    public Button buttonAnmelden;
    public PasswordField pwTextPasswort;
    public Label labelRegistrieren;

    private ClientProxy cp;

    public void anmelden(ActionEvent actionEvent) throws IOException {
        if(textBenutzername.getText() != null && pwTextPasswort.getText() != null)
        {
            System.out.println(pwTextPasswort.getText().hashCode());
            cp.senden(new BenutzerAnmeldeDaten(textBenutzername.getText(), pwTextPasswort.getText().hashCode()));

        }
    }
    public void bestaetigung(BenutzerAnmeldeDaten anmeldeDaten) throws IOException {
        if(anmeldeDaten.getBestaetigung() == true)
        {
            Stage primaryStage = (Stage) buttonAnmelden.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));

            //Parent parent = FXMLLoader.load(loader);
            Parent rootSignup = (Parent) loader.load();
            ClientController c = loader.getController();
            c.setCp(cp);
            c.getCp().setBenutzer(new Benutzer(anmeldeDaten.getBenutzername()));
            primaryStage.setScene(new Scene(rootSignup,  716.0, 564.0));
        }

    }
    public void starteClient()
    {
        try
        {
            Socket client = new Socket("localhost", 5555);
            System.out.println("Client konnte gestartet werden :)");
            cp = new ClientProxy(client,this);
            Thread t = new Thread(cp);
            t.start();
        } catch (Exception e) {
            System.out.println("Fehler in CC starteClient D:" + e.getMessage());
        }
    }



    public void registrieren(MouseEvent mouseEvent)
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        starteClient();
    }
}
