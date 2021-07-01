package Server;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    ArrayList<Gruppenraum> gruppenraumList;
    ArrayList<ClientProxy> cpListe;
    Anmeldung signIn;
    Gruppenraum defaultgruppenraum;
    Datenbank datenbank;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        starteServer();
        gruppenraumList = new ArrayList<Gruppenraum>();
        cpListe = new ArrayList<ClientProxy>();
        defaultgruppenraum = new Gruppenraum("Default");
        gruppenraumList.add(defaultgruppenraum);

    }
    public Gruppenraum getGruppenraum()
    {
        return defaultgruppenraum;
    }

    public void verschickeGruppenListeAnClients() throws IOException {
        for (ClientProxy cp: cpListe)
        {
            /*System.out.println(cp.benutzer.getBenutzername());
            for (Gruppenraum gr:gruppenraumList) {
                System.out.println(gr);
            }*/
            cp.schreiben(gruppenraumList);
            System.out.println("Die Liste wurde verschickt");
        }
    }

    public void starteServer()
    {
        try
        {
            signIn = new Anmeldung(this);
            Thread t = new Thread(signIn);
            t.start();
            /*
            datenbank = new Datenbank();
            datenbank.connectionStarten();
             */
        } catch (Exception e) {
            System.err.println("Server konnte nicht gestartet werden! Fehler: " + e.getMessage());
        }
    }

    public void addToDefaultGruppe(ClientProxy cp) throws IOException {
        defaultgruppenraum.getClientList().add(cp);
        verschickeGruppenListeAnClients();
    }

    public void removeFromDefaultGruppe(ClientProxy cp)
    {
        defaultgruppenraum.getClientList().remove(cp);
    }

    public void addeGruppenraum(ClientProxy cp) throws IOException {
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
            verschickeGruppenListeAnClients();
        }
        addeUser(g2, cp);

    }
    public void addeUser(Gruppenraum g2, ClientProxy cp) throws IOException {

        g2.addClient(cp);
        g2.MessageGruppe(cp.getUsername() + " ist der Gruppe beigetreten.");
        cp.speichereGruppenraum(g2);
    }

    public void entferneUser(ClientProxy cp) throws IOException {

        if(cp.getGruppenraum().getClientList().size() == 1 && cp.getGruppenraum().getGruppenname().equals("Default") == false)
        {
            gruppenraumList.remove(cp.getGruppenraum());
        }
        else
        {
            cp.getGruppenraum().getClientList().remove(cp);
            cp.getGruppenraum().MessageGruppe(cp.getUsername() + " hat die Gruppe verlassen.");

        }

    }
}
