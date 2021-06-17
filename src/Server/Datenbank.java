package Server;

import org.hsqldb.rights.User;

import java.sql.*;

public class Datenbank
{
    private Connection con = null;
    private Statement stmt = null;

    public  Datenbank()
    {
        try
        {
            connectionStarten();
            //userpasswortAbfragen("Test");
            //System.out.println(userregister("user", 1233));
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }

    protected void connectionStarten() throws SQLException
    {
        con = DriverManager.getConnection("jdbc:ucanaccess://C://Schule/JavaFX/Chatprojekt/src/Server/Datenbank.accdb");
        stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    protected boolean userregister(String username, Integer passwort) throws SQLException
    {
        ResultSet rs = stmt.executeQuery("SELECT * FROM User Where Username like '" + username + "'");
        rs.last();
        if(rs.getRow() == 0)
        {
            rs.moveToInsertRow();
            rs.updateInt("passwort", passwort);
            rs.updateString(2, username);
            rs.insertRow();
        }
        else
        {
            return false;
        }
        return true;
    }
    protected int userpasswortAbfragen(String Username) throws SQLException
    {
        ResultSet rs = stmt.executeQuery("SELECT  passwort FROM User WHere Username like '" + Username + "'");
        rs.first();
        return rs.getInt("passwort");
    }
}
