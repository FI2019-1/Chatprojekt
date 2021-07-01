package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientProxy implements Runnable
{
    Controller c;
    //PrintWriter writer;
    //BufferedReader reader;
    Socket client;
    Benutzer benutzer;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    String gruppenraumname;
    Gruppenraum gruppenraum;

    public ClientProxy(Socket client, Controller c)
    {
        this.c = c;
        this.client = client;
        benutzer = new Benutzer();
        gruppenraum = c.defaultgruppenraum;
        gruppenraumname = c.defaultgruppenraum.getGruppenname();

        try
        {
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
            /*OutputStream out = client.getOutputStream();
            writer = new PrintWriter(out);
            InputStream in = client.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));*/
            //System.out.println(c.gruppenraumList.get(0));

        }
        catch (Exception e)
        {
            System.out.println("Fehler im ClientProxy");
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
    protected String getUsername()
    {
        return benutzer.getBenutzername();
    }

    public void setzeUsername(String s) throws IOException {
        String username = s.substring(s.indexOf(";") + 1);
        benutzer.setBenutzername(username);
        c.addToDefaultGruppe(this);
    }
    public void setzeGruppennamen(String s) throws IOException {
        String gruppenraumname = s.substring(s.indexOf(",") + 1);
        c.entferneUser(this);
        this.gruppenraumname = gruppenraumname;

        c.addeGruppenraum(this);
    }
    public void pruefePasswort(String s) throws IOException, InterruptedException {
        String passwort = s.substring(s.indexOf(".") + 1);
        gruppenraum.pruefePasswort2(this, passwort, gruppenraum.getGruppenname());
    }

    private Boolean anmelden(String benutzername, int passwort) {
        try
        {

            if (c.datenbank.userpasswortAbfragen(benutzername) == passwort)
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void run()
    {
        try
        {
            String s = null;
            while ((s = ((String) ois.readObject())) != null)
            {
                /*
                if(benutzer == null)
                {
                    //erst anmelden
                }
                else
                {
                */

                    beginnen(s);
               //}

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
            System.out.println("Fehler in ClientProxy Run");
        }
    }

    private void beginnen(String s) throws IOException, InterruptedException {
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
            gruppenraum.MessageGruppe(s);
        }
    }



    public void speichereGruppenraum(Gruppenraum gruppenraum)
    {
        this.gruppenraum = gruppenraum;
        this.gruppenraumname = gruppenraum.getGruppenname();
    }
    public void schreiben(ArrayList arLs) throws IOException {
        if(arLs != null)
        {
            for (Object gr: arLs)
            {
                System.out.println("Auf Server Seite: " + gr);
                oos.writeObject(gr);
                oos.flush();
            }

        }

    }
    public void schreiben(String s) throws IOException {
        if(s != null)
        {
            oos.writeObject(s + "\n");
        }


    }

    public void verwehreZugriffClientseite() throws IOException {
        schreiben(";" + gruppenraum.getGruppenname());
        c.entferneUser(this);
        gruppenraumname = c.defaultgruppenraum.getGruppenname();
        gruppenraum = c.defaultgruppenraum;
        c.addToDefaultGruppe(this);

    }
    public void erlaubeZugriffClientseite() throws IOException {
        schreiben("," + gruppenraum.getGruppenname());
    }
}
