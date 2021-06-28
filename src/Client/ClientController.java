package Client;

import Gemeinsam.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import java.io.File;
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


    private ClientProxy cp;
    private Gruppenraum gruppenraum;


    public File[] file = new File[1];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        gruppenraum = new Gruppenraum();


        //TextFieldsWithEnter();
        //textFieldGruppenraum.setEditable(false);
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
                        //schickeNachricht();
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
        passwordFieldGruppe.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                event.consume();
                if(event.isShiftDown())
                {
                    passwordFieldGruppe.appendText(System.getProperty("Line.separator"));
                } else {
                    if(!passwordFieldGruppe.getText().isEmpty())
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
                        //setName();
                    }
                }
            }
        });
    }


/*

    public void schickeNachricht()
    {
        //bitte ändern.
        //cp.schreiben(cp.getBenutzer().getUsername() + ": " + nachrichten.getText());
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

    public void schickePasswort(String passwort)
    {
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
                labelMessage.setText("Erfolgreich beigetreten in Gruppe " + gruppenraum.getGruppenname());
            }
        });
    }

    public void setName()
    {
        // Funktioniert nicht mehr
        try
        {
            cp.getBenutzer().setUsername(name.getText());
        }
        catch(Exception e1)
        {
            System.out.println("Benutzername konnte nicht einglesen werden.");
        }
        if(cp.getBenutzer().getUsername() != null)
        {
            schickeNamen(cp.getBenutzer().getUsername());
            nachrichten.setEditable(true);
            textFieldGruppenraum.setText("Default");
            name.setEditable(false);
            textFieldGruppenraum.setEditable(true);
        }


    }
*/
    private void setGruppenraum()
    {
        /* Funktioniert nicht mehr
        try
        {
            gruppenraum.setGruppenname(textFieldGruppenraum.getText());
            gruppenraum.setPasswort(passwordFieldGruppe.getText());
        }
        catch (Exception e1)
        {
            System.out.println("Gruppenname oder Passwort konnte nicht eingelesen werden.");
        }

        if(cp.getBenutzer().getUsername() != null && gruppenraum.getPasswort() != null && gruppenraum.getGruppenname() != null)
        {
            schickeGruppennamen(gruppenraum.getGruppenname());
            schickePasswort(gruppenraum.getPasswort());
            textWindow.setText("");
        }
        else
        {
            System.out.println("Bitte füllen Sie alle notwendigen Textfelder.");
        }
        */
    }

    public ClientProxy getCp() {
        return cp;
    }


    public void setCp(ClientProxy cp) {
        this.cp = cp;
        cp.setC(this);
    }

    @FXML
    public void openFilechooser()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Datei auswählen");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Data", "*.*")
        );

        file[0] = fileChooser.showOpenDialog(null);
        System.out.println(file[0]);
    }




    public void sendenEvent(ActionEvent actionEvent)
    {
        Text t = new Text(nachrichten.getText(), cp.getBenutzer());
        cp.senden(t);
    }

}
