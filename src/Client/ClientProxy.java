package Client;

import java.io.*;
import java.net.Socket;

public class ClientProxy implements Runnable
{
    PrintWriter writer;
    BufferedReader reader;
    Socket client;
    ClientController c;

    public ClientProxy(Socket client, ClientController c)
    {
        this.c = c;
        this.client = client;

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
                c.textWindow.appendText(s + "\n");
            }
        }
        catch (Exception e)
        {
            System.out.println("Fehler in ClientProxy Constructor");
        }
    }

    public  void schreiben(String s)
    {
        writer.write(s + "\n");
        writer.flush();
    }
}
