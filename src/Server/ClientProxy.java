package Server;

import java.io.*;
import java.net.Socket;

public class ClientProxy implements Runnable
{
    Controller c;
    PrintWriter writer;
    BufferedReader reader;
    Socket client;
    Benutzer benutzer;

    public ClientProxy(Socket client, Controller c)
    {
        this.c = c;
        this.client = client;

        try
        {
            OutputStream out = client.getOutputStream();
            writer = new PrintWriter(out);
            InputStream in = client.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            System.out.println("Fehler im ClientProxy");
        }
    }

    private Boolean anmelden(String benutzername, int passwort)
    {
        String benutzer = "Benutzer";
        String pw = null;

        if(benutzer == null)
        {
            //user nicht in Datenbank gefunden
            return false;
        }
        else
        {
            pw = "123";
        }

        if(benutzer.equals(benutzername) && passwort == pw.hashCode())
        {
            this.benutzer = new Benutzer(benutzername);
            System.out.println("true");

            return true;
        }
        return false;
    }

    public void run()
    {
        try
        {
            String s = null;
            while ((s = reader.readLine()) != null)
                if(benutzer == null)
                {
                    //erst anmelden
                }
                else
                {
                    c.MessageAll(s);
                }


            //writer.close();
            //reader.close();
        } catch (Exception e) {
            System.out.println("Fehler in ClientProxy Run");
        }
    }

    public void schreiben(String s)
    {
        writer.write(s + "\n");
        writer.flush();
    }
}
