package Server;

import java.io.*;
import java.net.Socket;

public class ClientProxy implements Runnable
{
    Controller c;
    PrintWriter writer;
    BufferedReader reader;
    Socket client;

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

    public void run()
    {
        try
        {
            String s = null;
            while ((s = reader.readLine()) != null)
                c.MessageAll(s);

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
