package Server;

import Serialize.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientProxy implements Runnable
{
    private Controller c;
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket client;
    private Benutzer benutzer;
    private String gruppenraumname;
    private Gruppenraum gruppenraum;
    private Serializer serializer;
    private String username;
    static ArrayList<Datei> daten = new ArrayList<>();

    public ClientProxy(Socket client, Controller c)
    {

        this.c = c;
        this.client = client;
        serializer = new Serializer(client);

        gruppenraum = c.defaultgruppenraum;
        gruppenraumname = c.defaultgruppenraum.getGruppenname();

        try
        {
            OutputStream out = client.getOutputStream();
            writer = new PrintWriter(out);
            InputStream in = client.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
        }
        catch (Exception e)
        {
            System.out.println("Fehler im ClientProxy" + e.getMessage());
        }
    }
    public String getGruppenraumname()
    {
        return gruppenraumname;
    }
    public Gruppenraum getGruppenraum()
    {
        return gruppenraum;
    }
    /* Funktioniert nicht mehr
    protected String getUsername()
    {
        return benutzer.getBenutzername();
    }

    public void setzeUsername(String s)
    {
        String username = s.substring(s.indexOf(";") + 1);
        benutzer.setBenutzername(username);
        c.addToDefaultGruppe(this);
    }*/
    public void setzeGruppennamen(String s)
    {
        String gruppenraumname = s.substring(s.indexOf(",") + 1);

        c.entferneUser(this);
        this.gruppenraumname = gruppenraumname;

        c.addeGruppenraum(this);
    }
    public void pruefePasswort(String s)
    {
        String passwort = s.substring(s.indexOf(".") + 1);
        //Bitte anpassen:
        // gruppenraum.pruefePasswort2(this, passwort, gruppenraum.getGruppenname());
    }

    private void anmelden(BenutzerAnmeldeDaten anmeldeDaten) {
        try
        {

            if (c.getDatenbank().userpasswortAbfragen(anmeldeDaten.getBenutzername()) == anmeldeDaten.getPasswort())
            {
                benutzer = new Benutzer(anmeldeDaten.getBenutzername());
                anmeldeDaten.setBestaetigung(true);
                serializer.serialisierung(anmeldeDaten);
            }
            else
            {
                anmeldeDaten.setBestaetigung(false);
                serializer.serialisierung(anmeldeDaten);
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            //return false;
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
                /*
                if(benutzer == null)
                {
                    //erst anmelden
                }
                else
                {
                */

                //  beginnen(s);
                //}
                //empfangen();
                objektempfangen();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
            System.out.println("Fehler in ClientProxy Run");
        }
    }

    private void objektempfangen()
    {
        try
        {
            Nachricht n = serializer.deserialisierung();



            filter(n);
            //System.out.println(anmeldeDaten.getBenutzername());

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
            anmelden((BenutzerAnmeldeDaten)n);

        }
        else
        {
            System.out.println(n.getType());
        }

    }

/* Irrelevant ---> Umschreiben
    private void beginnen(String s)
    {
        if(s.startsWith(";"))
        {
            setzeUsername(s);
        }
        else if (s.startsWith(","))
        {
            setzeGruppennamen(s);
        }
        else if (s.startsWith("."))
        {
            pruefePasswort(s);
        }
        else
        {
            //c.MessageAll(s, gruppe);
            gruppenraum.MessageGruppe(s);
        }
    }



    public void speichereGruppenraum(Gruppenraum gruppenraum)
    {
        this.gruppenraum = gruppenraum;
        this.gruppenraumname = gruppenraum.getGruppenname();
    }

    public void schreiben(String s)
    {
        writer.write(s + "\n");
        writer.flush();
    }

    public void verwehreZugriffClientseite()
    {
        schreiben(";" + gruppenraum.getGruppenname());
        c.entferneUser(this);
        gruppenraumname = c.defaultgruppenraum.getGruppenname();
        gruppenraum = c.defaultgruppenraum;
        c.addToDefaultGruppe(this);


    }
    public void erlaubeZugriffClientseite()
    {
        schreiben("," + gruppenraum.getGruppenname());
    }
    */

}
