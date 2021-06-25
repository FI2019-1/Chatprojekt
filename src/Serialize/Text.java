package Serialize;

import java.lang.reflect.Type;

public class Text extends Nachricht{

    private String text;
    private Benutzer user;
    private String Type = "Text";

    public Text (String text)
    {
        this.text = text;
    }

    public Benutzer getUser() {
        return user;
    }

    public void setUser(Benutzer user) {
        this.user = user;
    }
    public void returnNachricht (Nachricht nachricht)
    {

    }

    @Override
    public Type getType() {
        System.out.println(this.getType().toString());
        return this.getType();

    }
}
