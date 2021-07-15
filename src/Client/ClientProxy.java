package Client;

import Gemeinsam.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientProxy extends Proxy
{
    private AnmeldeController anmeldeController;
    private ClientController c;
    private ArrayList<Text> nachrichtenListe;

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
        nachrichtenListe = new ArrayList<Text>();
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
            text.setId(String.valueOf(t.getHashCode()));
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
        nachrichtenListe.add(t);
        Confirm conf = new Confirm(super.getBenutzer(), t.getHashCode());
        senden(conf);
        System.out.println("Confirm senden");
    }

    @Override
    public void chatFileVerwalten(ChatFile t) {
        Platform.runLater(() ->
        {
            javafx.scene.text.Text text = new javafx.scene.text.Text(super.getBenutzer().getBenutzername() +": " + t.toString() + "\n");
            text.setId(String.valueOf(t.getHashCode()));
            text.getStyleClass().add("text");
            text.setOnMouseClicked(e ->
            {
                System.out.println("e.getSource()");
                c.textArea.setOnMouseClicked(ev -> {
                    if(ev.getTarget() instanceof javafx.scene.text.Text) {
                        javafx.scene.text.Text clicked = (javafx.scene.text.Text) ev.getTarget();
                        clicked.setText(clicked.getText() + "clicked");
                        System.out.println(clicked);
                    }
                });
            });
            c.textArea.getChildren().add(text);
        });

    }

        @Override
    public void registerdatenVerwalten(BenutzerRegisterDaten t) {
        Platform.runLater(() -> {
            try
            {
                anmeldeController.registerbestaetigung(t);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
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

    @Override
    public void confirmEmpfangen(Confirm c) {
        System.out.println("confirmEmpfangen ClientProxy");
        for (Text t : nachrichtenListe)
        {
            if(c.hashCode()==t.hashCode())
            {
                t.bestaetigungGelesen();

                if(t.getUser() == super.getBenutzer())
                {
                    //bestätigung für "gesendet"
                }

                if(t.getBestaetigungen() == t.getGesendetAn())
                {
                    t.setGelesen();
                    System.out.println("Nachricht gelesen" + t.getHashCode());
                }
            }
        }
    }
}
