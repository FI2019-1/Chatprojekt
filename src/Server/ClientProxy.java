package Server;

import Client.BenutzerAnmeldeDaten;
import Client.Nachricht;
import Serialize.Serializer;

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
        benutzer = new Benutzer();
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

    public void setzeUsername(String s)
    {
        String username = s.substring(s.indexOf(";") + 1);
        benutzer.setBenutzername(username);
        c.addToDefaultGruppe(this);
    }
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
        gruppenraum.pruefePasswort2(this, passwort, gruppenraum.getGruppenname());
    }

    private Boolean anmelden(String benutzername, int passwort) {
        try
        {

            if (c.getDatenbank().userpasswortAbfragen(benutzername) == passwort)
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

            BenutzerAnmeldeDaten anmeldeDaten = (BenutzerAnmeldeDaten)n;
            System.out.println(anmeldeDaten.getBenutzername());

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void empfangen()
    {
        System.out.println("angekommen");
        int fileId = 0;
        try
        {
            System.out.println("g");
            //System.out.println(dis.read());
            DataInputStream dis = new DataInputStream(client.getInputStream());
            int fileNameLength = dis.readInt();
            System.out.println(fileNameLength);
            System.out.println("h");
            if(fileNameLength > 0)
            {
                System.out.println("4");
                byte[] fileNameBytes = new byte[fileNameLength];
                dis.readFully(fileNameBytes, 0, fileNameBytes.length);
                String fileName = new String(fileNameBytes);
                int fileContentLength = dis.readInt();
                if (fileContentLength > 0)
                {
                    System.out.println("5");
                    byte[] fileContentBytes = new byte[fileContentLength];
                    dis.readFully(fileContentBytes, 0, fileContentBytes.length);
                    //c.inhalt.setText(fileName);

                    daten.add(new Datei(fileId, fileName, fileContentBytes, getFileExtension(fileName)));
                    fileId++;
                    System.out.println("6");
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static String getFileExtension(String fileName)
    {
        int i = fileName.lastIndexOf('.');
        if (i > 0)
        {
            return fileName.substring(i + 1);
        } else {
            return "No extension found.";
        }
    }

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
}
