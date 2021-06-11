package Server;

import java.util.ArrayList;
import java.util.Iterator;
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
        /*
        for(ClientProxy cp2 : clientList)
        {
            if(cp1.getUsername().equals(cp2.getUsername()))
            {
                clientList.remove(cp2);
                System.out.println(cp2.username);
            }
        }
        */

        List<ClientProxy> toRemove = new ArrayList<ClientProxy>();
        for (ClientProxy cp2 : clientList)
        {
            if (cp1.getUsername().equals(cp2.getUsername()))
            {
                toRemove.add(cp2);
            }
        }
        clientList.removeAll(toRemove);

       
        /*
        Iterator<ClientProxy> iter = clientList.iterator();
        System.out.println("Jetzt iterieren");
        try
        {
            while (iter.hasNext())
            {
                ClientProxy client = iter.next();
                System.out.println(iter.next().getUsername());

                if (iter.next().getUsername().equals(cp1.getUsername()))
                {
                    iter.
                    System.out.println("User geht");
                }


            }
        }
        catch(Exception e)
        {
            e.getCause();
            e.printStackTrace();
            System.out.println("Kein Element zum Iterieren");
          //  clientList.remove(0);

        }

         */
    }

    public String getGruppenname()
    {

        return gruppenname;
    }

    private void MessageAll(String s)
    {
        for(ClientProxy cp : clientList)
        {
            cp.schreiben(s);
        }
    }
    public void MessageGruppe(String s)
    {
        for(ClientProxy cp : clientList)
        {
            cp.schreiben(s);
        }
    }
}
