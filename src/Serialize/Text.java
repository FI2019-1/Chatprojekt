package Serialize;

import java.lang.reflect.Type;

public class Text extends Nachricht{

    private String text;
    private Benutzer user;


    public Text (String text, Benutzer user)
    {
        this.user = user;
        this.text = text;
    }
    public Text ()
    {
    }

    public Benutzer getUser() {
        return user;
    }

    public void setUser(Benutzer user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    @Override
    public Type getType() {
        //System.out.println(this.getType().toString());
        return this.getClass();
    }
}
