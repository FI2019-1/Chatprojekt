package Server;

import javafx.stage.Modality;
import javafx.stage.Stage;
import net.ucanaccess.jdbc.DeleteResultSet;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientProxy implements Runnable {
    private Controller c;
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket client;
    private Benutzer benutzer;
    private String gruppenraumname;
    private Gruppenraum gruppenraum;
    private String username;
    private Datenbank datenbank;
    private Ratelimiter ratelimiter;
    private int anzahltimeout;

    public ClientProxy(Socket client, Controller c) {
        this.c = c;
        this.client = client;
        benutzer = new Benutzer();
        ratelimiter = new Ratelimiter();

        gruppenraum = c.defaultgruppenraum;
        gruppenraumname = c.defaultgruppenraum.getGruppenname();
        try {
            OutputStream out = client.getOutputStream();
            writer = new PrintWriter(out);
            InputStream in = client.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            System.out.println("Fehler im ClientProxy");
        }
    }

    public String getGruppenraumname() {
        return gruppenraumname;
    }

    public Gruppenraum getGruppenraum() {
        return gruppenraum;
    }

    protected String getUsername() {
        return benutzer.getBenutzername();
    }

    public void setzeUsername(String s) {
        String username = s.substring(s.indexOf(";") + 1);
        benutzer.setBenutzername(username);
        c.addToDefaultGruppe(this);
    }

    public void setzeGruppennamen(String s) {
        String gruppenraumname = s.substring(s.indexOf(",") + 1);

        c.entferneUser(this);
        this.gruppenraumname = gruppenraumname;

        c.addeGruppenraum(this);
    }

    public void pruefePasswort(String s) {
        String passwort = s.substring(s.indexOf(".") + 1);
        gruppenraum.pruefePasswort2(this, passwort, gruppenraum.getGruppenname());
    }

    private Boolean anmelden(String benutzername, int passwort) {
        try {

            if (c.getDatenbank().userpasswortAbfragen(benutzername) == passwort) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public void run()
    {

        try
        {
            String s = null;

            while ((s = reader.readLine()) != null)
            {
                ratelimiter.berechneRate();
                if (ratelimiter.getCount() > 0)
                {
                    if (getUsername() == null)
                    {
                        //erst anmelden
                    }
                    else
                    {
                        beginnen(s);
                    }
                }
                else
                {
                    if (anzahltimeout < 3)
                    {
                        System.out.println("ratelimit überschritten");
                        anzahltimeout++;
                        Thread.sleep(10000);
                    }
                    else
                    {
                        if (getUsername()!= null)
                        {
                            c.getDatenbank().bannUser(getUsername());
                        }
                        reader.close();
                        writer.flush();
                        writer.close();
                        client.close();
                        return;
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            System.out.println("Fehler in ClientProxy Run");
        }

    }


    private void beginnen(String s) {
        if (s.startsWith(";")) {
            setzeUsername(s);
        } else if (s.startsWith(",")) {
            setzeGruppennamen(s);
        } else if (s.startsWith(".")) {
            pruefePasswort(s);
        } else {
            //c.MessageAll(s, gruppe);
            gruppenraum.MessageGruppe(s);
        }
    }

    public void speichereGruppenraum(Gruppenraum gruppenraum) {
        this.gruppenraum = gruppenraum;
        this.gruppenraumname = gruppenraum.getGruppenname();
    }

    public void schreiben(String s) {
        writer.write(s + "\n");
        writer.flush();
    }

    public void verwehreZugriffClientseite() {
        schreiben(";" + gruppenraum.getGruppenname());
        c.entferneUser(this);
        gruppenraumname = c.defaultgruppenraum.getGruppenname();
        gruppenraum = c.defaultgruppenraum;
        c.addToDefaultGruppe(this);


    }

    public void erlaubeZugriffClientseite() {
        schreiben("," + gruppenraum.getGruppenname());
    }
}
