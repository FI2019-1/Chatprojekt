package Client;

public class BenutzerAnmeldeDaten extends Nachricht
{
    private String benutzername;
    private int passwort;



    private boolean bestaetigung = false;

    public boolean getBestaetigung() {
        return bestaetigung;
    }
    public void setBestaetigung(boolean bestaetigung) {
        this.bestaetigung = bestaetigung;
    }

    public BenutzerAnmeldeDaten(String benutzername, int passwort)
    {
        this.passwort = passwort;
        this.benutzername = benutzername;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public int getPasswort() {
        return passwort;
    }
}
