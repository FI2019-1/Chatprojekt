package Gemeinsam;

import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;

public abstract class Proxy implements Runnable
{
    private Serializer serializer;
    private Socket client;
    private Benutzer benutzer;

    public void setBenutzer(Benutzer b)
    {
        this.benutzer = b;
    }
    public Benutzer getBenutzer() {
        return benutzer;
    }

    public Socket getClient() {
        return client;
    }

    protected Serializer getSerialiser() { return serializer;}

    public Proxy(Socket client)
    {
        this.client = client;
        this.serializer = new Serializer(client);

    }

    public void run()
    {
        try
        {
            while (true)
            {
                objektempfangen();
            }
        }
        catch (Exception e)
        {
            System.out.println("Fehler in Proxy run" + e.getMessage());
        }
    }

    private void objektempfangen()
    {
        try
        {
            Nachricht n = serializer.deserialisierung();
            System.out.println(n.getClass());
            filter(n);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    private void filter(Nachricht n) {
        if(n.getType() == new Text().getType())
        {
            textNachrichtVerwalten((Text) n);
        }
        else if(n.getType() == new BenutzerAnmeldeDaten().getType())
        {
            benutzerdatenverwalten((BenutzerAnmeldeDaten) n);
        }
        else if(n.getType() == new BenutzerRegisterDaten().getType())
        {
            registerdatenVerwalten((BenutzerRegisterDaten) n);

        }
        else if (n.getType() == new Gemeinsam.ChatFile().getType())
        {
            System.out.println("ich bin eine File :D");
            ChatFile chatFile = (ChatFile)n;
            chatFileVerwalten(chatFile);
        }
        else
        {
            System.out.println(n.getType());
        }
    }

    public abstract void textNachrichtVerwalten(Text t);
    public abstract void chatFileVerwalten(ChatFile t);
    public abstract void registerdatenVerwalten(BenutzerRegisterDaten t);
    public abstract void benutzerdatenverwalten(BenutzerAnmeldeDaten n);

    public void senden(Nachricht n)
    {
        serializer.serialisierung(n);
    }
}
