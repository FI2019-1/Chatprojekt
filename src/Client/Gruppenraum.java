package Client;

import Gemeinsam.Benutzer;

public class Gruppenraum
{
    private ClientProxy cp;
    private String gruppenname;
    private String passwort;
    private Benutzer benutzer;

    public ClientProxy getCp()
    {
        return cp;
    }
    public String getGruppenname()
    {
        return gruppenname;
    }

    public void setCp(ClientProxy cp)
    {
        this.cp = cp;
    }
    public void setGruppenname(String gruppenname)
    {
        this.gruppenname = gruppenname;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public Gruppenraum()
    {

    }
}
