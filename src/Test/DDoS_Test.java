package Test;

import Client.AnmeldeController;

import java.util.ArrayList;

/*
DDoS||DoS Test zum Testen von Server
 */
public class DDoS_Test
{
    private final int anzahlAnfragen=500;
    private final int anzahlProSek=21;
    private final int anzahlClient=5;
    private final ArrayList<AnmeldeController> anmeldeClients = new ArrayList<>();


    public static void main(String[] args) {
        DDoS_Test ddos = new DDoS_Test();
        ddos.dos();
        //ddos.ddos();

    }

    public void erstelleClients()
    {
        for(int i=0;i<anzahlClient;i++)
        {
            AnmeldeController anmelden = new AnmeldeController();
            anmelden.starteClient();
            anmeldeClients.add(anmelden);
        }
    }

    public void dos()
    {
        AnmeldeController anmelden = new AnmeldeController();
        anmelden.starteClient();

        Thread t = new Thread(() -> {
            System.out.println("Thread Running");
            for (int i =0;i<anzahlAnfragen;i++)
            {
                anmelden.getCp().schreiben("Hallo "+i);
                try {
                    System.out.println(1000/anzahlProSek);
                    Thread.sleep(1000/anzahlProSek);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        t.start();
    }

    public void ddos()
    {
        erstelleClients();
        Thread thread = new Thread(() -> {
            System.out.println("Thread Running");
            for (int i = 0; i < anzahlAnfragen; i++) {
                for (AnmeldeController anmelden:anmeldeClients)
                {
                    anmelden.getCp().schreiben("Hallo " + i);
                }
                try {
                    Thread.sleep(1000/anzahlProSek);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}

