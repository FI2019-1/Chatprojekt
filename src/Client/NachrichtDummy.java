package Client;

import java.time.LocalDateTime;
import java.util.Objects;

public class NachrichtDummy {
    String inhalt;
    LocalDateTime gesendet;
    int hashCode;

    public NachrichtDummy(String inhalt)
    {
        this.inhalt = inhalt;
        takeTime();
        this.hashCode = hashCode();
    }

    @Override
    public int hashCode()
    {
        int hashWert = 17;
        hashWert = Objects.hash(hashWert, inhalt);
        hashWert = Objects.hash(hashWert, gesendet);
        return hashWert;
    }

    public void takeTime()
    {
        gesendet = LocalDateTime.now();
    }

}
