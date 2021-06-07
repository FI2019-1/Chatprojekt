package Client;

public class BenutzerAnmeldeDaten extends Nachricht
{
    private String benutzername;
    private String passwort;


    public BenutzerAnmeldeDaten(String benutzername, String passwort)
    {
        this.passwort = passwort;
        this.benutzername = benutzername;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public String getPasswort() {
        return passwort;
    }
}
