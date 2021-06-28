package Server;

import Serialize.Nachricht;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    private ArrayList<Gruppenraum> gruppenraumList;
    private Anmeldung signIn;
    protected Gruppenraum defaultgruppenraum;
    private ArrayList<ClientProxy> clientList;

    public Datenbank getDatenbank() {
        return datenbank;
    }

    private Datenbank datenbank;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        starteServer();
        clientList = new ArrayList<>();
        gruppenraumList = new ArrayList<>();
        defaultgruppenraum = new Gruppenraum("Default");
        gruppenraumList.add(defaultgruppenraum);
    }
    public Gruppenraum getGruppenraum()
    {
        return defaultgruppenraum;
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

    public void addToDefaultGruppe(ClientProxy cp)
    {
        defaultgruppenraum.getClientList().add(cp);
    }

    public void removeFromDefaultGruppe(ClientProxy cp)
    {
        defaultgruppenraum.getClientList().remove(cp);
    }

    public void addeGruppenraum(ClientProxy cp)
    {
        Gruppenraum g2 = null;
        boolean vorhanden = false;

        for(Gruppenraum g : gruppenraumList)
        {
            if(g.getGruppenname().equals(cp.getGruppenraumname()))
            {
                vorhanden = true;
                g2 = g;
            }
        }
        if(vorhanden == false)
        {
            g2 = new Gruppenraum(cp.getGruppenraumname());
            gruppenraumList.add(g2);
        }
        addeUser(g2, cp);
    }
    public void addeUser(Gruppenraum g2, ClientProxy cp)
    {

        g2.addClient(cp);
        // Bitte anpassen:
        // cp.speichereGruppenraum(g2);
    }

    public void entferneUser(ClientProxy cp)
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
    protected void sendenAnAlle(Nachricht n)
    {
        for (ClientProxy c :clientList)
        {
            c.senden(n);
        }
    }
    protected void addClient(ClientProxy clientProxy)
    {
        clientList.add(clientProxy);
    }
}
