package Server;

import java.io.*;
import java.net.Socket;

public class ClientProxy implements Runnable
{
    Controller c;
    PrintWriter writer;
    BufferedReader reader;
    Socket client;
    String username;
    String gruppe;
    Gruppenraum gruppenraum;

    public ClientProxy(Socket client, Controller c)
    {
        this.c = c;
        this.client = client;

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
    public String getGruppe()
    {
        return gruppe;
    }
    public String getUsername()
    {
        return username;
    }


    public void run()
    {
          try
          {
            String s = null;



            while ((s = reader.readLine()) != null)
            {
                if(s.startsWith(";"))
                {


                    String username = s.substring(s.indexOf(";") + 1, s.indexOf(";;"));
                    this.username = username;
                    String gruppe = s.substring(s.indexOf(";;") + 2);
                    this.gruppe = gruppe;




                    //c.setzeAnfangsdaten();

                  //  c.verlasseGruppe(this);
                    c.pruefeGruppe(this);


                }
                else if(s.startsWith(","))
                {
                    String username = s.substring(s.indexOf(",") + 1, s.indexOf(";;"));
                    this.username = username;
                    String gruppe = s.substring(s.indexOf(";;") + 2);
                    this.gruppe = gruppe;




                    //c.setzeAnfangsdaten();

                    c.verlasseGruppe(this);
                 //   c.pruefeGruppe(this);
                }
                else
                {
                    //c.MessageAll(s, gruppe);
                    gruppenraum.MessageGruppe(s);
                }

            }
            //writer.close();
            //reader.close();
          }
          catch (Exception e)
          {
              e.printStackTrace();
              e.getCause();
              System.out.println("Fehler in ClientProxy Run");
          }
    }

    public void speichereGruppenraum(Gruppenraum gruppenraum)
    {
        this.gruppenraum = gruppenraum;
    }

    public void schreiben(String s)
    {
        writer.write(s + "\n");
        writer.flush();
    }
}
