package Gemeinsam;

import java.lang.reflect.Type;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;

public class Text extends Nachricht{

    private String text;
    private Benutzer user;
    private String sendTime;


    public Text (String text, Benutzer user)
    {
        this.user = user;
        this.text = text;
        gelesen = false;
        bestaetigung = 0;
        sendTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
        hashCode = hashCode();
    }
    public Text ()
    {
    }

    public void setGelesen()
    {
        gelesen = true;
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
    @Override
    public int hashCode()
    {
        int hashWert = 17;
        hashWert = Objects.hash(hashWert, text);
        hashWert = Objects.hash(hashWert, user);
        hashWert = Objects.hash(hashWert, sendTime);
        return hashWert;
    }
}
