package Server;

public class Benutzer
{
    private String benutzername;
    private Boolean angemeldet;

    public Benutzer(String benutzername)
    {
        this.benutzername = benutzername;
    }



    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }
}
