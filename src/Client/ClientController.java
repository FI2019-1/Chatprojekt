package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javax.net.ssl.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ClientController implements Initializable
{
    @FXML
    TextField nachrichten;
    @FXML
    TextField name;
    @FXML
    TextArea textWindow;

    private String username;
    ClientProxy cp;

    private static final String[] protocols = new String[] {"TLSv1.3"};
    private static final String[] cipher_suites = new String[] {"TLS_AES_128_GCM_SHA256"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        starteClient();
        TextFieldsWithEnter();
    }

    private void TextFieldsWithEnter()
    {
        nachrichten.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                if(event.isShiftDown()) {
                    nachrichten.appendText(System.getProperty("Line.separator"));
                } else {
                    if(!nachrichten.getText().isEmpty())
                    {
                        schickeNachricht();
                    }
                }
            }
        });

        name.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                if(event.isShiftDown())
                {
                    name.appendText(System.getProperty("Line.separator"));
                } else {
                    if(!name.getText().isEmpty())
                    {
                        setName();
                    }
                }
            }
        });
    }

    public void starteClient()
    {
        try
        {
            Socket client = new Socket("localhost", 5555);
            //neu
            //SSLSocketFactory sslFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
            //SSLSocket client = (SSLSocket)sslFactory.createSocket("localhost", 5555);
            //client.setUseClientMode(true);
            /*client.setEnabledCipherSuites( new String[] { "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256" });
            client.setEnabledProtocols(new String[] { "TLSv1.2" });
            SSLParameters sslParams = new SSLParameters();
            sslParams.setEndpointIdentificationAlgorithm("HTTPS");
             client.setSSLParameters(sslParams);
            //client.startHandshake();*/
            //client.setEnabledProtocols(protocols);
            //client.setEnabledCipherSuites(cipher_suites);
            //client.startHandshake();
            /*neu 24.06
            client.addHandshakeCompletedListener(
                    new HandshakeCompletedListener() {
                        public void handshakeCompleted(
                                HandshakeCompletedEvent event) {
                            System.out.println("Handshake finished!");
                            System.out.println(
                                    "\t CipherSuite:" + event.getCipherSuite());
                            System.out.println(
                                    "\t SessionId " + event.getSession());
                            System.out.println(
                                    "\t PeerHost " + event.getSession().getPeerHost());
                        }
                    }
            );*/


            //client.startHandshake();
            //neu ende
            System.out.println("Client konnte gestartet werden :)");
            cp = new ClientProxy(client, this); //geht nicht in ClientProxy
            Thread t = new Thread(cp);
            t.start();
        }
        catch (Exception e)
        {
            System.out.println("Fehler in CC starteClient D:");
            e.printStackTrace();
        }
    }

    public void  schickeNachricht()
    {
        cp.schreiben(username + ": " + nachrichten.getText());
        nachrichten.setText("");
    }

    public  void setName()
    {
        username = name.getText();
        nachrichten.setEditable(true);
        name.setEditable(false);
    }


}
