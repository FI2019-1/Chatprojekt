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
            new Datenbank();
        } catch (Exception e) {
            System.out.println("Server konnte nicht gestartet werden!");
        }
    }

    public void MessageAll(String s)
    {
        for(ClientProxy cp : clientList)
            cp.schreiben(s);
    }
}
