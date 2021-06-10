package Server;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    ArrayList<ClientProxy> clientList = new ArrayList<ClientProxy>();
    Anmeldung signIn;
    Datenbank datenbank;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        starteServer();
    }

    public void starteServer()
    {
        try
        {
            signIn = new Anmeldung(this);
            Thread t = new Thread(signIn);
            t.start();
            datenbank = new Datenbank();
            datenbank.connectionStarten();
        } catch (Exception e) {
            System.err.println("Server konnte nicht gestartet werden! Fehler: " + e.getMessage());
        }
    }

    public void MessageAll(String s)
    {
        for(ClientProxy cp : clientList)
            cp.schreiben(s);
    }
}
