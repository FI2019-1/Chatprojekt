package Server;


public class Ratelimiter
{
    private final long ratelimit = 20;
    private long count = ratelimit;
    private double timestamp;


    public Ratelimiter()
    {
        this.timestamp = System.currentTimeMillis();
    }

    public long getCount() {
        return count;
    }

    public void berechneRate()
    {
        long aktuelleAnfrage = System.currentTimeMillis();
        double zeitdifferenz = (aktuelleAnfrage -timestamp);
        if(zeitdifferenz>1000)
        {
            count = ratelimit;
            timestamp = aktuelleAnfrage;
        }
        else
        {
            count--;
        }
    }

}
