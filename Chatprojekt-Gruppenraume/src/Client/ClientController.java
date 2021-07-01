package Client;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import javax.naming.Binding;
import java.io.IOException;
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
    @FXML
    PasswordField passwordFieldGruppe;
    @FXML
    Label labelMessage;
    @FXML
    ComboBox comboBoxGruppen;


    private ClientProxy cp;
    private Gruppenraum gruppenraum;
    private Benutzer benutzer;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        benutzer = new Benutzer();
        gruppenraum = new Gruppenraum();
        starteClient();
        TextFieldsWithEnter();
        textFieldGruppenraum.setEditable(false);
        comboBoxGruppen.setPromptText("Wähle Gruppenraum");
    }

    public void wechsleGruppenraum() throws IOException {
        String grname = comboBoxGruppen.getSelectionModel().getSelectedItem().toString();
        System.out.println(grname);
        //Bindings.bindBidirectional(textFieldGruppenraum.textProperty(), comboBoxGruppen.accessibleTextProperty());
        textFieldGruppenraum.setText(grname);


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
                        try {
                            schickeNachricht();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                        try {
                            setGruppenraum();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        passwordFieldGruppe.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                if(event.isShiftDown())
                {
                    passwordFieldGruppe.appendText(System.getProperty("Line.separator"));
                } else {
                    if(!passwordFieldGruppe.getText().isEmpty())
                    {
                        try {
                            setGruppenraum();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                        try {
                            setName();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
            gruppenraum.setGruppenname("Default");
            cp = new ClientProxy(client, this);
            Thread t = new Thread(cp);
            t.start();
        } catch (Exception e) {
            System.out.println("Fehler in CC starteClient D:");
        }
    }

    public void schickeNachricht() throws IOException {
        cp.schreiben(benutzer.getUsername() + ": " + nachrichten.getText());
        nachrichten.setText("");
    }

    public void schickeNamen(String username) throws IOException {
        cp.schreiben(";" + username);
    }

    public void schickeGruppennamen(String gruppenname) throws IOException {
        cp.schreiben("," + gruppenname);
    }

    public void schickePasswort(String passwort) throws IOException {
        cp.schreiben("." + passwort);
    }

    public void verwehreZugriff(String s)
    {

        gruppenraum.setGruppenname(s.substring(s.indexOf(";") + 1));

        Platform.runLater(new Runnable() {
            @Override
            public void run()
            {
                labelMessage.setText("Falsches Passwort für Gruppe " + gruppenraum.getGruppenname());
                textFieldGruppenraum.setText("Default");
            }
        });
    }
    public void erlaubeZugriff(String s)
    {
        gruppenraum.setGruppenname(s.substring(s.indexOf(",") + 1));

        Platform.runLater(new Runnable() {
            @Override
            public void run()
            {
                labelMessage.setText("Willkommen in Gruppe " + gruppenraum.getGruppenname());
            }
        });
    }

    public void setName() throws IOException {
        try
        {
            benutzer.setUsername(name.getText());
        }
        catch(Exception e1)
        {
            System.out.println("Benutzername konnte nicht einglesen werden.");
        }
        if(benutzer.getUsername() != null)
        {
            schickeNamen(benutzer.getUsername());
            nachrichten.setEditable(true);
            textFieldGruppenraum.setText("Default");
            name.setEditable(false);
            textFieldGruppenraum.setEditable(true);
            Platform.runLater(new Runnable() {
                @Override
                public void run()
                {
                    labelMessage.setText("Willkommen in Gruppe " + gruppenraum.getGruppenname());
                }
            });
        }
    }

    private void setGruppenraum() throws IOException {
        try
        {
            gruppenraum.setGruppenname(textFieldGruppenraum.getText());
            gruppenraum.setPasswort(passwordFieldGruppe.getText());
        }
        catch (Exception e1)
        {
            System.out.println("Gruppenname oder Passwort konnte nicht eingelesen werden.");
        }

        if(benutzer.getUsername() != null && gruppenraum.getPasswort() != null && gruppenraum.getGruppenname() != null)
        {
            schickeGruppennamen(gruppenraum.getGruppenname());
            schickePasswort(gruppenraum.getPasswort());
            textWindow.setText("");
        }
        else
        {
            System.out.println("Bitte füllen Sie alle notwendigen Textfelder.");
        }
    }

}
