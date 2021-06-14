package Server;

import java.util.ArrayList;
import java.util.List;

public class Gruppenraum
{
    private String gruppenname;
    private ArrayList<ClientProxy> clientList;

    public Gruppenraum(String gruppenname)
    {
        clientList = new ArrayList<ClientProxy>();
        this.gruppenname = gruppenname;
    }

    public void addClient(ClientProxy cp)
    {
        clientList.add(cp);
    }
    public void entferneClient(ClientProxy cp1)
    {
        List<ClientProxy> toRemove = new ArrayList<ClientProxy>();
        for (ClientProxy cp2 : clientList)
        {
            if (cp1.getUsername().equals(cp2.getUsername()))
            {
                toRemove.add(cp2);
            }
        }
        clientList.removeAll(toRemove);
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
        System.out.println("Hallo2");
        for(ClientProxy cp : clientList)
        {
            System.out.println(cp.getGruppenraum().getGruppenname() + "Hier");
            cp.schreiben(s);
        }
    }
}
