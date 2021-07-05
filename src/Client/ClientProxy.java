package Client;

import Gemeinsam.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
        Platform.runLater(() ->
        {
            javafx.scene.text.Text text = new javafx.scene.text.Text(super.getBenutzer().getBenutzername() +": " + t.getText() + "\n");
            text.setId("hash");
            text.getStyleClass().add("text");
            text.setOnMouseClicked(e ->
            {
                System.out.println("e.getSource()");
                c.textArea.setOnMouseClicked(ev -> {
                    if(ev.getTarget() instanceof javafx.scene.text.Text) {
                        javafx.scene.text.Text clicked = (javafx.scene.text.Text) ev.getTarget();
                        System.out.println(clicked);
                    }
                });
            });

            c.textArea.getChildren().add(text);
        });

    }

    @Override
    public void chatFileVerwalten(ChatFile t) {
        Platform.runLater(() ->
        {
            javafx.scene.text.Text text = new javafx.scene.text.Text(super.getBenutzer().getBenutzername() +": " + t.toString() + "\n");
            text.setId("hash");
            text.getStyleClass().add("text");
            text.setOnMouseClicked(e ->
            {
                System.out.println("e.getSource()");
                c.textArea.setOnMouseClicked(ev -> {
                    if(ev.getTarget() instanceof javafx.scene.text.Text) {
                        javafx.scene.text.Text clicked = (javafx.scene.text.Text) ev.getTarget();
                        System.out.println(clicked);
                    }
                });
            });
            c.textArea.getChildren().add(text);
        });

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
