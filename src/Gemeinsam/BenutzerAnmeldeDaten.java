package Gemeinsam;

import java.lang.reflect.Type;

public class BenutzerAnmeldeDaten extends Nachricht {
    private String benutzername;
    private int passwort;



    private boolean bestaetigung = false;

    public boolean getBestaetigung() {
        return bestaetigung;
    }
    public void setBestaetigung(boolean bestaetigung) {
        this.bestaetigung = bestaetigung;
    }

    public BenutzerAnmeldeDaten()
    {

    }
    public BenutzerAnmeldeDaten(String benutzername, int passwort)
    {
        this.passwort = passwort;
        this.benutzername = benutzername;
    }
    public BenutzerAnmeldeDaten(String benutzername, int passwort, boolean bestaetigung)
    {
        this.bestaetigung = bestaetigung;
        this.passwort = passwort;
        this.benutzername = benutzername;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public int getPasswort() {
        return passwort;
    }

    @Override
    public Type getType() {
        return this.getClass();
    }
}
