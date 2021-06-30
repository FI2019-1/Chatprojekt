package Server;

import Client.ClientController;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;


public class Anmeldung implements Runnable
{
    Controller c;
    //neu
    HashMap<InetAddress, LocalDateTime> users = new HashMap<InetAddress, LocalDateTime>();
    //ArrayList<InetAddress> getimeoutet = new ArrayList<InetAddress>();
    int geblockt;
    //SSLServerSocket server; //position geändert
    private static final String[] protocols = new String[] {"TLSv1.3"};
    private static final String[] cipher_suites = new String[] {"TLS_AES_128_GCM_SHA256"};


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
            //neu
            //evtl ist hier der Fehler
            //SSLServerSocketFactory sslServerFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            //SSLServerSocket server = (SSLServerSocket)sslServerFactory.createServerSocket(5555);
            //server.setNeedClientAuth(true);
            //server.setNeedClientAuth(false); //dann bestehende Verbindung durch host unterbrochen
            //server.setEnabledProtocols(protocols);
            //server.setEnabledCipherSuites(cipher_suites);
            //server.setEnabledCipherSuites(new String[] { "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256" });
            //server.setEnabledProtocols(new String[] { "TLSv1.2" });

            System.out.println("Server konnte gestartet werden!");
            //neu ende

            while (true)
            {
                Socket client = (Socket)server.accept(); //neu

                geblockt = ddos(client); //ddos Methode wird aufgerufen
                if(geblockt == 1)
                {
                    ClientProxy cp = new ClientProxy(client, c);

                    Thread t = new Thread(cp);
                    t.start();

                    c.clientList.add(cp);
                }
                else if(geblockt == 0)
                {
                    System.out.println("Du bist im moment vom System ausgeschlossen");
                }
            }
        } catch (Exception e) {
            System.out.println("Fehler im Sign In");
            e.printStackTrace();
        }
    }

    public int ddos(Socket client) //Blacklist noch hinzufügen neu
    {
        InetAddress ip;
        LocalDateTime zeit;
        int zurueckwert = 1;

        ip = client.getLocalAddress();
        System.out.print(ip);
        System.out.println("\n");
        zeit = LocalDateTime.now();

        for(InetAddress ipA : users.keySet())
        {
            System.out.println("1");
            if(ip == ipA && ChronoUnit.SECONDS.between(zeit, users.get(ipA)) < 5) //Alternative ip.toString().equals(ipA.toString())
            {
                System.out.println("2.1");
                zurueckwert = 1; //Anmeldung schlägt fehl
                //getimeoutet.add(ip);
            }
            else
            {
                System.out.println("2.2");
                users.put(ip,zeit);
                zurueckwert = 1; //Anmeldung ist erfolgreich
            }

        }
        return zurueckwert;
    }
}
