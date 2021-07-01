package Client;

import Gemeinsam.*;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class ClientProxy extends Proxy
{
    private AnmeldeController anmeldeController;
    private ClientController c;

    /*
    public void setBenutzer(Benutzer benutzer) {
        super.setBenutzer(benutzer);
    }*/

    public ClientController getC() {
        return c;
    }

    public void setC(ClientController c) {
        this.c = c;
    }
    public Benutzer getBenutzer() {
        return super.getBenutzer();
    }

    public ClientProxy(Socket client, AnmeldeController anmeldeController)
    {
        super(client);
        this.anmeldeController = anmeldeController;
        this.c = c;
    }
/*

                Bitte aufräumen (run):
                System.out.println(s);
                if(s.startsWith(";"))
                {
                    c.verwehreZugriff(s);
                }
                else if(s.startsWith(","))
                {
                    c.erlaubeZugriff(s);
                }
                else
                {
                    c.textWindow.appendText(s + "\n");
                }*/

    public void textNachrichtVerwalten(Text t)
    {
        c.textWindow.appendText(super.getBenutzer().getBenutzername() +": " + t.getText() + "\n");
    }

    @Override
    public void registerdatenVerwalten(BenutzerRegisterDaten t) {

    }

    public void benutzerdatenverwalten(BenutzerAnmeldeDaten anmeldeDaten)
    {
        Platform.runLater(() -> {
            try
            {
                anmeldeController.bestaetigung(anmeldeDaten);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}
