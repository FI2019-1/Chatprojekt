package Client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClientMain extends Application implements WindowProperty{

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Anmeldung.fxml"));
        primaryStage.setTitle("Chat Anmeldung");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene sceneLogin = new Scene(root, 375, 403);

        DragDrop(root, primaryStage);

        primaryStage.setScene(sceneLogin);
        sceneLogin.getStylesheets().add((getClass().getResource("AnmeldungUI.css").toExternalForm()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
