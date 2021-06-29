package Server;

//import org.hsqldb.rights.User;

import java.io.File;
import java.net.URL;
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
        //con = DriverManager.getConnection("jdbc:ucanaccess://C://Schule/JavaFX/Chatprojekt/src/Server/Datenbank.accdb");
        File f = new File("src/Server/Datenbank.accdb");
        con = DriverManager.getConnection("jdbc:ucanaccess://" + f.getAbsolutePath());
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

    protected void bannUser(String Username) throws SQLException {

        PreparedStatement st;
        st = con.prepareStatement("UPDATE User set banned = 1 where Username='" + Username + "'");
        st.executeUpdate();
    }

    /*protected void unbannUser(String Username) throws SQLException {

        PreparedStatement st;
        st = con.prepareStatement("UPDATE User set banned = 0 where Username='" + Username + "'");
        st.executeUpdate();
    }*/


}
