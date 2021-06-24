package Client;

import Serialize.Serializer;

import java.io.*;
import java.net.Socket;

public class ClientProxy implements Runnable
{
    private PrintWriter writer;
    private BufferedReader reader;
    private Serializer serializer;


    private Socket client;
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

    public ClientProxy(Socket client)
    {
        this.c = c;
        this.client = client;
        this.benutzer = new Benutzer();
        serializer = new Serializer(client);
        try
        {
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            writer = new PrintWriter(out);
            reader = new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            System.out.println("Fehler in ClientProxy Constructor");
        }
    }

    public void run()
    {
        try
        {
            String s = null;
            while ((s = reader.readLine()) != null)
            {
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
                }

            }
        }
        catch (Exception e)
        {
            System.out.println("Fehler in ClientProxy Constructor");
        }
    }

    public void schreiben(String s)
    {
        writer.write(s + "\n");
        writer.flush();
    }
    public void senden(Nachricht n)
    {
        serializer.serialisierung(n);
    }
}
