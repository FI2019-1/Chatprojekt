package Gemeinsam;

import java.lang.reflect.Type;

public class BenutzerRegisterDaten extends BenutzerAnmeldeDaten
{
    public BenutzerRegisterDaten(String nutzer, int pw)
    {
        super(nutzer, pw);
    }
    public BenutzerRegisterDaten()
    {

    }
    @Override
    public Type getType() {
        return this.getClass();
    }
}
