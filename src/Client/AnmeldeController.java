package Client;

import javafx.application.Platform;
import Gemeinsam.Benutzer;
import Gemeinsam.BenutzerAnmeldeDaten;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AnmeldeController implements Initializable, WindowProperty
{
    public TextField textBenutzername;
    public Button buttonAnmelden;
    public PasswordField pwTextPasswort;
    public Label labelRegistrieren;
    private ClientProxy cp;

    public ClientProxy getCp() {
        return cp;
    }

    public void anmelden(ActionEvent actionEvent) throws IOException {
        if(textBenutzername.getText() != null && pwTextPasswort.getText() != null)
        {
            System.out.println(pwTextPasswort.getText().hashCode());
            cp.senden(new BenutzerAnmeldeDaten(textBenutzername.getText(), pwTextPasswort.getText().hashCode()));

        }
    }
    public void bestaetigung(BenutzerAnmeldeDaten anmeldeDaten) throws IOException {
        System.out.println("test");
        if(anmeldeDaten.getBestaetigung() == true)
        {

            Stage primaryStage = (Stage) buttonAnmelden.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("client.fxml"));

            //Parent parent = FXMLLoader.load(loader);
            Parent rootChat = (Parent) loader.load();
            ClientController c = loader.getController();
            c.setCp(cp);
            c.getCp().setBenutzer(new Benutzer(anmeldeDaten.getBenutzername()));
            Scene sceneChat = new Scene(rootChat,714, 631);
            sceneChat.getStylesheets().add((getClass().getResource("ClientUI.css").toExternalForm()));
            primaryStage.setScene(sceneChat);

            DragDrop(rootChat, primaryStage);
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

    public void exitApplication()
    {
        Platform.exit();
    }

    public void openSignUp(MouseEvent mouseEvent) throws IOException
    {
        Parent rootSignUp = FXMLLoader.load(getClass().getResource("Registrierung.fxml"));

        Stage primaryStage = (Stage)labelRegistrieren.getScene().getWindow();
        Scene sceneSignUp = new Scene(rootSignUp, 375, 483);
        sceneSignUp.getStylesheets().add((getClass().getResource("RegistrierungUI.css").toExternalForm()));
        primaryStage.setScene(sceneSignUp);
        DragDrop(rootSignUp, primaryStage);
    }

    public void registrieren(MouseEvent mouseEvent)
    {

    }

    public void openSignIn(MouseEvent mouseEvent) throws IOException
    {
        Parent rootSignIn = FXMLLoader.load(getClass().getResource("Anmeldung.fxml"));

        Stage primaryStage = (Stage)labelRegistrieren.getScene().getWindow();
        Scene sceneSignIn = new Scene(rootSignIn, 375, 403);
        sceneSignIn.getStylesheets().add((getClass().getResource("AnmeldungUI.css").toExternalForm()));
        primaryStage.setScene(sceneSignIn);

        DragDrop(rootSignIn, primaryStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        starteClient();
    }
}
