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
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT  passwort FROM User WHere Username like ");
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
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
