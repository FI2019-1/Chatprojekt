package Server;

import java.util.ArrayList;
import java.util.List;

public class Gruppenraum
{
    private String gruppenname;
    private String passwort = null;
    private ArrayList<ClientProxy> clientList;

    public Gruppenraum(String gruppenname)
    {
        clientList = new ArrayList<ClientProxy>();
        this.gruppenname = gruppenname;
    }
    public void pruefePasswort2(ClientProxy cp, String passwort, String gruppenname)
    {
        if(gruppenname.equals("Default") == true)
        {
            cp.erlaubeZugriffClientseite();
        }
        else if(this.passwort == null)
        {
            this.passwort = passwort;
            cp.erlaubeZugriffClientseite();
        }
        else if(this.passwort.equals(passwort) == true)
        {
            cp.erlaubeZugriffClientseite();
        }
        else
        {
            cp.verwehreZugriffClientseite();
        }

    }

    public void addClient(ClientProxy cp)
    {
        clientList.add(cp);
    }

    public ArrayList<ClientProxy> getClientList()
    {
        return clientList;
    }

    public String getGruppenname()
    {
        return gruppenname;
    }

    public void MessageGruppe(String s)
    {
        for(ClientProxy cp : clientList)
        {
            System.out.println(s);
            cp.schreiben(s);
        }
    }
}
