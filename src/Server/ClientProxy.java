package Server;

import Client.Crypt;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class ClientProxy implements Runnable
{
    Controller c;
    PrintWriter writer;
    BufferedReader reader;
    Socket client; //Socket geändert
    DataInputStream streamIn;
    DataOutputStream streamOut;

    Crypt crypt = new Crypt();
    PublicKey publilcKeyClient;

    public ClientProxy(Socket client, Controller c) //Socket geändert
    {
        this.c = c;
        this.client = client;

        try
        {
            OutputStream out = client.getOutputStream();
            writer = new PrintWriter(out);
            InputStream in = client.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            streamIn = new DataInputStream(client.getInputStream());
            streamOut = new DataOutputStream(client.getOutputStream());

            keyexchange();
        } catch (Exception e) {
            System.out.println("Fehler im ClientProxy");
        }
    }

    public void run()
    {
        try
        {
            String s = null;
            while ((s = streamIn.readUTF()) !=null)
                c.MessageAll(s);

            //writer.close();
            //reader.close();
        } catch (Exception e) {
            System.out.println("Fehler in ClientProxy Run");
        }
    }

    public void schreiben(String s)
    {
        //writer.write(s + "\n");
        //writer.flush();
        try
        {
            s = (crypt.encrypt(publilcKeyClient, s.getBytes())).toString();
            streamOut.writeUTF(s);
            streamOut.flush();
        }
        catch (Exception e)
        {
            System.out.println("WRITE UTF FEHLER");
        }
    }

    public  void keyexchange()
    {
        try
        {

            crypt.Schluesselpaar(); //Schlüssel generieren
            String empfkey;
            String sendkey;
            PublicKey pubK;

            pubK = crypt.getPublicKey();//senden eigener public key
            byte[] bytearray2 = pubK.getEncoded();
            sendkey = Arrays.toString(bytearray2);
            writer.write(sendkey + "\n");
            writer.flush();

            empfkey = reader.readLine();// lesen vom Socket
            byte[] bytearray = empfkey.getBytes();

            publilcKeyClient = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytearray));
        }
        catch(Exception e)
        {
            System.out.println("Fehler bei der Veschlüsselung");
            e.printStackTrace();
        }
    }



}
