package Client;

import Serialize.*;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class ClientProxy implements Runnable
{
    private PrintWriter writer;
    private BufferedReader reader;
    private Serializer serializer;
    AnmeldeController anmeldeController;


    private Socket client;

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    private Benutzer benutzer;

    public Benutzer getBenutzer() {
        return benutzer;
    }
    public ClientController getC() {
        return c;
    }

    public void setC(ClientController c) {
        this.c = c;
    }

    ClientController c;

    public ClientProxy(Socket client, AnmeldeController anmeldeController)
    {

        this.anmeldeController = anmeldeController;

        this.c = c;
        this.client = client;
        serializer = new Serializer(client);
        try
        {
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            writer = new PrintWriter(out);
            reader = new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            System.out.println("Fehler in ClientProxy Constructor" + e.getMessage());
        }
    }

    public void run()
    {
        try
        {
            String s = null;
            //while ((s = reader.readLine()) != null)
            while (true)
            {
                objektempfangen();


                /*
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

            }
        }
        catch (Exception e)
        {
            System.out.println("Fehler in ClientProxy  run" + e.getMessage());
        }
    }

    private void objektempfangen()
    {
        try
        {
            Nachricht n = serializer.deserialisierung();
            filter(n);
            //System.out.println("hallo");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    private void filter(Nachricht n) {
        if(n.getType() == new Text().getType())
        {
            Text t = (Text) n;
            System.out.println(t.getText());
        }
        else if(n.getType() == new BenutzerAnmeldeDaten().getType())
        {
            Platform.runLater(() -> {
                try {
                    anmeldeController.bestaetigung((BenutzerAnmeldeDaten) n);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        else
        {
            System.out.println(n.getType());
        }

    }


    public void senden(Nachricht n)
    {
        serializer.serialisierung(n);
    }
}
