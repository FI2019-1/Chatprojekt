package Server;

import java.net.ServerSocket;
import java.net.Socket;

public class Anmeldung implements Runnable
{
    Controller c;

    public Anmeldung(Controller c)
    {
        this.c = c;
    }

    @Override
    public void run()
    {
        try
        {
            ServerSocket server = new ServerSocket(5555);
            System.out.println("Server konnte gestartet werden!");

            while (true)
            {
                Socket client = server.accept();
                ClientProxy cp = new ClientProxy(client, c);

                Thread t = new Thread(cp);
                t.start();

                c.clientList.add(cp);
            }
        } catch (Exception e) {
            System.out.println("Fehler im Sign In");
        }
    }
}
