package Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

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
    TextField textFieldGruppenraum;
    @FXML
    TextArea textWindow;



    private String username;
    private String gruppenname;
    private ClientProxy cp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        starteClient();
        TextFieldsWithEnter();
        textFieldGruppenraum.setEditable(false);
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

        textFieldGruppenraum.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                if(event.isShiftDown())
                {
                    textFieldGruppenraum.appendText(System.getProperty("Line.separator"));
                } else {
                    if(!textFieldGruppenraum.getText().isEmpty())
                    {
                        setGruppenraum();
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
            System.out.println("Client konnte gestartet werden :)");
            cp = new ClientProxy(client, this);
            Thread t = new Thread(cp);
            t.start();
        } catch (Exception e) {
            System.out.println("Fehler in CC starteClient D:");
        }
    }

    public void  schickeNachricht()
    {
        cp.schreiben(username + ": " + nachrichten.getText());
        nachrichten.setText("");
    }

    public void schickeNamen(String username)
    {
        cp.schreiben(";" + username);

    }

    public void schickeGruppennamen(String gruppenname)
    {
        cp.schreiben("," + gruppenname);
    }

    public void setName()
    {
        username = name.getText();
        schickeNamen(username);
        nachrichten.setEditable(true);
        textFieldGruppenraum.setText("Default");
        name.setEditable(false);
        textFieldGruppenraum.setEditable(true);
    }

    private void setGruppenraum()
    {
        gruppenname = textFieldGruppenraum.getText();
        if(username != null)
        {
            schickeGruppennamen(gruppenname);
            textWindow.setText("");
        }
    }
}
