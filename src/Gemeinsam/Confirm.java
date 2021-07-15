package Gemeinsam;

import java.lang.reflect.Type;

public class Confirm extends Nachricht{
    private Benutzer user;

    public Confirm(Benutzer user, int hashCode)
    {
        this.user = user;
        this.hashCode = hashCode;
    }

    public Confirm()
    {}

    @Override
    public Type getType() {
        return this.getClass();
    }
}
