package Client;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;


public class ClientProxy implements Runnable
{
    PrintWriter writer;
    BufferedReader reader;
    Socket client; //socket geändert
    ClientController c;
    InputStream in;
    InputStreamReader inputStreamReader;
    DataInputStream streamIn;
    DataOutputStream streamOut;

    Crypt crypt = new Crypt();
    PublicKey publicKeySersver;


    //neu
    int port = 5555;
    String host = "localhost";


    public ClientProxy(Socket client, ClientController c) //socket geändert
    {
        this.c = c;
        this.client = client;

        try
        {
            //client.startHandshake(); //neu
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            writer = new PrintWriter(out);
            inputStreamReader = new InputStreamReader(in);
            streamIn = new DataInputStream(client.getInputStream());
            streamOut = new DataOutputStream(client.getOutputStream());
            //reader = new BufferedReader(new InputStreamReader(in));
            reader = new BufferedReader(inputStreamReader);
            streamIn = new DataInputStream(client.getInputStream());
            streamOut = new DataOutputStream(client.getOutputStream());

            keyexchange();

        } catch (Exception e)
        {
            System.out.println("Fehler in ClientProxy Constructor1");
            //e.printStackTrace();
        }
    }

    public void run() //Hier ist der Fehler
    {
        try
        {
            String s = null;
            while ((s = reader.readLine()) != null)
            {
                s = (crypt.decrypt(crypt.privKey, s.getBytes())).toString();
                c.textWindow.appendText(s + "\n");
            }
            //writer.close();
            //reader.close();

        } catch (Exception e)
        {
            System.out.println("Fehler in ClientProxy Constructor 2");
            e.printStackTrace();
        }
    }

    public  void schreiben(String s)
    {

        //writer.write(s + "\n");
        //writer.flush();
        try {
            s = (crypt.encrypt(publicKeySersver, s.getBytes())).toString();// verschlüsselen
            streamOut.writeUTF(s);
            streamOut.flush();
        }
        catch (Exception e)
        {
            System.out.println("WRITE UTF FEHLER");
        }

    }

    public void  keyexchange() //beim server andersrum
    {
        try
        {
            crypt.Schluesselpaar();// schlüssel generieren
            String empfkey;
            String sendkey;
            PublicKey pubK;

            empfkey = reader.readLine();// lesen vom Socket
            byte[] bytearray = empfkey.getBytes();

            publicKeySersver = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytearray)); //> abspeichern public key vom server

            pubK = crypt.getPublicKey();//senden eigener public key
            byte[] bytearray2 = pubK.getEncoded();
            sendkey = Arrays.toString(bytearray2);
            writer.write(sendkey + "\n");
            writer.flush();


        }
        catch (Exception e)
        {
            System.out.println("Fehler bei der Veschlüsselung");
            e.printStackTrace();

        }
    }
}
