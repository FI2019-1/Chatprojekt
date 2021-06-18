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
    ArrayList<Gruppenraum> gruppenraumList;
    Anmeldung signIn;
    Datenbank datenbank;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        starteServer();
        gruppenraumList = new ArrayList<>();
        clientList = new ArrayList<>();
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

    public void MessageAll(String s, String g)
    {
        /*
        for(ClientProxy cp : clientList)
        {
            cp.schreiben(s);

        }
         */

        for(ClientProxy cp : clientList)
        {
            if(cp.getGruppe().equals(g))
            {
                cp.schreiben(s);
            }
        }

    }

    public void pruefeGruppe(ClientProxy cp)
    {
        Gruppenraum g2 = null;
        boolean vorhanden = false;



        for(Gruppenraum g : gruppenraumList)
        {

            if(g.getGruppenname().equals(cp.getGruppe()))
            {
                vorhanden = true;
                g2 = g;
            }
        }


        if(vorhanden == false)
        {
            g2 = new Gruppenraum(cp.getGruppe());
            gruppenraumList.add(g2);


           // System.out.println(gruppe);
        }
        g2.addClient(cp);
        cp.speichereGruppenraum(g2);

    }
    public void verlasseGruppe(ClientProxy cp)
    {
        if(cp.getGruppenraum().getClientList().size() == 1 && cp.getGruppenraum().getGruppenname().equals("Default") == false)
        {
            gruppenraumList.remove(cp.getGruppenraum());
        }
        else
        {
            cp.getGruppenraum().getClientList().remove(cp);
        }
    }
}
