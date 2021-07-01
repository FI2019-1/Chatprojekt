package Server;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Gruppenraum implements Serializable
{
    private String gruppenname;
    private transient String passwort = null;
    private transient ArrayList<ClientProxy> clientList;

    public Gruppenraum(String gruppenname)
    {
        clientList = new ArrayList<ClientProxy>();
        this.gruppenname = gruppenname;
    }
    public void pruefePasswort2(ClientProxy cp, String passwort, String gruppenname) throws IOException, InterruptedException {
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

    public void MessageGruppe(String s) throws IOException {
        for(ClientProxy cp : clientList)
        {
            System.out.println(s);
            cp.schreiben(s);
        }
    }
    @Override
    public String toString()
    {
        return gruppenname;
    }
}
