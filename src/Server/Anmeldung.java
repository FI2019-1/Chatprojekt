package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Anmeldung implements Runnable
{
    private Controller c;


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
                //DataInputStream dis = new DataInputStream(client.getInputStream());
                ClientProxy cp = new ClientProxy(client, c);
                System.out.println("Neuer Client erstellt");
                Thread t = new Thread(cp);
                t.start();
            }
        } catch (Exception e) {
            System.out.println("Fehler beim annehmen von Clients: " + e.getMessage());
        }
    }


}
