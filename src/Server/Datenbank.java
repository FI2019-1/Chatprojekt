package Server;

import java.sql.*;

public class Datenbank
{
    private Connection con = null;

    public  Datenbank()
    {
        try
        {
            con = DriverManager.getConnection("jdbc:ucanaccess://C://Schule/JavaFX/Chatprojekt/src/Server/Datenbank.accdb");
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        //con = DriverManager.getConnection("");
    }
    protected boolean eintragen()
    {
        return true;
    }
    protected int abfragen(String text)
    {
        return 0;
    }
}
