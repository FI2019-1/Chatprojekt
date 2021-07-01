package Client;

import Server.Gruppenraum;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientProxy implements Runnable
{
    //PrintWriter writer;
    //BufferedReader reader;
    Socket client;
    ClientController c;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ArrayList<Client.Gruppenraum> gruppenraumListe = new ArrayList<Client.Gruppenraum>();
    boolean istDa = false;

    public ClientProxy(Socket client, ClientController c)
    {
        this.c = c;
        this.client = client;

        try
        {
            /*OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            writer = new PrintWriter(out);
            reader = new BufferedReader(new InputStreamReader(in));*/
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());

        } catch (Exception e) {
            System.out.println("Fehler in ClientProxy Constructor");
        }
    }

    public void run()
    {
        try
        {
            Object s = null;

            while ((s = ois.readObject()) != null)
            {
                if(s instanceof java.lang.String)
                {

                    //System.out.println(s);
                    if(s.toString().startsWith(";"))
                    {
                        c.verwehreZugriff(s.toString());
                    }
                    else if(s.toString().startsWith(","))
                    {
                        c.erlaubeZugriff(s.toString());
                    }
                    else
                    {
                        c.textWindow.appendText(s.toString());
                    }
                }
                else if(s instanceof Server.Gruppenraum)
                {
                    for (Client.Gruppenraum gr:gruppenraumListe)
                    {
                      //  System.out.println("Von Client: " + gr);
                      //  System.out.println("Vom Server: " + ((Server.Gruppenraum) s).getGruppenname());
                        if(gr.getGruppenname().equals(((Server.Gruppenraum) s).getGruppenname()))
                        {
                            istDa = true;
                        }

                    }
                    if(istDa == false)
                    {
                        gruppenraumListe.add(new Client.Gruppenraum(((Gruppenraum) s).getGruppenname()));

                    }
                    istDa = false;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            c.comboBoxGruppen.getItems().clear();
                            for (Client.Gruppenraum ob : gruppenraumListe)
                            {
                                //System.out.println("Das ist von Server Liste\n" + ob);
                                c.comboBoxGruppen.getItems().add(ob);
                            }
                        }
                    });
                }
                /*else if(s instanceof java.util.ArrayList)
                {
                    ArrayList <Server.Gruppenraum> lsgr = (ArrayList<Gruppenraum>) s;
                    for (Server.Gruppenraum gr:lsgr)
                    {
                        System.out.println("Auf Client Seite: " + gr);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run()
                        {
                            c.comboBoxGruppen.getItems().clear();
                            for (Server.Gruppenraum ob : lsgr)
                            {
                                //System.out.println("Das ist von Server Liste\n" + ob);
                                c.comboBoxGruppen.getItems().add(new Client.Gruppenraum(ob.getGruppenname()));
                            }
                        }
                    });


                    /*c.comboBoxGruppen.getItems().clear();
                    c.comboBoxGruppen.setItems(c.getGrListefuerComboBox());
                    System.out.println(c.getGrListefuerComboBox().get(0));

                }*/

            }

        }
        catch (Exception e)
        {
            System.out.println("Fehler in ClientProxy Constructor " + e);
        }
    }

    public void schreiben(String s) throws IOException {
        oos.writeObject(s);
        oos.flush();
    }
}
