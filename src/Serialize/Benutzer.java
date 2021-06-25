package Serialize;

import java.lang.reflect.Type;

public class Benutzer extends Nachricht
{
    private String benutzername;

    public Benutzer(String benutzername) {
        super();
    }

    public String getBenutzername()
    {
        return benutzername;
    }

    public void setBenutzername(String benutzername)
    {
        this.benutzername = benutzername;
    }

    public Benutzer(String benutzername)
    {
        this.benutzername = benutzername;
    }

    @Override
    public Type getType() {
        return this.getType();
    }
}