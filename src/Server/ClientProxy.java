package Server;

import Gemeinsam.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientProxy extends Proxy
{
    private Controller c;
    private String gruppenraumname;
    private Gruppenraum gruppenraum;
    static ArrayList<Datei> daten = new ArrayList<>();

    public String getGruppenraumname()
    {
        return gruppenraumname;
    }
    public Gruppenraum getGruppenraum()
    {
        return gruppenraum;
    }

    public ClientProxy(Socket client, Controller c)
    {
        super(client);
        this.c = c;
        this.gruppenraum = c.defaultgruppenraum;
        this.gruppenraumname = c.defaultgruppenraum.getGruppenname();
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

    public void benutzerdatenverwalten(BenutzerAnmeldeDaten anmeldeDaten) {
        try
        {

            if (c.getDatenbank().userpasswortAbfragen(anmeldeDaten.getBenutzername()) == anmeldeDaten.getPasswort())
            {
                super.setBenutzer(new Benutzer(anmeldeDaten.getBenutzername()));
                anmeldeDaten.setBestaetigung(true);
                super.senden(anmeldeDaten);
                //serializer.serialisierung(anmeldeDaten);
            }
            else
            {
                anmeldeDaten.setBestaetigung(false);
                super.senden(anmeldeDaten);
                //serializer.serialisierung(anmeldeDaten);
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            //return false;
        }
    }

    @Override
    public void textNachrichtVerwalten(Text t)
    {
        c.sendenAnAlle(t);
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
